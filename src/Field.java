import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;

public class Field {

    Random random = new Random();

    HashMap<String, ShipAndCoordinate> shipsCoordinates = new HashMap<>();
    HashMap<String, ShipAndCoordinate> shipsEdgesCoordinates = new HashMap<>();

    Ship[][] field = new Ship[10][10];
    
    public void getShipsCoordinates(){
        for (String coordinate : shipsCoordinates.keySet()) {
            System.out.print(coordinate+" ");
        }
    }

    public void getShipsEdgesCoordinates(){
        for (String coordinate : shipsEdgesCoordinates.keySet()) {
            System.out.print(coordinate+" ");
        }
    }

    public void setShip(Ship ship, int x, int y){

        for (int i = 0; i<ship.getSize(); i++) {
            if (ship.getOrientation().equals("h")) {
                field[x][y+i] = ship;
                ship.setCell(x, y+i);
                shipsCoordinates.put(x+":"+(y+i), new ShipAndCoordinate(ship, new Coordinate(x, y+i)));
            }
            else {
                field[x+i][y] = ship;
                ship.setCell(x+i, y);
                shipsCoordinates.put((x+i)+":"+y, new ShipAndCoordinate(ship, new Coordinate(x+i, y)));
            }
        }

        LinkedHashSet<Coordinate> edgesCoordinates = ship.getSurroundCoordinates();
        for (Coordinate coordinate : edgesCoordinates) {
            shipsEdgesCoordinates.put(coordinate.getX()+":"+coordinate.getY(), new ShipAndCoordinate(ship, coordinate));
        }
    }

    public void setShipRandom(Ship ship) {
        int maxXCoordinate = field.length;
        int maxYCoordinate = field[0].length;
        if (ship.getOrientation().equals("h")) {
            maxYCoordinate = field[0].length - ship.getSize() + 1;
        } else {
            maxXCoordinate = field.length - ship.getSize() + 1;
        }
        setShip(ship, random.nextInt(maxXCoordinate), random.nextInt(maxYCoordinate));
    }

    public void makeShot(int x, int y) {

        if(shipsCoordinates.containsKey(x+":"+y)){
            shipsCoordinates.get(x+":"+y).getShip().gotShot(x, y);
        }
        drawField();
    }

    public void drawField() {

        System.out.print("Y ");

        for (int i = 0; i < field.length; i++) {
            System.out.print(" "+i+" ");
        }

        System.out.println("");
        System.out.print("X ");

        for (int i = 0; i < field.length; i++) {
            System.out.print("---");
        }

        System.out.println("");

        for (int i = 0; i < field.length; i++) {
            System.out.print(i+"|");

            for (int j = 0; j < field[0].length; j++) {

                if(shipsCoordinates.containsKey(i+":"+j)){
                    System.out.print(" "+ shipsCoordinates.get(i+":"+j).getShip().drawShip(i, j)+" ");
                }else if(shipsEdgesCoordinates.containsKey(i+":"+j)){
                    System.out.print(" • ");
                }else{
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }



}
