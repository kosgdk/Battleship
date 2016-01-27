import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Battleship {



    public static void main(String[] args) {

        Field field = new Field();

        Ship ship = new Ship(4, "h");

        field.setShip(ship, 1, 1);

        field.drawField();

        field.makeShot(1, 1);
        field.makeShot(1, 2);
        field.makeShot(1, 3);
        field.makeShot(1, 4);


    }


}
