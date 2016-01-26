import java.util.HashMap;

public class Ship {


    boolean dead;

    HashMap<String, ShipCell> cellsMap = new HashMap<String, ShipCell>();

    int size;
    int injuredCells;

    private String orientation;

    public Ship(int size) {
        this.size = size;
        this.orientation = Math.random() > 0.5 ? "v" : "h";
    }

    public String getOrientation() {
        return orientation;
    }

    public int getSize() {
        return size;
    }

    public void setCell(int x, int y) {
        cellsMap.put(x+":"+y, new ShipCell(x, y));
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

    public Coordinate[][] getSurroundCoordinates() {

        Coordinate[][] coordinates;

        if(orientation.equals("v")) {
            coordinates = new Coordinate[size+2][2];
        }
        else {
            coordinates = new Coordinate[2][size+2];
        }
        return coordinates;
    }

}
