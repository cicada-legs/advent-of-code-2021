import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day22 {

    private Set<String> onSet = new HashSet<>();
    //private Set<Day19Point> offSet = new HashSet<>();

    public void runDay22One(){

        //2 sets of on and of

        try{
            String onlyNums;
            List<Day19Point> activatedCells = new ArrayList<>();

            for(String line : Files.readAllLines(Path.of("input22.txt"))){
                onlyNums = line.replaceAll("[^\\d-]", " ");
                UI.println(onlyNums);
                Scanner sc= new Scanner(line);
                String instruction = sc.next();
                sc.close();
                Scanner scan = new Scanner(onlyNums);

                Range xRange = new Range(scan.nextInt(), scan.nextInt());
                Range yRange = new Range(scan.nextInt(), scan.nextInt());
                Range zRange = new Range(scan.nextInt(), scan.nextInt());

                //if(!(xRange.tooLarge()|| yRange.tooLarge() || zRange.tooLarge())){
                    //return a list of perms
                    //add all to onSet or remove depending on instruction
                    Set<String> temp = new HashSet<>();
                    findPermutations(new ArrayList<>(List.of(xRange, yRange, zRange)), temp, 0, "");
                    if(instruction.equals("on")){
                        onSet.addAll(temp);
                    }
                    else if(instruction.equals("off")){
                        temp.forEach(onSet::remove);
                    }
                    //convert temp to 1d list of point
                // }

                //if any number Math.abs > 50, skip
            }

//            for(String line : onSet){
//                UI.println(line);
//            }
            UI.println("cubes: " + onSet.size());

        } catch (IOException e) {
            UI.println("File reading failed");
        }

    }

    public void findPermutations(ArrayList<Range> list, Set<String> results, int depth, String current){
        //
        if(depth == list.size()){
            results.add(current);
            return;
        }

        for(int i = 0; i < list.get(depth).getSize(); i++){
            findPermutations(list, results, depth + 1, current + " " + list.get(depth).getAt(i) + " ");
        }
    }

    public static void main(String[] args) {
        // write your code here
        Day22 d22 = new Day22();
        d22.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay22One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}

