
import ecs100.*;
import java.util.*;
import java.awt.Color;

public class Day11Oct implements Iterable<Day11Oct> {

    private int level;
    private Set<Day11Oct> neighbours;
    private int X, Y;
    private boolean flashed;

    public Day11Oct(int l, int x, int y){
        level = l;
        neighbours = new HashSet<>(8);
        X = x;
        Y = y;
        flashed = false;
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    public void increaseLevel(){
        level++;
    }

    public void doFlash(){
        level = 0;
        flashed = true;
        //increase neighbours levels
    }

    public boolean hasFlashed(){
        return flashed;
    }

    public void reset(){
        flashed = false;
        //if(level == 10){level = 0;}
    }

    public boolean isMax(){
        return level > 9;
    }

    public int getLevel(){
        return level;
    }

    public Iterator<Day11Oct> iterator() {
        return this.neighbours.iterator();
    }
}
