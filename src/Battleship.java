import java.util.Scanner;

public class Battleship {

    public static void main(String[] args) {

        Field computerField = new Field(10, 10, true);
        Field playerField = new Field(10, 10, false);
        Computer computer = new Computer(playerField);

        Ship[] computerShips = new Ship[]{
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
        Ship[] playerShips = new Ship[]{
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

        for (Ship ship : computerShips) {
            computerField.setShip(ship);
        }
        for (Ship ship : playerShips) {
            playerField.setShip(ship);
        }

        GameController gameController = new GameController(playerField, computerField, computer);

        gameController.startGame();

    }
}
