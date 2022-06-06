import ecs100.UI;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day14 {

    Map<String, String> instructions = new HashMap<>();

    Map<String, BigInteger> newSequence = new HashMap<>();
    Map<String, BigInteger> letterCounts = new HashMap<>();

    public void runDay14One() {
        //
        UI.clearText();
        instructions.clear();
        letterCounts.clear();
        newSequence.clear();
        try {

            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input14.txt")));
            String original = allLines.remove(0);

            for (int i = original.length() - 2; i >= 0; i--) {//get all pairs
                //
                StringBuilder pair = new StringBuilder();
                pair.append(original.charAt(i)).append(original.charAt(i + 1));
                //add pair into a collection
                if (!newSequence.containsKey(pair.toString())) {
                    //
                    newSequence.put(pair.toString(), BigInteger.ONE);
                } else {
                    newSequence.put(pair.toString(), newSequence.get(pair.toString()).add(BigInteger.ONE));
                }

                String key = String.valueOf(original.charAt(i + 1));
                if (!letterCounts.containsKey(key)) {
                    //
                    letterCounts.put(key, BigInteger.ONE);
                } else {
                    letterCounts.put(key, letterCounts.get(key).add(BigInteger.ONE));
                }

            }
            String key = String.valueOf(original.charAt(0));
            if (!letterCounts.containsKey(key)) {
                //
                letterCounts.put(key, BigInteger.ONE);
            } else {
                letterCounts.put(key, letterCounts.get(key).add(BigInteger.ONE));
            }

            for (String line : allLines) {//get all the instructions
                //
                Scanner sc = new Scanner(line);
                if (sc.hasNext()) {
                    String bothSides = sc.next();
                    sc.next();
                    String middle = sc.next();
                    instructions.put(bothSides, middle);
                }
            }

            //first method
            UI.println(newSequence.entrySet());
            calculate();
            insert(40);
            calculate();

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void insert(int step) {
        //
        UI.println(step);
        if (step == 0) {
            UI.println(newSequence.keySet());
            UI.println(newSequence.entrySet());
            return;
        }

        //toRemove
        Map<String, BigInteger> tempMap = new HashMap<>();
        Map<String, BigInteger> toRemove = new HashMap<>();
        //Map<String, Integer> ogLetterCounts = new HashMap<>(letterCounts)l

        for (String key : instructions.keySet()) {
            if (newSequence.containsKey(key)) {
                BigInteger occurrences = newSequence.get(key);
                toRemove.put(key, occurrences);


                StringBuilder newVal1 = new StringBuilder();
                //NC
                newVal1.append(key.charAt(0)).append(instructions.get(key));
                //add to tempMap
                if (tempMap.containsKey(newVal1.toString())) {
                    tempMap.put(newVal1.toString(), tempMap.get(newVal1.toString()).add(occurrences));
                } else {
                    tempMap.put(newVal1.toString(), occurrences);
                }

                newVal1.setLength(0);//clear
                //CN
                newVal1.append(instructions.get(key)).append(key.charAt(1));
                if(tempMap.containsKey(newVal1.toString())){
                    tempMap.put(newVal1.toString(), tempMap.get(newVal1.toString()).add(occurrences));
                }
                else {
                    tempMap.put(newVal1.toString(), occurrences);
                }

                String toAdd = instructions.get(key);
                if (!letterCounts.containsKey(toAdd)) {
                    letterCounts.put(toAdd, BigInteger.ONE);
                } else {//THIS ISNT ADDING???
                    letterCounts.put(toAdd, letterCounts.get(toAdd).add(occurrences));} }

        }
        for (String key : toRemove.keySet()) {
            //if(newSequence.containsKey(key)){
            newSequence.put(key, newSequence.get(key).subtract(toRemove.get(key)));
            //}
        }

        //newSequence = tempMap;//NO, DO ADDALL
        for (String key : tempMap.keySet()) {
            if (newSequence.containsKey(key)) {
                newSequence.put(key, newSequence.get(key).add(tempMap.get(key)));
            } else {
                newSequence.put(key, tempMap.get(key));
            }
        }


        insert(step - 1);

    }

    public void calculate() {
        //
        //Collections.sort(letterCounts.values().tol);
        String maxLetter = letterCounts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        String minLetter = letterCounts.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        UI.println("max value: " + letterCounts.get(maxLetter) + "          min value: " + letterCounts.get(minLetter));
        UI.println("max value: " + maxLetter + "          min value: " + minLetter);
        UI.println("result = " + (letterCounts.get(maxLetter).subtract(letterCounts.get(minLetter))));

        //
    }

    public static void main(String[] args) {
        // write your code here
        Day14 d14 = new Day14();
        d14.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay14One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
