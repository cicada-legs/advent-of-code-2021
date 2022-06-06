import ecs100.UI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.math.BigInteger;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DaySixCode {

    private ArrayList<DaySixFish> allFish = new ArrayList<>();
    private int[] lives = new int[9];
    private Map<Integer, BigInteger> life = new HashMap<>();

    public void runDaySixCodeOne() {
        allFish.clear();
        try {
            for (String line : Files.readAllLines(Path.of("input6.txt"))) {
                String fishNums = line.replaceAll("[^0-9]", " ");
                Scanner sc = new Scanner(fishNums);
                while (sc.hasNextInt()) {//make all the fish for day 1
                    //scan and make a new fish
                    DaySixFish newFish = new DaySixFish(sc.nextInt());
                    allFish.add(newFish);
                }
                //call time running method
                runSim(80);//test
                UI.println("Number of total fish: " + allFish.size());
            }

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDaySixCodeTwo() {
        allFish.clear();
        lives = new int[9];
        life.clear();
        try {
            for (String line : Files.readAllLines(Path.of("input6.txt"))) {
                String fishNums = line.replaceAll("[^0-9]", " ");
                Scanner sc = new Scanner(fishNums);
                while (sc.hasNextInt()) {//make all the fish for day 1
                    //scan and make a new fish
                    int life = sc.nextInt();
                    DaySixFish newFish = new DaySixFish(life);
                    allFish.add(newFish);
                }
                //call time running method
                runSimBig(256);//test
                //UI.print("Number of total fish: " + allFish.size());
            }

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runSim(int time) {
        //
        while (time > 0) {
            time--;
            for (int f = 0; f < allFish.size(); f++) {
                //
                allFish.get(f).tick();//pass the time

                if (allFish.get(f).isReady()) {
                    allFish.get(f).resetTimer();
                    DaySixFish babyFish = new DaySixFish(9);
                    allFish.add(babyFish);
                }
                //UI.print(allFish.get(f).getTime() + " ");
            }//UI.println();
        }
    }

    public void runSimBig(int days) {
        //
        for (DaySixFish f : allFish) {
            //
            lives[f.getTime()]++;

            if (life.containsKey(f.getTime())) {
                life.put(f.getTime(), life.get(f.getTime()).add(BigInteger.ONE));
            } else {
                life.put(f.getTime(), BigInteger.ONE);
            }
            //UI.println(life.get(f.getTime()));
            //life.get(f.getTime(), )
            //UI.println(f.getTime() + ": " + lives[f.getTime()]);
        }

        for(int t = 0; t<9; t++){
            if(!life.containsKey(t)){
                life.put(t, BigInteger.ZERO);
            }
            //UI.println(life.get(t));
        }
        Map<String, BigInteger> negatives = new HashMap<>();
        negatives.put("fook", BigInteger.ZERO);
        for (int i = 0; i < days; i++) {
            //
            UI.println("day: "+i );

            negatives.put("fook" , life.get(0));
            life.put(0, life.get(1));
            life.put(1, life.get(2));
            life.put(2, life.get(3));
            life.put(3, life.get(4));
            life.put(4, life.get(5));
            life.put(5, life.get(6));
            life.put(6, life.get(7));
            life.put(7, life.get(8));
            life.put(8, negatives.get("fook"));
            life.put(6, life.get(6).add(negatives.get("fook")));
            negatives.clear();//???
        }
        BigInteger totalFish = BigInteger.ZERO;
        int total = 0;
        for (int i = 0; i < 9; i++) {
            total = total + lives[i];
            totalFish = (totalFish.add(life.get(i)));
        }
        UI.println("Theres so many goddamn fish: " + totalFish);
    }

    public static void main(String[] args) {
        // write your code here
        DaySixCode dsc = new DaySixCode();
        dsc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Six Part 1", this::runDaySixCodeOne);
        UI.addButton("Day Six Part 2", this::runDaySixCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
