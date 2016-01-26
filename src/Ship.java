public class Ship {

    String symbol = "O";
    private  int size;
    private String orientation;

    public Ship(int size, String orientation) {
        this.size = size;
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        return (symbol);
    }

}
