public class Player {
    public int pos;
    public int score = 0;

    public Player(int start) {
        pos = start;
    }

    public int getScore(){
        return score;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int updateScore(int newIndex){
        //
        return score = score + newIndex;
    }
}
