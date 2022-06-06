import ecs100.*;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class DayOne {

    public int increases = 0;

    public void runDayOneCodeOne() {
        increases = 0;
        UI.clearText();
        try {
            readFile(Files.readAllLines(Path.of("input.txt")));
        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayOneCodeTwo() {
        UI.clearText();
        increases = 0;
        readFileWindow();
    }

    public void readFile(List<String> allLines) {//add param
        ////get the current and previous depth
        //is the current is larger, concat increased
        //else concat smaller or same


        for (int i = 0; i < allLines.size(); i++) {
            int depth = Integer.parseInt(allLines.get(i));//get the line as an int
            String change = "";
            if (i > 0) {//if this is not the first line
                //check the previous num
                int prevDepth = Integer.parseInt(allLines.get(i - 1));

                if (depth > prevDepth) {
                    //increase
                    change = change + "(increased)";
                    increases++;
                } else if (depth == prevDepth) {
                    change = change + "(no change)";
                } else {
                    change = change + "(decreased)";
                }
            } else {
                change = change + "(N/A - no previous measurement)";
            }

            UI.println(depth + change + "   debug: "+increases);
        }
        UI.println();
        UI.println("--------------------");
        UI.println("Number of times depth increased: " + increases);


    }

    public void readFileWindow() {
        //
        try {
            List<String> allLines = Files.readAllLines(Path.of("input.txt"));
            List<String> allSums = new ArrayList<>();

            for (int i = 0; i < allLines.size(); i++) {
                int windowSum = 0;
                if (i < allLines.size()-2) {//on;y start recording at third index
                    //
                    int depthOne = Integer.parseInt(allLines.get(i));
                    int depthTwo = Integer.parseInt(allLines.get(i + 1));
                    int depthThree = Integer.parseInt(allLines.get(i + 2));
                    windowSum = depthOne + depthTwo + depthThree;
                }
                allSums.add(String.valueOf(windowSum));
            }

            //call file reading method here
            readFile(allSums);

        } catch (IOException e) {
            UI.println("File reading failed");
        }

    }

    /*****************************************************************/

//    private Point2D.Double currentPos = new Point2D.Double(0,0);
//
//    public void processFIle(){
//        //
//    }

    /*****************************************************************/

    public static void main(String[] args) {
        // write your code here
        DayOne doc = new DayOne();
        doc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day One Part 1", this::runDayOneCodeOne);
        UI.addButton("Day One Part 2", this::runDayOneCodeTwo);
        //UI.addButton("Day Two Part 1", this::runDayTwoCodeOne);
        //UI.addButton("Day Two Part 2", this::runDayTwoCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
