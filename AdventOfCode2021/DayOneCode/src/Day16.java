import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day16 {

    private StringBuilder binaryString = new StringBuilder();
    private int literalTotal = 0;
    private Map<String, String> dictionary = new HashMap<>();

    public void run() {
        UI.println("run");

    }

    public void runDay16One() {
        //
        binaryString = new StringBuilder();

        try {//move some stuff to readExpr

            //pass binaryString to function
            //change this
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input16.txt")));
            for (String line : allLines) {
                //iterate through each char
                for (int i = 0; i < line.length(); i++) {
                    //
                    binaryString.append(hexToBin(line.charAt(i)));
                }
            }
            //process stringbuilder function
            binaryString.replace(0, binaryString.length(), binaryString.toString().replace("", " ").trim());
            UI.println(binaryString);

            //give char by char?
            UI.println("result: " + evaluate(readExpr(new Scanner(binaryString.toString()))));
            //evaluate(String.valueOf(binaryString), 0, new StringBuilder(), 0);

            UI.println(allLines);


        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public String readNBits(Scanner sc, int n) {
        //
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //
            num.append(sc.nextInt());
        }
        return num.toString();
    }

    private long packetLength = 0;
    boolean stop = false;

    public Day16GTNode<ExpElem> readExpr(Scanner sc) {//passed whole string


        String version = binToDec(readNBits(sc, 3));
        packetLength += 3;
        String typeID = binToDec(readNBits(sc, 3));
        packetLength += 3;

        //node structure,
        //type id 4 is

        if (!typeID.equals("4")) {//HAS SUBPACKETS
            //new expression with children
            String operator = readNBits(sc, 1);
            packetLength += 1;

            //UI.println("entered operator: "+operator);
            Day16GTNode<ExpElem> node = new Day16GTNode<ExpElem>(new ExpElem(typeID));

            if (operator.equals("0")) {
                //
                String length = binToDec(readNBits(sc, 15));
                packetLength += 15;
                long end = Long.parseLong(length) + packetLength;
                //while
                while (packetLength != end) {//make sure this is updating
                    //
                    Day16GTNode<ExpElem> child = readExpr(sc);
                    node.addChild(child);
                    //soFar += packetLength - before;
                }

            } else if (operator.equals("1")) {
                //
                String subPackets = binToDec(readNBits(sc, 11));
                packetLength += 11;
                UI.println("packet length: "+packetLength);
                //HERE!!! MAY BE PADDING AT THE END NOT ACCOUNTED FOR

                for (long i = 0; i < Long.parseLong(subPackets); i++) {
                    //add to collection of subPackets??
                    Day16GTNode<ExpElem> child = readExpr(sc);
                    node.addChild(child);
                }
            }
            return node;
        }
        //if typeid is 4
        //returns single literal value

            String binValue = readLiteral(sc);
            packetLength = (int) (packetLength + 5 * (binValue.length() / 4.0));
            stop = true;

            Day16GTNode<ExpElem> literalNode = new Day16GTNode<ExpElem>(new ExpElem(typeID));
            literalNode.addChild(new Day16GTNode<ExpElem>(new ExpElem(binToDec(binValue), true)));



            return literalNode;//returns a literal value


    }

    public String readLiteral(Scanner sc) {

        StringBuilder header = new StringBuilder();
        StringBuilder literalValue = new StringBuilder();


        while (!header.toString().equals("0")) {
            //remove first
            header.replace(0, header.length(), readNBits(sc, 1));

            literalValue.append(readNBits(sc, 4));
        }
        return literalValue.toString();


    }


    public long evaluate(Day16GTNode<ExpElem> expr) {
        long result = 0;
        //if(expr == null){return (int) Double.NaN;}//0 or 1????
        String typeID = expr.getItem().getOperator();
        UI.println("operator: " + typeID);
        switch (typeID) {//MAKE GTNODE CLASS

            case "#"://no operator, literal value
                long literal = Long.parseLong((expr.getItem().literal));
                UI.println("literal value: " + literal);
                return literal;
            case "0"://sum value of subpackets\
                for (Day16GTNode<ExpElem> child : expr) {
                    result += evaluate(child);
                }
                return result;
            case "1":
                result = 1;
                for (Day16GTNode<ExpElem> child : expr) {
                    result *= evaluate(child);
                }
                return result;
            case "2":
                result = Long.MAX_VALUE;//
                for (Day16GTNode<ExpElem> child : expr) {
                    long childVal = (evaluate(child));
                    if (childVal < result) {//childVal < result
                        result = childVal;//test
                    }
                }
                return result;
            case "3":
                result = Integer.MIN_VALUE;//
                for (Day16GTNode<ExpElem> child : expr) {
                    long childVal = evaluate(child);
                    if (childVal > result) {//childVal > result
                        result = childVal;
                    }
                }
                return result;
            case "4"://for all children???
                UI.println("sum");

                for (Day16GTNode<ExpElem> child : expr) {
                    return evaluate(child);
                }
                //return Long.parseLong(binToDec(expr.getItem().literal));
            case "5":
                if (evaluate(expr.getChild(0)) > evaluate(expr.getChild(1))) {
                    return 1;
                } else {
                    return 0;
                }

            case "6":
                if (evaluate(expr.getChild(0)) < evaluate(expr.getChild(1))) {
                    return 1;
                } else {
                    return 0;
                }
            case "7":
                if (evaluate(expr.getChild(0)) == evaluate(expr.getChild(1))) {
                    return 1;
                } else {
                    return 0;
                }

        }
        return 0;
    }

    public String binToDec(String binary) {//TEST THIS
        long decValue = 0;
        long base = 1;

        //long temp = Long.parseLong(binary);

//        while (temp > 0) {
//            //
//            long lastDigit = temp % 10;
//            temp = temp / 10;
//            decValue += lastDigit * base;
//            base = base * 2;
//        }
        decValue = Long.parseLong(binary, 2);
        return String.valueOf(decValue);
    }

    public static String hexToBin(char hex) {
        //

        //mAP char to string

        long i = Long.parseLong(Character.toString(hex), 16);
        return String.format("%04d", Long.parseLong(Long.toBinaryString(i)));
    }

    public static void main(String[] args) {
        // write your code here
        Day16 d16 = new Day16();
        d16.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay16One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }


}
