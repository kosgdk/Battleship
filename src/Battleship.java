public class Battleship {



    public static void main(String[] args) {

        Field field = new Field(10, 10);

        Ship[] ships = new Ship[]{
            new Ship(4),
            new Ship(3),
            new Ship(3),
            new Ship(2),
            new Ship(2),
            new Ship(2),
            new Ship(1),
            new Ship(1),
            new Ship(1),
            new Ship(1)
        };

        for (Ship ship : ships) {
            field.setShipRandom(ship);
        }

        field.drawField();


    }


}
