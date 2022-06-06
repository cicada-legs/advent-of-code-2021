import ecs100.UI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Day15Cell {

    private int riskLevel, row, col;
    private Set<Day15Cell> shortestPath = new HashSet<>();
    private BigInteger distance = BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(99999));
    private Map<Day15Cell, Integer> neighbours = new HashMap();

    public Day15Cell(int r, int c, int risk){
        riskLevel = risk;
        row = r;
        col = c;
    }

    public void addDestination(Day15Cell destination, int dist){
        neighbours.put(destination, dist);
    }

    public Map<Day15Cell, Integer> getNeighbours(){
        return neighbours;
    }

    public Set<Day15Cell> getShortestPath(){
        return shortestPath;
    }

    public void clearNeighbours(){
        neighbours.clear();
    }

    public void setShortestPath(Set<Day15Cell> list){
        shortestPath = list;
    }

    public BigInteger getDistance(){
        return distance;
    }

    public void setDistance(BigInteger i){
        distance = i;
    }

    public void setRiskLevel(int risk){
        riskLevel = risk;
    }

    public int getRiskLevel(){
        return riskLevel;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o == null) {
//            return false;
//        }
//        if (!(o instanceof Point)) {
//            return false;
//        }
//        return (x == ((Point) o).x && y == ((Point) o).y);
//    }

}
