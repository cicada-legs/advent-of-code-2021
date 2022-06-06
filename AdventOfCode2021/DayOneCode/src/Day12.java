import ecs100.UI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {

    private Map<String, Day12Cave> caveNetwork = new HashMap<>();
    private Set<Day12Cave> smallCaves = new HashSet<>();
    private Set<Stack<String>> uniquePaths = new HashSet<>();
    private int pathNum = 0;

    public void runDay12One(){
        pathNum = 0;
        smallCaves.clear();
        caveNetwork.clear();
        uniquePaths.clear();
        try{

            for(String line : Files.readAllLines(Path.of("input12.txt"))){
                //
                String hereToThere = line.replaceAll("-", " ");
                Scanner sc = new Scanner(hereToThere);
                String here = sc.next();
                String there = sc.next();
                //isAllUpperCase

                if(!caveNetwork.containsKey(here)){
                    // add it but add neighbour
                    caveNetwork.put(here, new Day12Cave(here, here.equals(here.toLowerCase()), here.equals("start"), here.equals("end")));//check this works
                }
                if(!caveNetwork.containsKey(there)){
                    //dont add it but add neighbour
                    caveNetwork.put(there, new Day12Cave(there, there.equals(there.toLowerCase()), there.equals("start"), there.equals("end")));
                }

                //then add each other as neighbours
                caveNetwork.get(here).addNeighbour(caveNetwork.get(there));
                caveNetwork.get(there).addNeighbour(caveNetwork.get(here));

                if(caveNetwork.get(here).isSmall() && !(caveNetwork.get(here).isStart() || caveNetwork.get(here).isEnd())){
                    smallCaves.add(caveNetwork.get(here));
                }
                if(caveNetwork.get(there).isSmall() && !(caveNetwork.get(here).isStart() || caveNetwork.get(here).isEnd())){
                    smallCaves.add(caveNetwork.get(there));
                }

            }
            UI.println("Loaded " + caveNetwork.size() + " caves");

        }catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void partTwo(){

        for(Day12Cave original : smallCaves){
            //create dupliate small cave with the exact same neighbours
            //caveNetwork.put()
            Day12Cave here = caveNetwork.get(original.getName());
            Day12Cave copy = new Day12Cave(here.getName(), here.isSmall(), here.isStart(), here.isEnd());

            caveNetwork.put("copy", copy);//DO I HAVE TO MANUALLY MAKE A COPY??
            //add all neighbours to here
            //add to all neighbours
            for(Day12Cave neighbour : original.getNeighbours()){
                //
                caveNetwork.get("copy").addNeighbour(caveNetwork.get(neighbour.getName()));
                caveNetwork.get(neighbour.getName()).addNeighbour(caveNetwork.get("copy"));
            }
            //do recursive call here
            printAllPaths(caveNetwork.get("start"), new Stack<>());
            //then reverse all changes
            for(Day12Cave neighbour : original.getNeighbours()){
                //
                caveNetwork.get("copy").removeNeighbour(caveNetwork.get(neighbour.getName()));
                caveNetwork.get(neighbour.getName()).removeNeighbour(caveNetwork.get("copy"));
            }
            caveNetwork.remove("copy");

        }
        UI.println("unique paths: " + uniquePaths.size());
        for(Stack<String> stack : uniquePaths){
            for(String step : stack){
                UI.print(step);
            }
            UI.println();
        }
    }

    public void printAllPaths(Day12Cave cave, Stack<String> pathStack){
        //
        if(cave.getName().equals("end")){
            //add string to paths
            pathStack.push(cave.getName());
            //UI.println(path);
            for(String step : pathStack){
                //UI.print(step);
            }
            //UI.println();
            pathNum++;
            //UI.println(pathNum);
            Stack<String> copyStack = new Stack<>();
            copyStack.addAll(pathStack);
            uniquePaths.add(copyStack);//??????
            pathStack.pop();//???
            return;
        }

        if(cave.isStart() && !pathStack.isEmpty()){return;}

        if(cave.isVisited() && cave.isSmall()){return;}
        cave.visit();
        pathStack.push(" " + cave.getName() + " ");
        //add this cave to the string for one path
        for(Day12Cave neighbour : cave.getNeighbours()){
            printAllPaths(neighbour, pathStack);
        }
        cave.reset();//unvisit
        pathStack.pop();
        //how to only print direct path??
    }

    public static void main(String[] args) {
        // write your code here
        Day12 d12 = new Day12();
        d12.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Load", this::runDay12One);
        UI.addButton("Search all", ()-> {printAllPaths(caveNetwork.get("start"), new Stack<>());});
        UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
