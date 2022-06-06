import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.TreeMap;

public class Day19 {

    private NavigableMap<String, Set<Day19Point>> scannerMap = new TreeMap<>();


    public void runDay19One(){
        try{
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input19.txt")));
            for(String line : allLines){
                Scanner sc;
                if(line.contains("scanner")){
                    String scanNum = line.replaceAll("[^\\d]", "");
                    scannerMap.put(scanNum, new HashSet<>());
                    UI.println(scanNum);
                }
                else{
                    String coords = line.replaceAll("[^\\d-]", "");
                    sc = new Scanner(coords);
                    Day19Point point = new Day19Point(sc.nextInt(), sc.nextInt(), sc.nextInt());
                    scannerMap.computeIfAbsent(scannerMap.lastKey(), k -> new HashSet<>()).add(point);

                }
            }
            //foo

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void findOverlap(){
        //try each scanner (all coords) with scanner 0
        //try all permuations with +and-
        //add xyz together, sibtracts from another scanners xyz
        //dx dy dz
        //12 good beacons
    }

    public static void main(String[] args) {
        // write your code here
        Day19 d19 = new Day19();
        d19.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1 day 19", this::runDay19One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
