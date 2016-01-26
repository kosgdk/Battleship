import java.util.HashMap;

public class Battleship {


    public static void main(String[] args) {

        Field field = new Field();

        Ship ship1 = new Ship(4, "v");

        field.setShip(ship1, 0, 0);

        field.drawField();

        field.shot(0, 0);
        field.shot(1, 0);
        field.shot(2, 0);
        field.shot(3, 0);
        System.out.println();

        field.drawField();
        System.out.println(ship1.injuredCells);
    }


}
