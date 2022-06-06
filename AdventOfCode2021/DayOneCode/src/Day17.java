import ecs100.UI;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day17 {

    private Map<Point2D, Integer> grid = new HashMap<>();//row col


    public void runDay17One(){
        try{
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input17.txt")));
            String targetInfo = allLines.get(0).replaceAll("[^\\d-]", " ");
            Scanner sc = new Scanner(targetInfo);
            int xLeft = sc.nextInt();
            int xRight = sc.nextInt();
            int yBot = sc.nextInt();
            int yTop = sc.nextInt();
            UI.println(xLeft);
            UI.println(xRight);
            UI.println(yBot);
            UI.println(yTop);

            for(int r = yBot; r <= yTop; r++){
                for(int c = xLeft; c <= xRight; c++){
                    grid.put(new Point2D.Double(c, r), 1);
                }
            }
            launch(xLeft, xRight, yBot, yTop);


        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public int getXResistance(double xSpeed){
        //
        if(xSpeed > 0){
            return -1;
        }
        else if(xSpeed < 0){
            return  1;
        }
        return  0;
    }

    public void launch(int xLeft, int xRight, int yBot, int yTop){
        //
        int count = 0;
        Point2D pos = new Point2D.Double(0,0);
        Point2D velocity = new Point2D.Double(0,0);
        int resistance = -1;
        //choose starting velocity
        int maxHeight = Integer.MIN_VALUE;

        int xRange = Math.max(Math.abs(xLeft), Math.abs(xRight));
        int yRange = Math.max(Math.abs(yBot), Math.abs(yTop));


        for(int y = -1000; y < 1000; y++){
            //
            for(int x = -1000; x < 1000; x++){
                //
                pos.setLocation(0,0);
                velocity.setLocation(x, y);

                while(pos.getX() <= xRight && pos.getY() >= yBot){

                    pos.setLocation(pos.getX() + velocity.getX(), pos.getY() + velocity.getY());

                    if(pos.getY() > maxHeight){
                        maxHeight = (int) pos.getY();
                    }

                    velocity.setLocation(velocity.getX() + getXResistance(velocity.getX()), velocity.getY()-1);

                    if(grid.containsKey(new Point2D.Double(pos.getX(), pos.getY()))){
                        //UI.println("velocity used: " + x + " " +  y + "     max height reached: " + maxHeight);
                        //UI.println(pos);
                        count++;
                        break;//if target area is reached
                    }

                }

            }
        }

        UI.println("done: " + count);

        //stop if pos x > xRight or posy < yBot

    }

    public static void main(String[] args) {
        // write your code here
        Day17 d17 = new Day17();
        d17.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay17One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
