import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DayTenCode {

    //bracket name then stack of opening brackets
    private Map<String, Stack<String>> typesAndStacks = new HashMap<>();
    private int total = 0;

    public void runDayTenCodeOne(){
        //
        total = 0;
        try{
            //each time a closing bracket is detected, find the index of its opening bracket and delete everything between them
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input10.txt")));
            ArrayList<String> allLinesMod = new ArrayList<>();

            for(String line : allLines){
                typesAndStacks.clear();//????????
                char[] bracketArr = line.toCharArray();
                List<String> bracketList = new ArrayList<>();

                for(char c : bracketArr){
                    bracketList.add(String.valueOf(c));
                }

                //each line
                boolean addTo = true;
                for(int i = 0; i < bracketList.size(); i++){
                    String bracket = bracketList.get(i);

                    if(!isOpening(bracket)){//WHAT TO DO WHEN BAD IS DETECTED? KEEP GOING?
                        //get the last index of its opening bracket
                        if(bracketList.get(i-1).equals(getOpening(bracket))){
                            //GOOD delete both remove i then i-1
                            bracketList.remove(i);
                            bracketList.remove(i-1);
                            i = i - 2;//?????
                        }
                        else{
                            //BAD
                            total += calculatePoints(bracket);
                            addTo = false;
                            break;
                        }
                    }

                }
                //allLinesMod.add;//????
                if(addTo){//change to ignore complete lists??
                    String listString = String.join("", bracketList);
                    allLinesMod.add(listString);
                    //UI.println(listString);
                }
            }
            UI.println("total: " + total);
            //do second part
            runDayTenCodeTwo(allLinesMod);

        }catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayTenCodeTwo(List<String> allLines){
        //
        List<Double> allScores = new ArrayList<>();
        for(String line : allLines){
            //reverse each string
            double totalScore = 0;
            char[] brackets = line.toCharArray();

            for(int i = brackets.length-1; i >= 0; i--){//remember were using opening brackets
                //
                totalScore = totalScore*5;
                switch (String.valueOf(brackets[i])) {
                    case "(" -> totalScore += 1;
                    case "[" -> totalScore += 2;
                    case "{" -> totalScore += 3;
                    case "<" -> totalScore += 4;
                };

            }
            //UI.println("lineScores: " + totalScore);
            allScores.add(totalScore);
        }

        Collections.sort(allScores);
        for(Double s : allScores){UI.println(s);}
        int mid = (allScores.size() - 1) / 2;
        UI.print("middle number: " + allScores.get(mid));

    }

    public boolean isOpening(String bracket){
        return bracket.equals("(") || bracket.equals("{") || bracket.equals("[") || bracket.equals("<");
    }

    public String getOpening(String closing){
        if(closing.equals(")")){
            return "(";
        }
        else if(closing.equals("]")){
            return "[";
        }
        else if(closing.equals("}")){
            return "{";
        }
        else if(closing.equals(">")){
            return  "<";
        }
        return "";
    }

    public int calculatePoints(String bracket){
        return switch (bracket) {
            case ")" -> 3;
            case "]" -> 57;
            case "}" -> 1197;
            case ">" -> 25137;
            default -> 0;
        };
    }


    public static void main(String[] args) {
        // write your code here
        DayTenCode dtc = new DayTenCode();
        dtc.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Six Part 1", this::runDayTenCodeOne);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
