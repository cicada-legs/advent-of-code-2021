import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DayNineCode {

    private ArrayList<ArrayList<Day9Square>> pointMap = new ArrayList<ArrayList<Day9Square>>();
    private ArrayList<Integer> basinList = new ArrayList<>();
    private ArrayList<Day9Square> lowPoints = new ArrayList<>();
    int bs = 0;

    public void initializeFile() {
        try {
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input9.txt")));
            for (int i = 0; i < allLines.size(); i++) {
                pointMap.add(i, new ArrayList<>());
                //
                for (int j = 0; j < allLines.get(i).length(); j++) {
                    //
                    pointMap.get(i).add(j, new Day9Square(Integer.valueOf(String.valueOf(allLines.get(i).charAt(j))), i, j));
                    //UI.print(Integer.valueOf(String.valueOf(allLines.get(i).charAt(j))));
                }
                //UI.println();

            }
        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayNineCodeOne() {
        //
        //for
        pointMap.clear();
        int totalRisk = 0;
        initializeFile();


        //check all elements using lowerThan neighbours, if so, +1 then add to total risk

        for (int r = 0; r < pointMap.size(); r++) {
            for (int c = 0; c < pointMap.get(0).size(); c++) {
                if (lowerThanNeighbours(r, c)) {
                    UI.print("|" + pointMap.get(r).get(c).getHeight() + "|");
                    totalRisk = totalRisk + (pointMap.get(r).get(c).getHeight() + 1);
                    //add to low points!!!!!
                    //lowPoints.get(r).add(c, pointMap.get(r).get(c).getHeight());
                    lowPoints.add(pointMap.get(r).get(c));
                } else {
                    UI.print(" " + pointMap.get(r).get(c).getHeight() + " ");
                    //lowPoints.remove()
                }

            }
            UI.println();
        }
        UI.println("total risk: " + totalRisk);


        //method 2: basins


    }

    public void runDayNineCodeTwo() {
        //
        pointMap.clear();
        basinList.clear();
        initializeFile();
        for (int r = 0; r < pointMap.size(); r++) {

            for (int c = 0; c < pointMap.get(0).size(); c++) {

                //call recursive method, check if visited
                //if(lowPoints.contains(pointMap.get(r).get(c))){//???????
                bs = 0;
                exploreBasin(r, c);
                if(bs > 0){
                    basinList.add(bs);
                }
                //}

            }
        }

        Collections.sort(basinList, Collections.reverseOrder());
        for(int i = 0; i < basinList.size(); i++){
            UI.println("basin size: " + basinList.get(i));
        }
        UI.println("result: " + basinList.get(0)*basinList.get(1)*basinList.get(2));
    }

    public boolean lowerThanNeighbours(int row, int col) {
        //
        if (pointMap.get(row).get(col) == null) {
            return false;
        }

        int mid = pointMap.get(row).get(col).getHeight();

        ArrayList<Integer> notNull = new ArrayList<>();

        if (row - 1 > -1) {
            notNull.add(pointMap.get(row - 1).get(col).getHeight());
        }
        if (row + 1 < pointMap.size()) {
            notNull.add(pointMap.get(row + 1).get(col).getHeight());
        }
        if (col - 1 > -1) {
            notNull.add(pointMap.get(row).get(col - 1).getHeight());
        }
        if (col + 1 < pointMap.get(0).size()) {
            notNull.add(pointMap.get(row).get(col + 1).getHeight());
        }

//        if(pointMap.get(row-1).get(col) != null && pointMap.get(row-1).get(col) < mid){
//            //
//        }

        for (int i = 0; i < notNull.size(); i++) {
            if (notNull.get(i) <= mid) {
                return false;
            }

        }


        return true;
    }

    public List<Day9Square> getNeighbours(int row, int col) {
//        if (pointMap.get(row).get(col) == null) {
//            return false;
//        }

        int mid = pointMap.get(row).get(col).getHeight();

        ArrayList<Day9Square> notNull = new ArrayList<>();

        if (row - 1 > -1) {
            notNull.add(pointMap.get(row - 1).get(col));
        }
        if (row + 1 < pointMap.size()) {
            notNull.add(pointMap.get(row + 1).get(col));
        }
        if (col - 1 > -1) {
            notNull.add(pointMap.get(row).get(col - 1));
        }
        if (col + 1 < pointMap.get(0).size()) {
            notNull.add(pointMap.get(row).get(col + 1));
        }

//        if(pointMap.get(row-1).get(col) != null && pointMap.get(row-1).get(col) < mid){
//            //
//        }

        return notNull;
    }

    public void exploreBasin(int row, int col) {//passed all cells lower than neighbours
        //
        Day9Square cell = pointMap.get(row).get(col);
        if (cell.getHeight() == 9) {
            return;
        }
        if (cell.isVisited()) {
            return;
        }
        cell.visit();
        //basinList.add(cell);//????????
        //
        if(cell.getHeight() < 9){bs++;}
        for (Day9Square neighbour : getNeighbours(row, col)) {

            //basinList.get()
            exploreBasin(neighbour.getRow(), neighbour.getCol());

        }
    }

    public static void main(String[] args) {
        // write your code here
        DayNineCode dnc = new DayNineCode();
        dnc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Six Part 1", this::runDayNineCodeOne);
        UI.addButton("Day Six Part 2", this::runDayNineCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
