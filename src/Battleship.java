import java.util.HashMap;

public class Battleship {


    public static void main(String[] args) {

        Field field = new Field();

        Ship[] ships = {
//                new Ship(4),
//                new Ship(3),
//                new Ship(3),
//                new Ship(2),
//                new Ship(2),
//                new Ship(2),
//                new Ship(1),
//                new Ship(1),
//                new Ship(1),
                new Ship(2)
        };
        for (Ship ship : ships) {
            field.setShipRandom(ship);
        }


        field.drawField();

//        field.shot(0, 0);
//        field.shot(1, 0);
//        field.shot(2, 0);
//        field.shot(3, 0);
//        System.out.println();
//
//        field.drawField();

    }


}
