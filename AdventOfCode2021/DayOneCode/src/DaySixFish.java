public class DaySixFish {

    private int timer;

    public DaySixFish(int startTime){
        //
        timer = startTime;
    }

    public int getTime(){
        return timer;
    }

    public void tick(){
        timer--;
    }

    public boolean isReady(){
        return timer == -1;
    }

    public void resetTimer(){
        timer = 6;
    }

}
