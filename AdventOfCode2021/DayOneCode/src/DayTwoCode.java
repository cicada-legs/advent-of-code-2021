import ecs100.UI;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class DayTwoCode {

    private Point2D.Double currentPos = new Point2D.Double(0,0);
    private double aim = 0;

    public void runDayTwoCodeOne() {
        UI.clearText();
        try {
            readFile(Files.readAllLines(Path.of("input2.txt")));
        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayTwoCodeTwo() {
        UI.clearText();
        readFileButDiff();
    }

    public void readFile(List<String> allLines){
        currentPos.setLocation(0,0);
        for(String line : allLines){
            int xChange = 0;
            int yChange = 0;
            Scanner scan = new Scanner(line);
            String direction = scan.next();
            int magnitude = scan.nextInt();

            if(direction.equals("forward")){
                //x value increase
                xChange = magnitude;
            }
            else if(direction.equals("down")){
                //y value increase
                yChange = magnitude;
            }
            else if(direction.equals("up")){
                //y value decrease
                yChange = magnitude*-1;
            }
            currentPos.setLocation(currentPos.x + xChange, currentPos.y + yChange);
            UI.println("current position: " + currentPos.x +"   "+ currentPos.y);
            scan.close();
        }

    }

    public void readFileButDiff(){
        //
        currentPos.setLocation(0,0);
        aim = 0;
        try {
            List<String> allLines = Files.readAllLines(Path.of("input2.txt"));
            //
            for(String line : allLines){
                Scanner scan = new Scanner(line);

                String direction = scan.next();
                int magnitude = scan.nextInt();

                if(direction.equals("forward")){
                    //x value increase
                    currentPos.setLocation(currentPos.x + magnitude, currentPos.y + aim*magnitude);
                }
                else if(direction.equals("down")){
                    aim = aim + magnitude;
                }
                else if(direction.equals("up")){
                    aim = aim - magnitude;
                }
                UI.println("current position: " + currentPos.x +"   "+ currentPos.y + "     aim: "+aim);
                UI.println("sum = " + currentPos.x * currentPos.y);
                scan.close();
            }

            //
        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public static void main(String[] args) {
        // write your code here
        DayTwoCode dtc = new DayTwoCode();
        dtc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Two Part 1", this::runDayTwoCodeOne);
        UI.addButton("Day Two Part 2", this::runDayTwoCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
