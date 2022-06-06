import ecs100.UI;

public class Day18Node{

    Day18Node leftChild;
    Day18Node rightChild;
    Day18Node parent;
    int layer = 0;
    long value;
    String text;

    //HOW TO SET PARENTS

    public Day18Node(String txt) {//if bracket,
        text = txt;
    }

    public Day18Node(String txt, Day18Node yesChild, Day18Node noChild){
        text = txt;
        setChildren(yesChild, noChild);
    }

    public void setNewValue(Day18Node newNode){
        this.text = newNode.text;;
        this.leftChild = newNode.leftChild;
        this.rightChild = newNode.rightChild;
        this.parent = newNode.parent;
        this.layer = newNode.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
        if(this.isNum()){
            return;
        }
        if(leftChild.isNum()){
            leftChild.setLayer(layer);
        }
        if(rightChild.isNum()){
            rightChild.setLayer(layer);
        }
    }

    public int getLayer() {
        return layer;
    }

    public String getText(){
        return text;
    }

    public Day18Node getLeftChild() {
        return leftChild;
    }

    public Day18Node getRightChild() {
        return rightChild;
    }

    public void setLeftChild(String t){
        //
        this.getLeftChild().text = t;
    }

    public void setRightChild(String t) {
        this.rightChild.text = t;
    }

    public void clearChildren(String newText){
        text = newText;
        leftChild = null;
        rightChild = null;
    }

    public Day18Node getNumChild(){
        if(this.leftChild.isNum()){
            return leftChild;
        }
        else if (this.rightChild.isNum()){
            return this.rightChild;
        }
        else if(this.leftChild == null){
            throw new RuntimeException("this node is a num " + this.text);
        }
        throw new RuntimeException("neither children are nums");
    }

    public void setParent(Day18Node p){
        //
        parent = p;
    }

    public void setLeftChild(Day18Node newLeft){
        if(rightChild == null){
            throw new RuntimeException("LEft: cant set only one non null child");
        }
        leftChild = newLeft;
    }

    public void setRightChild(Day18Node newRight){
        if(leftChild == null){
            throw new RuntimeException("RIght: cant set only one non null child");
        }
        rightChild = newRight;
    }

    public Day18Node getOtherChild(Day18Node thisChild){
        //
        if(this.isNum()){
            UI.println("class error");
            return null;
        }
        if(this.getLeftChild() == thisChild){
            return this.getRightChild();
        }
        else{
            return this.getLeftChild();
        }
    }

    public Day18Node getParent(){
        return parent;
    }


    public void setChildren(Day18Node left, Day18Node right){
        if ((left==null) != (right == null)){
            throw new RuntimeException("Not allowed to have one null child");
        }

        leftChild = left;
        rightChild = right;

        leftChild.setParent(this);
        rightChild.setParent(this);
        //
    }

    public boolean isNum(){
        return (leftChild==null && rightChild == null);
    }

    public String toString(){
        String toReturn = "";
        if(this.isNum()){
            return text;
        }
        else{
            toReturn = toReturn + "[";
            toReturn = toReturn + this.getLeftChild().getText();
            toReturn = toReturn + " , " + this.getRightChild().getText();
            toReturn = toReturn + "]";
        }
        return toReturn;
    }

}

