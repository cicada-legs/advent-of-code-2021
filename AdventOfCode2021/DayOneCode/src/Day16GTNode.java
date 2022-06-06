import ecs100.UI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
//make e string?
public class Day16GTNode <E> implements Iterable <Day16GTNode<E>>{

    private final E item;
    private List<Day16GTNode<E>> children;

    /** Constructor for objects of class GTNode */
    public Day16GTNode(E item){
        this.item = item;
        this.children = new ArrayList<Day16GTNode<E>>();
    }

    /** Get item from node */
    public E getItem(){ return item; }

    /** Get number of children of node */
    public int numberOfChildren() {
        return children.size();
    }

    /** Get the i'th child of node */
    public Day16GTNode<E> getChild(int i){
        return children.get(i);
    }

    /** Add child at end */
    public void addChild(Day16GTNode<E> node){
        children.add(node);
    }

    /** Add child at position i */
    public void addChild(int i, Day16GTNode<E> node){
        children.add(i,node);
    }

    /** Remove child at position i */
    public Day16GTNode<E> removeChild(int i){
        return children.remove(i);
    }

    /** Set child at position i */
    public Day16GTNode<E> setChild(int i, Day16GTNode<E> node){
        return children.set(i, node);
    }

    /**
     * Enables foreach:
     *      for (GTNode<E> child : node){..do something to each child..}
     * to loop through the children of a node
     */
    public Iterator<Day16GTNode<E>> iterator(){
        return children.iterator();
    }

}

//necessary??
//public class ExpElem{
//    public final String operator;
//    public final double value;
//
//    /** Construct an Expr given an operator */
//    public ExpElem(String token){
//        operator = token;
//        value = Double.NaN;
//    }
//
//    /** Construct an ExpElem given a number */
//    public ExpElem(double v){
//        operator = "#";
//        value = v;
//    }
//
//    public String toString(){
//        return  (operator=="#")?""+value:operator;
//    }
//
//
//}
