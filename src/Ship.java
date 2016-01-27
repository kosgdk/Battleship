import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class Ship {

    TreeMap<String, ShipCell> cellsMap = new TreeMap<>();
    TreeSet<Coordinate> surroundCoordinates = new TreeSet<>();

    boolean dead;

    int size;
    int xSize;
    int ySize;
    int injuredCells;

    private String orientation;

    public Ship(int size) {
        this.size = size;
        this.orientation = Math.random() > 0.5 ? "v" : "h";
        setSizes(size, orientation);
    }

    public Ship(int size, String orientation) {
        this.size = size;
        this.orientation = orientation;
        setSizes(size, orientation);
    }

    private void setSizes(int size, String orientation){
        if(orientation.equals("v")){
            this.ySize = 1;
            this.xSize = size;
        } else {
            this.ySize = size;
            this.xSize = 1;
        }
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

    public void /*Coordinate[][]*/ getSurroundCoordinates() {
        int headX = cellsMap.get(cellsMap.firstKey()).getX();
        int headY = cellsMap.get(cellsMap.firstKey()).getY();


       for (int x = headX - 1; x < (xSize+1); x++){
           for (int y = headY - 1; y < (ySize+1); y++){
               surroundCoordinates.add(new Coordinate(x,y));
           }
       }

        Iterator iterator = surroundCoordinates.iterator();
       while (iterator.hasNext()){
           System.out.println(iterator.next()+" ");
       }


        /*
        Coordinate[][] coordinates;

        if(orientation.equals("v")) {
            coordinates = new Coordinate[size+2][3];
        }
        else {
            coordinates = new Coordinate[3][size+2];
        }


        for (int i = 0; i < coordinates.length; i++){
            System.out.println("");
            for (int j = 0; j < coordinates[0].length; j++){
                coordinates[i][j] = new Coordinate(headX, headY);
                System.out.print(coordinates[i][j].toString()+" ");
            }
        }
        */
//        return coordinates;
    }

    public void printCoordinates() {
        for (ShipCell shipCell : cellsMap.values()) {
            System.out.println(shipCell.getX()+":"+shipCell.getY());
        }
    }

}
