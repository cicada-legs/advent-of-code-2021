import ecs100.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DayFourCode {

    private ArrayList<Integer> numbersDraw = new ArrayList<>();
    private ArrayList<DayFourBoard> allBoards = new ArrayList<>();
    private Map<DayFourBoard, Double> winningBoards = new HashMap<>();
    private String[][] lastBoard = new String[5][5];
    private double winningNumber = 0;

    public void runDayFourCodeOne(){
        allBoards = new ArrayList<>();
        numbersDraw = new ArrayList<>();
        UI.clearText();
        processBoards();
        playBingo(true);
    }

    public void runDayFourCodeTwo(){
        allBoards = new ArrayList<>();
        numbersDraw = new ArrayList<>();
        winningBoards = new HashMap<>();
        UI.clearText();
        processBoards();
        playBingo(false);
        checkWinning();
    }

    public void processBoards(){
        try {
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input4.txt")));
            String num = "";
            for(int i = 0; i < allLines.get(0).length(); i++){
                //
                if(allLines.get(0).charAt(i) != ','){
                    num = num + allLines.get(0).charAt(i);

                    if(i == allLines.get(0).length()-1){
                        numbersDraw.add(Integer.valueOf(num));
                        num = "";
                    }
                }
                else{//if comma OR space
                    numbersDraw.add(Integer.valueOf(num));
                    //UI.println("drawn: " + num);
                    num = "";
                }
            }
            //good above
            DayFourBoard newBoard = new DayFourBoard(false);//!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            int index = 0;
            for(int i = 2; i < allLines.size(); i ++){
                //scan all lines of the file
                Scanner sc = new Scanner(allLines.get(i));//scanner for each line
                if(sc.hasNextInt()){//allLines.get(i).length() > 1
                    //add all row
                    int indexCol = 0;
                    while(sc.hasNextInt()){
                        newBoard.setValue(index, indexCol, String.valueOf(sc.nextInt()));
                        //UI.println(newBoard[index][indexCol]);
                        //UI.println(indexCol);
                        indexCol++;
                    }
                    //UI.println(index);
                    index++;
                }
                else{
                    //empty line means new board soon
                    index = 0;
                    DayFourBoard tempBoard = newBoard;
                    allBoards.add(tempBoard);
                    newBoard = new DayFourBoard(false);
                }

                if(i == allLines.size()-1){
                    DayFourBoard tempBoard = newBoard;
                    allBoards.add(tempBoard);
                }

            }

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void playBingo(boolean first){
        winningBoards = new HashMap<>();

        //
        for(Integer number : numbersDraw){// number draw
            //iterate though all numbers, in each board
            String drawnNum = String.valueOf(number);

            for(DayFourBoard board : allBoards){//iterate though each board, hits

                for(int x = 0; x < board.getLength(); x++){
                    for(int y = 0; y < board.getLength(); y++){
                        //
                        if(drawnNum.equals(board.getValue(x, y))  && !board.isFinished() ){
                            //UI.println(board[x][y]);
                            board.setValue(x, y, "hit");//

                            if(checkWin(board) && !board.isFinished()){//////////////////////////////////////////IF A BOARD HAS WON AFTER A NUMBER IS CALLED
                                double winningNum = number;
                                double sum = 0;
                                //add up all remaining nums on board
                                for(int i = 0; i < board.getLength(); i ++){
                                    for(int j = 0; j < board.getLength(); j ++){

                                        if(!board.getValue(i, j).equals("hit")){
                                            //
                                            sum = sum + Double.valueOf(board.getValue(i, j));
                                        }
                                        //board[x][y] = "hit";//??????????????????????????????????
                                    }
                                }
                                if(first){
                                    UI.println("num: " + winningNum + "   sum: " + sum);
                                    UI.println("final: " + sum*winningNum);
                                    //return;
                                }
                                //return;
                                board.finishBoard();
                                winningBoards.put(board, winningNum);
                            }
                            //UI.println(board[x][y]);
                        }
                    }
                }

            }
            //check wins


        }
    }

    public void checkWinning(){
        //
        for(DayFourBoard board : winningBoards.keySet()){
            double sum = 0, sumTest = 0;
            //add up all remaining nums on board

            for(int i = 0; i < board.getLength(); i ++){
                for(int j = 0; j < board.getLength(); j ++){

                    if(!board.getValue(i, j).equals("hit")){
                        //
                        sum = sum + Double.valueOf(board.getValue(i, j));
                    }

                }
            }
            UI.println("num: " + winningNumber + "   sum: " + sum);
                UI.println("final: " + sum*winningNumber);
        }
    }

    public boolean checkWin(DayFourBoard board){
        int countRow = 0, countCol = 0;
        //iterate though each board, hits
            //each board check win

            for(int x = 0; x < board.getLength(); x++){//check rows for win
                countRow = 0;

                for(int y = 0; y < board.getLength(); y++){
                    //
                    if(board.getValue(x, y).equals("hit")){//count++
                        countRow++;
                    }
                }
                if(countRow == 5){
                    return true;
                }
            }
            //check vert
            for(int x = 0; x < board.getLength(); x++){//check rows for win
                countCol = 0;
                for(int y = 0; y < board.getLength(); y++){
                    //
                    if(board.getValue(y, x).equals("hit")){//count++
                        countCol++;
                    }
                }
                if(countCol == 5){
                    return true;
                }
            }



        return false;
    }

    public static void main(String[] args) {
        // write your code here
        DayFourCode dfc = new DayFourCode();
        dfc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Four Part 1", this::runDayFourCodeOne);
        UI.addButton("Day Four Part 2", this::runDayFourCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
