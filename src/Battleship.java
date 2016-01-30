
public class Battleship {

    public static void main(String[] args) {

        Field field = new Field(10, 10);
        GameController gameController = new GameController(field);

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

//        Computer computer = new Computer(field);
//        for (int i = 0; i < 1; i++) {
//            computer.makeShot();
//        }

        gameController.playerMove();

    }
}
