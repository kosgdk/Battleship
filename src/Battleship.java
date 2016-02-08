import Exceptions.GameOverException;

import java.util.Scanner;

public class Battleship {

    public static void main(String[] args) {

        Field field = new Field(10, 10, true);
        GameController gameController = new GameController(field);
        Computer computer = new Computer(field);

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
            field.setShip(ship);
        }

        field.drawField();

        Scanner scanner = new Scanner(System.in);

        String test = "";

        for (int i = 0; i < 100; i++) {
            computer.makeShot();
//            test = scanner.nextLine();
            if (gameController.checkGameOver()){
                System.out.println("Game over");
                break;
            }
        }


    }
}
