public class Day19Point {

    private double xCoord;
    private double yCoord;
    private double zCoord;


    public Day19Point(double x, double y, double z){
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    public Day19Point(){
        this (0,0,0);
    }

    public double getxCoord() {
        return xCoord;
    }
    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }
    public double getyCoord() {
        return yCoord;
    }
    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }
    public double getzCoord() {
        return zCoord;
    }
    public void setzCoord(double zCoord) {
        this.zCoord = zCoord;
    }

    public double distanceTo(Day19Point p) {
        return Math.sqrt(Math.pow(xCoord - p.getxCoord(), 2) + Math.pow(yCoord - p.getyCoord(), 2) + Math.pow(zCoord - p.getzCoord(), 2));
    }
}