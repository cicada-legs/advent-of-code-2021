import ecs100.UI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day15 {

    private Day15Cell[][] grid = new Day15Cell[100][100];
    private Map<Point, Day15Cell> graph = new HashMap();
    //private Map<>

    public void runDay15One(){
        try{

            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input15.txt")));
            //startPos not counted

            for(int r = 0; r < allLines.size(); r++){
                //
                for(int c = 0; c < allLines.get(0).length(); c ++){
                    Day15Cell cell = new Day15Cell(r, c, Integer.parseInt(String.valueOf(allLines.get(r).charAt(c))));
                    graph.put(new Point(r, c), cell);
                    grid[r][c] = cell;
                }

            }

//            for(Day15Cell cell : graph.values()){
//                addNeighbours(cell);
//            }
//
//            findShortest(grid[0][0]);
//            UI.println(graph.get(new Point(99, 99)).getDistance());
//
//            for(Day15Cell cell : graph.values()){
//                cell.clearNeighbours();
//            }

            //works up to here EXPANDING IS WRONG

            int maxRow = grid.length-1;
            int maxCol = grid[0].length -1;

            for(int r = maxRow+1; r < 5*(maxRow+1); r++){
                for(int c = 0; c <= maxCol; c++){
                    //extend downward;
                    //Day15Cell newCell = new Day15Cell(r, c, );
                    int newRisk = graph.get(new Point(r - maxRow -1, c)).getRiskLevel() + 1;
                    if(newRisk > 9){newRisk = 1;}
                    graph.put(new Point(r, c), new Day15Cell(r, c, newRisk));
                }
            }//good
            maxRow = 5*(maxRow+1)-1;
            for(int c = maxCol+1; c < 5*(maxCol+1); c++){
                for(int r = 0; r <= maxRow; r++){
                    //extend right
                    int newRisk = graph.get(new Point(r, c - maxCol - 1)).getRiskLevel() + 1;
                    if(newRisk > 9){newRisk = 1;}
                    graph.put(new Point(r, c), new Day15Cell(r, c, newRisk));
                }
            }

            for(Day15Cell cell : graph.values()){
                addNeighbours(cell);
            }

            UI.println("new size: " + graph.size());
            UI.println("-----");
            findShortest(graph.get(new Point(0,0)));
            UI.println(graph.get(new Point(499, 499)).getDistance());




        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void findShortest(Day15Cell source){

        source.setRiskLevel(0);
        source.setDistance(BigInteger.ZERO);

        Set<Day15Cell> settledNodes = new HashSet<>();
        Set<Day15Cell> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while(unsettledNodes.size() != 0){
            Day15Cell currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for(Map.Entry<Day15Cell,Integer> neighbourPair : currentNode.getNeighbours().entrySet()){
                //
                Day15Cell neighbour = neighbourPair.getKey();
                int risk = neighbourPair.getValue();
                if(!settledNodes.contains(neighbour)){
                    calculateMinDistance(neighbour, risk, currentNode);
                    unsettledNodes.add(neighbour);
                }
            }
            settledNodes.add(currentNode);

        }
        //finish here
    }

    public Day15Cell getLowestDistanceNode(Set<Day15Cell> unSettledNodes){

        Day15Cell lowestDistNode = null;
        BigInteger lowestDist = BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(99999));//infinity
        for(Day15Cell cell : unSettledNodes){

            BigInteger nodeDistance = cell.getDistance();
            if(nodeDistance.compareTo(lowestDist) < 0 ){//nodeDistance < lowestDist
                lowestDist = nodeDistance;
                lowestDistNode = cell;
            }

        }
        return lowestDistNode;
    }

    public void calculateMinDistance(Day15Cell evaluationNode, int risk, Day15Cell sourceNode){
        BigInteger sourceDist = sourceNode.getDistance();
        if(sourceDist.add(BigInteger.valueOf(risk)).compareTo(evaluationNode.getDistance()) < 0){//sourceDist + risk < evaluationNode.getDistance()
            evaluationNode.setDistance(sourceDist.add(BigInteger.valueOf(risk)));
//            Set<Day15Cell> shortestPath = new HashSet<>(sourceNode.getShortestPath());
//            shortestPath.add(sourceNode);
            sourceNode.getShortestPath().add(sourceNode);
              evaluationNode.setShortestPath(sourceNode.getShortestPath());
        }
    }

    public Set<Day15Cell> addNeighbours(Day15Cell here){
        //
        Set<Day15Cell> neighbours = new HashSet<>(4);

        if (graph.get(new Point(here.getRow() - 1, here.getCol())) != null) {
            neighbours.add(graph.get(new Point(here.getRow() - 1, here.getCol())));
        }
        if (graph.get(new Point(here.getRow() + 1, here.getCol())) != null) {
            neighbours.add(graph.get(new Point(here.getRow() + 1, here.getCol())));
        }
        if ((graph.get(new Point(here.getRow(), here.getCol() - 1)) != null)) {
            neighbours.add(graph.get(new Point(here.getRow(), here.getCol() - 1)));
        }
        if (graph.get(new Point(here.getRow(), here.getCol() + 1)) != null) {
            neighbours.add(graph.get(new Point(here.getRow(), here.getCol() + 1)));
        }

        for(Day15Cell n : neighbours){
            here.addDestination(n, n.getRiskLevel());
        }
        return neighbours;
    }

    public static void main(String[] args) {
        // write your code here
        Day15 d15 = new Day15();
        d15.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay15One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }


}

