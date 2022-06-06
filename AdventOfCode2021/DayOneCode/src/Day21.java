import ecs100.UI;

import java.util.*;
import java.util.stream.IntStream;

public class Day21 {

    private int[] diracDice = {1,2,3};
    private int rolls= 3;

    public void runDay21One(){

        Player p1 = new Player(5);
        Player p2 = new Player(9);
        int losingScore = 0;

        UI.println("before "+ Arrays.toString(diracDice));

        for(int i = 0; i < 100; i++){
            UI.println(getPos(1, i));
        }

        while(p1.getScore() < 1000 && p2.getScore() < 1000){
            //roll dice 3 times for both players
            p1.setPos(getPos(p1.pos, IntStream.of(diracDice).sum()));
            UI.println("p1 score   "+p1.updateScore(p1.pos));

            UI.println(diracDice);
            if(p1.getScore() >=1000){
                losingScore = p2.getScore();
                break;
            }

            incrementDie();

            p2.setPos(getPos(p2.pos, IntStream.of(diracDice).sum()));
            UI.println("p2 score   "+p2.updateScore(p2.pos));
            UI.println(diracDice);

            if(p2.getScore() >=1000){
                losingScore = p1.getScore();
                break;
            }

            incrementDie();
        }
        UI.println("player 1 score " + p1.getScore());
        UI.println("player 2 score " + p2.getScore());
        UI.println("result = " + (losingScore * rolls));
        UI.println("rolls " + rolls);

    }

    public void runDay21Two(){
        Player p1 = new Player(5);
        Player p2 = new Player(9);



    }

    //data struct: player score, player pos,

    public void splitUniverse(){
        //
        for(int i = 0; i < 3; i++){
            //diracDice[i];
        }

    }

    public void incrementDie(){
        diracDice = Arrays.stream(diracDice)
                .map(i -> (i +2)%100 + 1).toArray();

        rolls+=3;
    }

    public int getPos(int currentPos, int dieSum){
        UI.println("current : " + currentPos + " rolled " + dieSum + " " +((currentPos + dieSum -1) %10 +1));
        return (currentPos + dieSum -1) %10 +1;
    }

    public static void main(String[] args) {
        // write your code here
        Day21 d21 = new Day21();
        d21.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay21One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}

