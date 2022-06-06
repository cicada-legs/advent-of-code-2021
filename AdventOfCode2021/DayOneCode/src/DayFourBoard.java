public class DayFourBoard {

    //field for a single square object
    //field for marked
    private String[][] grid = new String[5][5];
    //private final double ID;
    private boolean finished = false;

    public DayFourBoard(boolean f) {
        finished = f;
    }

    public boolean isFinished (){
        return finished;
    }

    public void finishBoard(){
        finished = true;
    }

    public void setValue(int row, int col, String newVal){
        //
        grid[row][col] = newVal;
    }

    public String getValue(int row, int col){
        return grid[row][col];
    }

    public int getLength(){
        return grid[0].length;
    }

}
