import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day24 {
    private Map<Integer, ArrayList<Expr>> instructionsMap = new HashMap<>();

    public static int[] xOffsets = { 12, 13, 13, -2, -10, 13, -14, -5, 15, 15, -14, 10, -14, -5 };

    public static int[] wOffsets = { 7, 8, 10, 4, 4, 6, 11, 13, 1, 8, 4, 13, 4, 14 };

    public static boolean[] hasDiv = { false, false, false, true, true, false, true, true, false, false, true, false,
            true, true };

    public static void main(String[] args) {
        // Part 1
        System.out.println(solveStep(13, Set.of(0), "", false));

        // Part 2
        System.out.println(solveStep(13, Set.of(0), "", true));
    }

    public static final List<Integer> SMALL_TO_LARGE = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    public static final List<Integer> LARGE_TO_SMALL = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1);

    private static String solveStep(int step, Set<Integer> okZ, String suffix, boolean smallestFirst) {

        if (step < 0) {
            return suffix;
        }

        List<Integer> order = smallestFirst ? SMALL_TO_LARGE : LARGE_TO_SMALL;
        for (int w : order) {
            Set<Integer> nextOkZ = new HashSet<>();
            for (int potZ = 0; potZ < 17576; potZ++) {
                int x = (potZ % 26) + xOffsets[step];
                int z = potZ;
                if (hasDiv[step]) {
                    z /= 26;
                }
                if (x != w) {
                    z = 26 * z + w + wOffsets[step];
                }
                if (okZ.contains(z)) {
                    nextOkZ.add(potZ);
                }
            }
            if (!nextOkZ.isEmpty()) {
                String rv = solveStep(step - 1, nextOkZ, Integer.toString(w) + suffix, smallestFirst);
                if (rv != null) {
                    return rv;
                }
            }
        }

        return null;
    }
////    public static void main(String[] args) {
////        // write your code here
////        Day24 d24 = new Day24();
////        d24.setupGUI();
////    }
//
//    public void setupGUI() {
//        UI.addButton("part 1", this::runDay24One);
//        //UI.addButton("Part 2", this::partTwo);
//        UI.addButton("Clear", UI::clearText);
//        UI.addButton("Quit", UI::quit);
//        UI.setDivider(1.0);
//    }

}
