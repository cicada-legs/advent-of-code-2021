import ecs100.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DayThreeCode {

    public void runDayThreeCodeOne() {
        UI.clearText();
        try {
            readFile(Collections.unmodifiableList(Files.readAllLines(Path.of("input3.txt"))));
        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayThreeCodeTwo() {
        UI.clearText();
        try {//do it twice
            //first char is kept
            UI.print("oxy: ");
            readFileCont(Collections.unmodifiableList(Files.readAllLines(Path.of("input3.txt"))), '1', '0');
            UI.print("co2: ");
            readFileCont(Collections.unmodifiableList(Files.readAllLines(Path.of("input3.txt"))), '0', '1');
        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void readFile(List<String> allLines) {
        //
        String gamma = "";
        String epsilon = "";

        int[] zeroArray = new int[12];
        int[] oneArray = new int[12];

        for (String line : allLines) {
            Scanner scan = new Scanner(line);
            String num = scan.next();
            for (int i = 0; i < line.length(); i++) {
                Scanner sc = new Scanner(num);
                if (sc.hasNext()) {
                    //
                    char bit = sc.next().charAt(i);
                    String c = String.valueOf(bit);
                    if (c.equals("0")) {
                        zeroArray[i]++;
                    } else if (c.equals("1")) {
                        oneArray[i]++;
                    }
                    //UI.println("zeroArray: " + zeroArray[i] + "     oneArray: " + oneArray[i]);
                }


            }
        }

        //iterate through both arrays and find largest of each char

        for (int i = 0; i < zeroArray.length; i++) {//?
            //
            if (zeroArray[i] < oneArray[i]) {
                //
                gamma = gamma + "1";
                epsilon = epsilon + "0";
            } else {
                //
                gamma = gamma + "0";
                epsilon = epsilon + "1";
            }
            //UI.println("zeroArray: " + zeroArray[i] + "     oneArray: " + oneArray[i]);
        }
        UI.println("gamma: " + gamma + "    epsilon: " + epsilon);
    }

    public void readFileCont(List<String> allLines, char keep, char kill) {//final?????
        int ones = 0;
        int zeros = 0;
        char keepChar = '0';
        ArrayList<String> tempList = new ArrayList<>();

        for(int i = 0; i < allLines.get(0).length(); i++){//loop through each char
            //
            for(int x = 0; x < allLines.size(); x++){//loop through each line and get most common in each char
                //
                if(String.valueOf(allLines.get(x).charAt(i)).equals("0")){
                    zeros++;
                }
                else{
                    ones++;
                }
            }

            if(zeros > ones){//more zeros than ones
                keepChar = keep;//e.g. 0, 1
            }
            else{
                keepChar = kill;
            }

            for(int z = 0; z < allLines.size(); z++){
                if(allLines.get(z).charAt(i) == keepChar){
                    //add it to mod
                    tempList.add(allLines.get(z));
                }
            }

            allLines = new ArrayList<>(tempList);
            tempList.clear();

            ones = 0;
            zeros = 0;

            if(allLines.size() == 1){
                //stop
                break;
            }
        }
        UI.println(allLines.get(0));
    }

    public static void main(String[] args) {
        // write your code here
        DayThreeCode dthc = new DayThreeCode();
        dthc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Three Part 1", this::runDayThreeCodeOne);
        UI.addButton("Day Three Part 2", this::runDayThreeCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
