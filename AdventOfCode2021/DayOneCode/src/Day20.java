import ecs100.UI;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20 {

    private Map<Integer, Integer> lightAlgorithm = new HashMap<>();
    private Set<Point2D.Float> brightPixels = new HashSet<>();//0 and 1

    public void runDay20One() {
        try {
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("input20.txt")));

            String algorithm = allLines.remove(0);
            allLines.remove(0);
            int digit = 0;
            for (int i = 0; i < algorithm.length(); i++) {
                digit = 0;
                if (algorithm.charAt(i) == '#') {
                    digit = 1;
                }
                lightAlgorithm.put(i, digit);
            }

            for (int row = 0; row < allLines.size(); row++) {

                for (int col = 0; col < allLines.get(row).length(); col++) {

                    if (allLines.get(row).charAt(col) == '#') {
                        brightPixels.add(new Point2D.Float(row, col));
                    }
                }
            }
            //twice
            Set<Point2D.Float> enhancedImg = enhanceImage(brightPixels, 50, 1);
            UI.println("num of unique pixels   " + enhancedImg.size());


//            for(int i = -5; i < 10;i++){
//
//                for(int j = -5; j < 10; j++){
//
//                    if(i ==0 && j ==0){
//                        UI.print("o");
//                    }
//                    else if(enhancedImg.contains(new Point2D.Float(i, j))){
//                        UI.print("#");
//                    }
//                    else{
//                        UI.print(".");
//                    }
//
//                }
//                UI.println();
//            }


        } catch (IOException e) {
            UI.println("File reading failed");
        }
    }

    public Set<Point2D.Float> enhanceImage(Set<Point2D.Float> original, int times, int uniqueDigit) {//passed a map


        //UI.println("size: " + original.size());
        if(times <= 0){
            return original;
        }

        Set<Point2D.Float> enhanced = new HashSet<>();
        Set<Point2D.Float> surrounding = new HashSet<>();
        //uniqueDigit = 0;
        //UI.println(uniqueDigit);

        int midRow;
        int midCol;
        int dictionaryDigit;
        StringBuilder binaryString;

        for (Point2D uniquePix : original) {
            //
            midRow = (int) uniquePix.getX();
            midCol = (int) uniquePix.getY();
            binaryString = new StringBuilder();


                for (int r = midRow - 1; r <= midRow + 1; r++) {
                    for (int c = midCol - 1; c <= midCol + 1; c++) {
                        surrounding.add(new Point2D.Float(r,c));
                        if(original.contains(new Point2D.Float(r,c))){
                            //unique
                            binaryString.append(uniqueDigit);
                        }
                        else{
                            //infinite void pixel
                            binaryString.append(oppositeDigit(uniqueDigit));
                        }
                    }
                }
                //if(midCol == 1 && midRow == 1){
//                    UI.print("binaryOrigin    "+binaryString);
//                    UI.println("  row and col   " + midRow + "   " + midCol);
//                //}
//                {
//                   // UI.println("row and col   " + midRow + "   " + midCol);
//                }
                //surrounding.remove(new Point2D.Float(midRow, midCol));
                //check if replacement pixel is light or dark

                dictionaryDigit = lightAlgorithm.get(binToDec(binaryString.toString()));
                if(dictionaryDigit != uniqueDigit){
                    enhanced.add(new Point2D.Float(midRow, midCol));
                }
                //middle digit processing end


        }
        for(Point2D.Float s : surrounding){
            //
            midRow = (int) s.getX();
            midCol = (int) s.getY();
            binaryString = new StringBuilder();

            for (int r = midRow - 1; r <= midRow + 1; r++) {
                for (int c = midCol - 1; c <= midCol + 1; c++) {
                    if(original.contains(new Point2D.Float(r,c))){
                        //unique
                        binaryString.append(uniqueDigit);
                    }
                    else{
                        //infinite void pixel
                        binaryString.append(oppositeDigit(uniqueDigit));
                    }
                }
            }
       //     if(midCol == 1 && midRow == 1){
//                UI.println("binary    "+binaryString + " " + binToDec(binaryString.toString()));
//       //     }
//            //else{
//                UI.println("surrounding row and col   " + midRow + "   " + midCol);
//           /// }
            //check if replacement pixel is light or dark
            dictionaryDigit = lightAlgorithm.get(binToDec(binaryString.toString()));

            if(dictionaryDigit != uniqueDigit){
                enhanced.add(new Point2D.Float(midRow, midCol));
            }
        }

        return enhanceImage(enhanced, times-1, oppositeDigit(uniqueDigit));
    }

    public int oppositeDigit(int digit){
        if(digit == 0){
            return 1;
        }
        else return 0;
    }

    public Integer binToDec(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static void main(String[] args) {
        // write your code here
        Day20 d20 = new Day20();
        d20.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay20One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }
}
