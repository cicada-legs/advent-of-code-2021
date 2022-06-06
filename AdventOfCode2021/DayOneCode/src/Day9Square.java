public class Day9Square {

    private int height;
    private boolean visited;
    private int row, col;

    public Day9Square(int h, int r, int c){
        height = h;
        visited = false;
        row = r;
        col = c;
    }

    public void visit(){
        visited = true;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getHeight(){
        return height;
    }

    public boolean isVisited(){
        return visited;
    }

}
