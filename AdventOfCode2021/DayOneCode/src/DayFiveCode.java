import ecs100.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DayFiveCode {

    private Integer[][] grid = new Integer[1000][1000];

    public void runDayFiveCodeOne() {
        //
        UI.clearText();
        try {
            for(int x = 0; x < grid[0].length; x++){
                for(int y = 0; y < grid.length; y++){
                    grid[x][y] = 0;
                }
            }
            processFile(Files.readAllLines(Path.of("input5.txt")));
            UI.println("Number of intersections: " + processGrid());
        } catch (IOException e) {
            UI.println("File reading failed");
        }

    }

    public void runDayFiveCodeTwo() {
        //
        UI.clearText();
    }

    public void processFile(List<String> allLines) {

        for (String lineCoord : allLines) {
            //
            String numbersOnly = lineCoord.replaceAll("[^0-9]", " ");
            Scanner sc = new Scanner(numbersOnly);
            //UI.println(numbersOnly);

            Point2D.Double start = new Point2D.Double(sc.nextInt(),sc.nextInt());
            Point2D.Double end = new Point2D.Double(sc.nextInt(),sc.nextInt());

            Day5Line line = new Day5Line(start, end);

            if(line.isHoriz()){//horizontal lines, x changes, y stays the same
                int largest = Math.max(line.getX1(), line.getX2());
                int smallest = Math.min(line.getX1(), line.getX2());
                int yCoord = line.getY1();

                for(int i = smallest; i <= largest; i++){
                    grid[i][yCoord]++;
                }

            }
            else if(line.isVert()){//vertical lines, y changes, x stays the same
                int largest = Math.max(line.getY1(), line.getY2());
                int smallest = Math.min(line.getY1(), line.getY2());
                int xCoord = line.getX1();
                for(int i = smallest; i <= largest; i++){
                    grid[xCoord][i]++;
                }
            }
            else if(line.isDiag()){
                //Point2D.Double smallestPoint =
                double x = line.getX1();
                double y = line.getY1();
                while(line.getX1() != line.getX2()){
                    //UI.println(x+" diag "+y);
                    //UI.sleep(1);


                    grid[line.getX1()][line.getY1()]++;

                    if(line.getX1() < line.getX2()){//x is reaching but not y
                        //x++

                        if(line.getY1() > line.getY2()){
                        //y--

                            line.setStart(line.getX1()+1, line.getY1()-1);
                        }
                        else if(line.getY1() < line.getY2()){
                            //y++
                            line.setStart(line.getX1()+1, line.getY1()+1);
                        }
                    }
                    else if(line.getX1() > line.getX2()){
                        //x--

                        if(line.getY1() > line.getY2()){
                            //y--
                            line.setStart(line.getX1()-1, line.getY1()-1);
                        }
                        else if(line.getY1() < line.getY2()){
                            //y++
                            line.setStart(line.getX1()-1, line.getY1()+1);
                        }

                    }

                    }
                grid[line.getX1()][line.getY1()]++;

            }


            //grid[line.getX1()][line.getY1()]++;//increase num at that index


        }
    }

    public int processGrid(){
        int count = 0;
        for(int x = 0; x < grid[0].length; x++){
            for(int y = 0; y < grid.length; y++){
                if(grid[x][y] > 1){
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        // write your code here
        DayFiveCode dfic = new DayFiveCode();
        dfic.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Five Part 1", this::runDayFiveCodeOne);
        UI.addButton("Day Five Part 2", this::runDayFiveCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
