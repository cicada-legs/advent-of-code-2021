import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day18 {

    private final ArrayList<Day18Node> snailNumberList = new ArrayList<>();
    //public Day18Node root;

    public void runDay18One() {
        //

        try {

            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(Path.of("fuck.txt")));

            //replace the commas with spaces??
            //print evaluate(readExpr)
            for (String line : allLines) {
                String pair = line.replaceAll(",", " ").replaceAll("]", " ").replaceAll("", " ");
                snailNumberList.add(readExpr(new Scanner(pair)));
            }

            //UI.println(snailNumberList);
            //ArrayDeque<Day18Node> queue = new ArrayDeque<>(snailNumberList);
            //UI.println(queue);
            addition(snailNumberList);

        } catch (IOException e) {
            UI.println("File reading failed");
        }

    }

    public Day18Node readExpr(Scanner sc) {

        String type = sc.next();
        Day18Node node = new Day18Node(type);

        if (type.equals("[")) {
            //
            Day18Node leftCh = readExpr(sc);
            Day18Node rightCh = readExpr(sc);
            node.setChildren(leftCh, rightCh);
            //UI.println(" eheheh "+node);
        }

        return node;

    }

    public void addition(ArrayList<Day18Node> allNums) {
        //new node concatenating two things to add
        Day18Node sum;

        while (!allNums.isEmpty()) {
            sum = new Day18Node("[", allNums.remove(0), allNums.remove(0));

            loopTree(sum, "", 0);

            getLayers(sum,0);//find any deep layers
            while (notReduced(sum)) {//NOT COMPLETING LAST ACTION
                UI.println("explode");
                explode(sum);
                getLayers(sum, 0);
                if(!notReduced(sum)){
                    UI.println("break");
                    break;
                }
                split(sum);
                UI.println("split");
                if(!notReduced(sum)){
                    UI.println("break 2");
                    break;
                }
            }

            explode(sum);


            UI.println("new value: ");
            loopTree(sum, "", 0);
        }

        //make a tree of the equation
        //explode left ot right with pre order trsversal
    }

    public void loopTree(Day18Node node, String label, int layer) {

        if (node != null) {
            for (int x = 0; x < layer; x++) {
                UI.printf("  ");
            }//add number of indentations depending on which layer the node is in
            UI.printf(label + node.getText());
            if (!node.isNum()) {
                UI.printf(" [parent]");
            }//append question mark if current node is a question
            UI.println();

            loopTree(node.getLeftChild(), "left: ", layer + 1);//print labels depending on whether the answer is yes or no
            loopTree(node.getRightChild(), "right: ", layer + 1);
        }

    }

    public boolean notReduced(Day18Node root) {

        if(root == null){
            return false;//??
        }

        if (root.getLayer() >= 4) {
            UI.println("layer: " + root.getLayer());
            return true;
        }
        if (root.isNum()) {
            if (Integer.parseInt(root.getText()) > 9) {
                return true;
            }
        }

        return notReduced(root.getLeftChild()) || notReduced(root.getRightChild());
    }

    public void split(Day18Node root) {//replace big num with a pair
        if (root != null) {
            Stack<Day18Node> toDo = new Stack<>();
            //Stack<Day18Node> leftVisited = new Stack<>();
            Day18Node node = root;

            while (node != null || !toDo.isEmpty()) {

                if (node != null) {//includes the numbers
                    toDo.push(node);
                    node = node.getLeftChild();
                } else {
                    node = toDo.pop();
                    Day18Node parent = node.getParent();

                    if (node.isNum() && Integer.parseInt(node.getText()) > 9) {
                        //
                        boolean isLeft = (parent.getLeftChild() == node);

                        Day18Node newNode;
                        int bigNum = Integer.parseInt(node.getText());
                        if (bigNum % 2 == 0) {
                            //even split
                            bigNum = (int) (bigNum * 0.5);
                            newNode = new Day18Node("[", new Day18Node(String.valueOf(bigNum)), new Day18Node(String.valueOf(bigNum)));
                        } else {
                            //
                            int smallNum = (int) ((bigNum - 1) * 0.5);
                            int otherNum = smallNum + 1;
                            newNode = new Day18Node("[", new Day18Node(String.valueOf(smallNum)), new Day18Node(String.valueOf(otherNum)));

                        }
                        //node.setNewValue(newNode);//test this
                        if (isLeft) {
                            parent.setChildren(newNode, parent.getRightChild());
                        } else {
                            parent.setChildren(parent.getLeftChild(), newNode);
                        }
                    }

                    node = node.getRightChild();
                }

            }

        }
    }

    public void getLayers(Day18Node node, int layer) {//surface is layer 1!

        if (node != null && !node.isNum()) {
            //UI.println(node.getText()+":   "+layer);

            if(node.getLeftChild().isNum()){
                node.getLeftChild().setLayer(layer);
            }
            if(node.getRightChild().isNum()){
                node.getRightChild().setLayer(layer);
            }

            getLayers(node.getLeftChild(), layer + 1);
            getLayers(node.getRightChild(), layer + 1);

        }


    }

    public void explode(Day18Node root) {//DOES POST VS IN ORDER MATTER HERE??
        if (root != null) {//change later to isNum??
            Stack<Day18Node> toDo = new Stack<>();
            Stack<Day18Node> leftNumStack = new Stack<>();
            //Stack<Day18Node> rightNumStack = new Stack<>();
            Day18Node node = root;

            while (node!=null || !toDo.isEmpty()) {

                if (node!=null) {//includes the numbers

                    toDo.push(node);
                    node = node.getLeftChild();
                } else  {
                    node = toDo.pop();
                    //UI.println("todo Stack pop   "+node.getText());

                    if(node.getLayer() >= 4){
                        //
                        int oldLeft;
                        int oldRight;
                        Day18Node parent = node.getParent();
                        //if both children are literals
                        if(node.isNum() && parent.getOtherChild(node).isNum()){//??
                            toDo.remove(parent);///?????????????

                            oldLeft = Integer.parseInt(parent.getLeftChild().getText());
                            oldRight = Integer.parseInt(parent.getRightChild().getText());

                            //both of these will always be nums
                            Day18Node rightAffected;
                            Day18Node leftAffected;

                            if(!leftNumStack.isEmpty()){
                                leftAffected = leftNumStack.peek();
                                oldLeft = oldLeft + Integer.parseInt(leftAffected.getText());
                                leftAffected.clearChildren(String.valueOf(oldLeft));
                            }
                            if(!toDo.isEmpty()){//SOMETHING WRING HERE OTHERWISE GOOD
                                //UI.print("right explode: " + oldRight + " + "  + Integer.parseInt(rightNumStack.peek().getText()));
                                rightAffected = toDo.peek().getNumChild();
                                oldRight = oldRight + Integer.parseInt(rightAffected.getText());
                                rightAffected.clearChildren(String.valueOf(oldRight));
                                //UI.println( "   = " + oldRight);
                                //rightNumStack.pop();
                            }
                            //UI.println("oldValue of parent  " + parent.getText());

                            parent.clearChildren("0");
                           // UI.println("new value of parent " + parent.getText());
                        }
                    }

                    if(node.isNum()){
                        //UI.println("leftStackNum " + node.getText());
                        leftNumStack.push(node);
                    }


                    node = node.getRightChild();

                }

            }

        }

    }

    public boolean inSubtree(Day18Node top, Day18Node target) {
        if (top == target) {//if subtree contains node
            return true;
        }
        if (top.getLeftChild() == target || top.getRightChild() == target) {//if any of the nodes are equal to the target (they are the same node)
            return true;
        }
        if (inSubtree(top.getLeftChild(), target) || inSubtree(top.getRightChild(), target)) {//recursive call for all team members to check all subtrees of pos
            return true;
        }


        return false;//if no matches found
    }

    public Day18Node findNextNum(Stack<Day18Node> stack){
        Day18Node numNode;
        while(!stack.isEmpty()){
            numNode = stack.pop();
            if(numNode.isNum()){
                return numNode;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // write your code here
        Day18 d18 = new Day18();
        d18.setupGUI();
    }

    public void setupGUI() {
        UI.addButton("part 1", this::runDay18One);
        //UI.addButton("Part 2", this::partTwo);
        UI.addButton("Clear", UI::clearText);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0);
    }

}
