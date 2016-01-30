import java.util.Scanner;

public class GameController {

    Field field;

    public GameController(Field field) {
        this.field = field;
    }

    void playerMove(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter coordinates in format X:Y :");
        checkInput(scanner.nextLine());
    }

    void checkInput(String input) {

        if (!input.equals("")) {
            System.out.println("OK");
        } else {
            System.out.println("NO!");
            playerMove();
        }

    }

}
