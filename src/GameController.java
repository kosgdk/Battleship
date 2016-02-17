import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameController {

    Field playerField;
    Field computerField;
    Computer computer;

    public GameController(Field playerField, Field computerField, Computer computer) {
        this.playerField = playerField;
        this.computerField = computerField;
        this.computer = computer;
    }

    void startGame(){
        while (!checkGameOver()) {

            // Ход игрока
            do {
                if (checkGameOver()){break;}
                drawFields();
            } while (playerMove());

            // Ход компьютера
            do {
                if (checkGameOver()){break;}
                drawFields();

                System.out.println("Press Enter for Computer to make shot...");
                Scanner scanner = new Scanner(System.in);
                String enter = scanner.nextLine();

            } while (computer.makeShot());
        }
        drawFields();
        whoWin();
    }

    boolean playerMove(){

        System.out.println("Your move.\nEnter coordinates in format X:Y :");
        boolean shotResult = false;
        Coordinate coordinate = getCoordinateFromInput();

            if (computerField.wasShotBefore(coordinate)){
                System.out.println("You shot here before. Try another coordinate.");
                playerMove();
            } else {
                System.out.println("You shoot: " + coordinate);
                shotResult = computerField.makeShot(coordinate);
            }

//        System.out.println("ShotResult = " + shotResult); /**DEBUG*/
        return shotResult;
    }

    public Coordinate getCoordinateFromInput() {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Coordinate coordinate = null;

        int x = 0, y = 0;

        if (!Pattern.matches(".*\\d+\\D+\\d+.*", input)) {
            System.out.println("Are you sure you entered a valid coordinates? Try again.");
            coordinate = getCoordinateFromInput();
        } else {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(input);

            matcher.find();
            x = Integer.parseInt(matcher.group());
            matcher.find();
            y = Integer.parseInt(matcher.group());

            if (x > computerField.getFieldSizeX()-1 || y > computerField.getFieldSizeY()-1){
                System.out.println("Are you sure you entered a valid coordinates? Try again.");
                coordinate = getCoordinateFromInput();
            } else {
                coordinate = computerField.getCoordinateObject(x, y);
                return coordinate;
            }
        }

        return coordinate;
    }

    void drawFields(){
        System.out.println();
        playerField.drawField();
        computerField.drawField();
        System.out.println("================================");

    }

    public boolean checkGameOver(){
        return playerField.isAllShipsDead() || computerField.isAllShipsDead();
    }

    private void whoWin(){
        if (playerField.isAllShipsDead()){
            System.out.println("Game over. Computer win.");
        } else if (computerField.isAllShipsDead()){
            System.out.println("Game over. You win! Congratulations!");
        }
    }

}

