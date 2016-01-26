import java.util.HashMap;

public class Ship {


    boolean dead;

    HashMap<String, ShipCell> cellsMap = new HashMap<String, ShipCell>();

    int size;
    int injuredCells;

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

    public void setCoordinates(int x, int y) {
        cellsMap.put(x+":"+y, new ShipCell());
    }

    public String drawShip (int x, int y){
        return cellsMap.get(x+":"+y).toString();
    }

    public void gotShot(int x, int y){
        cellsMap.get(x+":"+y).setState("injured");
        injuredCells++;
        checkForDeath();
    }

    private void checkForDeath () {
       if (injuredCells == size) {
           dead = true;
           for (ShipCell shipCell : cellsMap.values()) {
               shipCell.setState("dead");
           }
       }
    }

}
