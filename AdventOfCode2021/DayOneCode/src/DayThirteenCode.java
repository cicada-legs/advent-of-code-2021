import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class DayThirteenCode {

    private Set<Point2D> paper = new HashSet<>();
    private Set<Point2D> uniquePoints = new HashSet<>();

    public void runDay13One() {
        //
        paper.clear();
        uniquePoints.clear();
        try {
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input13.txt")));

            for (String line : allLines) {
                String dotLoc = line.replaceAll(",", " ");
                Scanner sc = new Scanner(dotLoc);
                if (sc.hasNextInt()) {
                    double x = Double.parseDouble(sc.next());
                    double y = Double.parseDouble(sc.next());
                    Point2D point = new Point2D.Double(x, y);
                    //UI.println("x and y: " + x + "  " + y);
                    paper.add(point);
                } else if (sc.hasNext("fold")) {//????
                    String instruction = line.replaceAll("=", " ");
                    UI.println(instruction);
                    Scanner scan = new Scanner(instruction);
                    scan.next();//fold
                    scan.next();//along


                    if (scan.hasNext("y")) {//row
                        //
                        scan.next();
                        double foldLine = Double.parseDouble(scan.next());
                        //go through all points, if point y val is > foldLine

                        for (Point2D p : paper) {
                            //
                            if (p.getY() > foldLine) {
                                //
                                double oldX = p.getX();
                                double difference = foldLine - p.getY();
                                double newY = foldLine + difference;
                                p.setLocation(oldX, newY);
                            }
                            uniquePoints.add(p);
                        }

                    } else if (scan.hasNext("x")) {//col
                        //
                        scan.next();
                        double foldLine = Double.parseDouble(scan.next());

                        for (Point2D p : paper) {
                            //
                            if (p.getX() > foldLine) {
                                //
                                double oldY = p.getY();
                                double difference = foldLine - p.getX();
                                double newX = foldLine + difference;
                                p.setLocation(newX, oldY);
                            }
                            uniquePoints.add(p);
                        }

                    }
                    paper.clear();
                    paper.addAll(uniquePoints);
                }
            }


        } catch (IOException e) {
            UI.println("File reading failed");
        }
        print();
        drawPoints();
    }

    public void print() {

        for (Point2D p : paper) {
            UI.println("x and y: " + p.getX() + "   " + p.getY());
        }
        UI.println(paper.size());
    }

    public void drawPoints(){
        for (Point2D p : paper) {
            UI.fillOval(p.getX()*3, p.getY()*3, 5,5);
        }
    }

    public static void main(String[] args) {
        // write your code here
        DayThirteenCode d13 = new DayThirteenCode();
        d13.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay13One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}

//public class Point
//{
//    public int x;
//    public int y;
//
//    public Point(int x, int y) {this.x=x; this.y=y;}
//    public Point(){this(0,0);}
//
//    @Override
//    public boolean equals(Object obj)
//    {
//        if(obj == null) return false;
//        if(obj == this) return true;
//        if(obj.getClass() != Point.class) return false;
//        Point other = (Point) obj;
//        return other.x==this.x && other.y==this.y;
//    }
//}
//
