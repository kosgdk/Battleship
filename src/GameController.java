import Exceptions.GameOverException;
import Exceptions.InvalidCoordinateFormatException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameController {

    Field field;

    public GameController(Field field) {
        this.field = field;
    }

    void playerMove(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter coordinates in format X:Y :");
        try{
            field.makeShot(getCoordinateFromInput(scanner.nextLine()));
        }catch (InvalidCoordinateFormatException e){
            System.out.println("Are you sure you entered a valid coordinates? Try again.");
            playerMove();
        }
    }

    public Coordinate getCoordinateFromInput(String input) throws InvalidCoordinateFormatException {

        int x, y;

        if (!Pattern.matches(".*\\d+.*\\d+.*", input)) {
            throw new InvalidCoordinateFormatException();
        } else {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(input);

            matcher.find();
            x = Integer.parseInt(matcher.group());
            matcher.find();
            y = Integer.parseInt(matcher.group());

            if (x > field.getFieldSizeX() || y > field.getFieldSizeY()){
                throw new InvalidCoordinateFormatException();
            }
        }
        return field.getCoordinateObject(x, y);
    }

    public void checkGameOver() throws GameOverException {
        if(field.isAllShipsDead()){
            throw new GameOverException();
        }
    }

}

