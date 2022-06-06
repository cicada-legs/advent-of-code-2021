import ecs100.UI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.module.FindException;
import java.math.BigInteger;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DaySevCode {

    private ArrayList<Integer> allCrabs = new ArrayList<>();

    public void runDaySevCodeOne(){
        //
        try{
            for(String line : Files.readAllLines(Path.of("input7.txt"))){
                //
                String crabNums = line.replaceAll("[^0-9]", " ");
                Scanner sc = new Scanner(crabNums);
                while (sc.hasNextInt()) {//make all the fish for day 1
                    int crab = sc.nextInt();
                    allCrabs.add(crab);//add each num as a crab
                }
            }

            //findCost(0, new ArrayList<Integer>(allCrabs), 0);
            int cheapest = Integer.MAX_VALUE;
            int pos = 0;
            for(int c = 0; c < 2000; c++){
                int cost = findCost(c, 0);
                UI.println("pos: " + c + " cost: " + cost);
                if(cost < cheapest){
                    cheapest = cost;
                    pos = c;
                }
            }
            UI.println("Move all to: " + pos + "        cost: " + cheapest);

        }catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public int findCost(int crabIndex, int cost){

        for(int i = 0 ; i < allCrabs.size(); i++){

            int steps = Math.abs(allCrabs.get(i) - crabIndex);

            for(int s = 1; s <= steps; s++){
                cost = cost + s;
            }
        }
        return cost;
    }

    public void runDaySevCodeTwo(){
        //
    }

    public static void main(String[] args) {
        // write your code here
        DaySevCode dsec = new DaySevCode();
        dsec.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Six Part 1", this::runDaySevCodeOne);
        UI.addButton("Day Six Part 2", this::runDaySevCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
