public class Range {
    private int low;
    private int high;

    public Range(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public boolean tooLarge(){
        return Math.abs(this.low) > 50 || Math.abs(this.high) > 50;
    }

    public int getAt(int i) {
        return this.low + i;
    }

    public int getSize(){
        return this.high - this.low + 1;
    }

    public boolean contains(int number) {
        return (number >= low && number <= high);
    }
}
