import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DayElCode {

    private Day11Oct[][] allOcts = new Day11Oct[10][10];
    private int totalFlash = 0;
    private int step = 1;

    public void runDayElCodeOne() {
        //try
        try {

            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("fuck.txt")));
            //
            for (int x = 0; x < allLines.size(); x++) {
                //
                String line = allLines.get(x);
                for (int y = 0; y < line.length(); y++) {
                    allOcts[x][y] = new Day11Oct(Integer.parseInt(String.valueOf(line.charAt(y))), x, y);
                }
            }
            //do method
            step(1000);
            UI.print("total flashed: " + totalFlash);

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayElCodeTwo() {
        //try
    }

    public void step(int steps) {
        //call recursive method which returns num of total flashes
        //method is given amount of steps to take
        if (steps == 0) {
            return;
        }
        UI.println("steps left: " + (steps - 1));
        Set<Day11Oct> maxOcts = new HashSet<>();

        for (int x = 0; x < allOcts.length; x++) {
            for (int y = 0; y < allOcts[0].length; y++) {
                //increase all numbers, if 10, flash
                allOcts[x][y].reset();
                allOcts[x][y].increaseLevel();
                //UI.print(allOcts[x][y].getLevel());
                if(allOcts[x][y].isMax()){
                    maxOcts.add(allOcts[x][y]);
                }
            }
            //UI.println();
        }
        for(Day11Oct o : maxOcts){
            doFlashes(o);
        }
        //check all

        if(printAll()){
            UI.println("AT THIS STEP ALL FLASHED" + step);
            return;
        }
        else{step++;}

        step(steps - 1);
    }

    public void doFlashes(Day11Oct octo) {

//        if (!octo.isMax()) {
//            return;
//        }
        if (octo.isMax()) {
            octo.doFlash();//set level to 0 and set flashed to true
            totalFlash = totalFlash + 1;//???????
            for (Day11Oct o : getNeighbours(octo.getX(), octo.getY())) {
                //if(octo.hasFlashed() == false){
                //step
                if(!o.hasFlashed()){
                    o.increaseLevel();
                }
                //
                doFlashes(o);
                //}
                //WHY ARE NON ZERI NEIGHBOURS NOT INCREASING???
            }
        }

    }

    public boolean printAll() {
        //
        int total = 0;
        UI.println("****************************************");
        for (int x = 0; x < allOcts.length; x++) {
            for (int y = 0; y < allOcts[0].length; y++) {
                //increase all numbers, if 10, flash
                UI.print(allOcts[x][y].getLevel());
                total += allOcts[x][y].getLevel();
            }
            UI.println();
        }
        UI.println("****************************************");
        return total == 0;
    }

    public List<Day11Oct> getNeighbours(int row, int col) {//change to increase all negihbours
        //
        ArrayList<Day11Oct> neighbours = new ArrayList<>();
        if (row > 0) {
            neighbours.add(allOcts[row - 1][col]);
            if (col > 0) {
                neighbours.add(allOcts[row - 1][col - 1]);
            }
            if (col < 9) {
                neighbours.add(allOcts[row - 1][col + 1]);
            }
        }
        if (row < 9) {
            neighbours.add(allOcts[row + 1][col]);
            if (col > 0) {
                neighbours.add(allOcts[row + 1][col -1]);
            }
            if (col < 9) {
                neighbours.add(allOcts[row + 1][col + 1]);
            }
        }
        if (col > 0) {
            neighbours.add(allOcts[row][col - 1]);
        }
        if (col < 9) {
            neighbours.add(allOcts[row][col + 1]);
        }
        return neighbours;
    }

    public static void main(String[] args) {
        // write your code here
        DayElCode dec = new DayElCode();
        dec.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Six Part 1", this::runDayElCodeOne);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
