import java.util.Arrays;

public class Expr {

    private String op, first = null, second = null;
    private double numFirst, numSecond;
    private String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public Expr(String operator, String a, String b){
        op = operator;

        if(Arrays.asList(numbers).contains(a)){
            numFirst = Double.parseDouble(first);
        }
        else{
            first = a;
        }

        if(Arrays.asList(numbers).contains(b)){
            numSecond = Double.parseDouble(second);
        }
        else{
            second = b;
        }
    }

    public Expr(String operator, String inp){
        op = operator;
        numFirst = Double.parseDouble(inp);
    }

    public String getOp(){
        return op;
    }

    public void setFirstNum(double newVal){
        numFirst = newVal;
    }

    public void setSecondNum(double newVal){
        numSecond = newVal;
    }

    public double getNumFirst(){
        return numFirst;
    }

    public double getNumSecond() {
        return numSecond;
    }
}
