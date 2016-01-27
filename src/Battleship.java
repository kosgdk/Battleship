import java.util.SortedSet;
import java.util.TreeSet;

public class Battleship {


    SortedSet<String> stringset;

    public static void main(String[] args) {

        Field field = new Field();

        Ship ship = new Ship(4, "h");

        field.setShip(ship, 4, 4);

        field.drawField();

        ship.getSurroundCoordinates();

//        TreeSet<String> stringset = new TreeSet<String>();

//        for (int i = 0; i < 10; i++){
//            stringset.add(""+i);
//        }
//        System.out.println(stringset.add(""+15));
//
//        for (String s : stringset) {
//            System.out.println(s);
//        }


//        Ship[] ships = {
//                new Ship(4),
//                new Ship(3),
//                new Ship(3),
//                new Ship(2),
//                new Ship(2),
//                new Ship(2),
//                new Ship(1),
//                new Ship(1),
//                new Ship(1),
//                new Ship(2)
//        };
//        for (Ship ship : ships) {
//            field.setShipRandom(ship);
//        }
//
//        field.drawField();
//
//        field.shot(0, 0);
//        field.shot(1, 0);
//        field.shot(2, 0);
//        field.shot(3, 0);
//        System.out.println();
//
//        field.drawField();

    }


}
