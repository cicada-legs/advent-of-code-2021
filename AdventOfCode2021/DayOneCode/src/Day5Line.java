import ecs100.UI;

import java.awt.geom.Point2D;

public class Day5Line {

    private Point2D.Double startPoint = new Point2D.Double(0,0);
    private Point2D.Double endPoint = new Point2D.Double(0,0);

    public Day5Line(Point2D.Double s, Point2D.Double e){
        startPoint.setLocation(s);
        endPoint.setLocation(e);
    }

    public void setStart(double x, double y){
        //
        startPoint.setLocation(x, y);
    }

    public int getX1(){
        return (int)startPoint.x;
    }
    public int getX2(){
        return (int)endPoint.x;
    }

    public int getY1(){
        return (int)startPoint.y;
    }
    public int getY2(){
        return (int)endPoint.y;
    }

    public boolean isHoriz(){
        return startPoint.getY() == endPoint.getY();
    }

    public boolean isVert(){
        return startPoint.getX() == endPoint.getX();
    }

    public boolean isDiag(){
        return this.getAngle() == 45;//fix later
    }

    public double getAngle(){
        //
        double diffX = endPoint.x - startPoint.x;
        double diffY = endPoint.y - startPoint.y;
        return Math.toDegrees(Math.atan2(Math.abs(diffX), Math.abs(diffY)));
    }

}
