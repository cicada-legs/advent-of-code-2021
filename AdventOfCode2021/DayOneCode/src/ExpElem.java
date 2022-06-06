public class ExpElem {
    //String

    public String typeID;
    public String literal;

    public ExpElem(String id) {
        typeID = id;
    }

    public ExpElem(String lString, boolean binary) {
        literal = (lString);
        typeID = "#";//?????
    }

    public String getOperator() {
        return typeID;
    }

}
