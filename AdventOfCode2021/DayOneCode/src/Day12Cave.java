import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Day12Cave {

    private boolean small;
    private boolean visited = false;
    private boolean start, end;
    private Set<Day12Cave> neighbours = new HashSet<>();
    private String name;
    //boolean for if its the start or end

    public Day12Cave(String n, boolean s, boolean strt, boolean ed){
        //
        name = n;
        small = s;
        start = strt;
        end = ed;
    }

    public String getName(){
        return name;
    }

    public Set<Day12Cave> getNeighbours(){
        return Collections.unmodifiableSet(neighbours);
    }

    public void visit(){
        visited = true;
    }

    public boolean isVisited(){
        return visited;
    }

    public boolean isStart(){
        return start;
    }

    public boolean isEnd(){
        return end;
    }

    public boolean isSmall(){
        return small;
    }

    public void reset(){
        visited = false;
    }

    public void addNeighbour(Day12Cave node){
        neighbours.add(node);
    }

    public void removeNeighbour(Day12Cave node){
        neighbours.remove(node);
    }

}
