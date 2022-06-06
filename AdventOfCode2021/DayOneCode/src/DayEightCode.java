import ecs100.UI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.module.FindException;
import java.math.BigInteger;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class DayEightCode {

    private int[] digitCount = new int[10];

    public void runDayEightCodeOne() {
        //
        digitCount = new int[10];
        ArrayList<String> outPut = new ArrayList<>();

        try {
            int count = 0;
            for (String line : Files.readAllLines(Path.of("input8.txt"))) {

                Scanner sc = new Scanner(line);
                for (int i = 0; i < 10; i++) {
                    sc.next();
                }
                sc.next();//throw away delimiter

                while (sc.hasNext()) {
                    String next = sc.next();
                    outPut.add(next);
                }
            }
            //process digits
            process3Digits(outPut);
            //UI.println("--------------------------------------------");

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void runDayEightCodeTwo() {


        try {
            double total = 0;
            for (String line : Files.readAllLines(Path.of("input8.txt"))) {


                ArrayList<String> tenDigits = new ArrayList<>();//
                ArrayList<String> outPut = new ArrayList<>();
                Scanner sc = new Scanner(line);

                for (int i = 0; i < 10; i++) {
                    String input = sc.next();
                    tenDigits.add(input);
                }
                sc.next();//throw away delimiter
                while (sc.hasNext()) {
                    String next = sc.next();
                    outPut.add(next);
                }


                //process each line over and over ugggg
                //sort each digit as a char array

                String one = tenDigits.stream().filter(s -> s.length() == 2).findFirst().get();
                String four = tenDigits.stream().filter(s -> s.length() == 4).findFirst().get();
                String seven = tenDigits.stream().filter(s -> s.length() == 3).findFirst().get();
                String eight = tenDigits.stream().filter(s -> s.length() == 7).findFirst().get();
                String[] numbers = {null, one, null, null, four, null, null, seven, eight, null};

                for (String digit : tenDigits) {
                    //
                    if (digit.length() == 5) {//
                        //2 5

                        if(containsAllChars(digit, one)){
                            //3
                            numbers[3] = digit;
                        }
                        else if(containsAllChars(digit, remove(four, one))){
                            //5
                            numbers[5] = digit;
                        }
                        else{
                            numbers[2] = digit;
                        }

                    }
                    else if (digit.length() == 6) {//
                        //0 6 9

                        if(containsAllChars(digit, four)){
                            //9
                            numbers[9] = digit;
                        }
                        else if(containsAllChars(digit, seven)){
                            //0
                            numbers[0] = digit;
                        }
                        else{
                            numbers[6] = digit;
                        }

                    }


                    //fourDigNum = fourDigNum + processAllDigits(array);//return String
                }

                StringBuilder sp = new StringBuilder();
                for(String value : outPut){
                    //
                    for(int i = 0; i < 10; i++){
                        char[] arrayVal = numbers[i].toCharArray();
                        char[] valVal = value.toCharArray();
                        Arrays.sort(arrayVal);
                        Arrays.sort(valVal);

                        if(Arrays.equals(arrayVal, valVal)){
                            sp.append(i);
                            //UI.println(i + ":   " + value);
                        }
                    }
                    UI.println();
                }

                UI.println("4 digit number: " + sp);
                total = total + Double.parseDouble(sp.toString());

            }
            UI.println("Total: " + total);

        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public void process3Digits(List<String> outPut) {
        for (String digit : outPut) {
            //
            if (digit.length() == 2) {
                digitCount[1]++;
            } else if (digit.length() == 4) {
                digitCount[4]++;
            } else if (digit.length() == 3) {
                digitCount[7]++;
            } else if (digit.length() == 7) {
                digitCount[8]++;
            }

        }
//        UI.println("Ones: " + digitCount[1]);
//        UI.println("Fours: " + digitCount[4]);
//        UI.println("Sevens: " + digitCount[7]);
//        UI.println("Eights: " + digitCount[8]);
    }

    public boolean containsAllChars(String container, String containee){
        return container.length() - containee.length() == remove(container, containee).length();
    }

    public String remove(String input, String toRemove){
        //
        StringBuilder sb = new StringBuilder();
        for(char c : input.toCharArray()){
            if(!toRemove.contains(Character.toString(c))){
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        // write your code here
        DayEightCode dec = new DayEightCode();
        dec.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("Day Six Part 1", this::runDayEightCodeOne);
        UI.addButton("Day Six Part 2", this::runDayEightCodeTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
