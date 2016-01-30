import java.util.ArrayList;
import java.util.Random;

public class Computer {

    Random random = new Random();
    private Field field;

    ArrayList<Coordinate> availableCoordinates;

    public Computer(Field field) {
        this.field = field;
        int availableCoordinatesSize = field.getFieldSizeX()* field.getFieldSizeY();
        availableCoordinates = new ArrayList<Coordinate>(availableCoordinatesSize);

        for (int x = 0; x < field.getFieldSizeX(); x++) {
            for (int y = 0; y < field.getFieldSizeY(); y++) {
                availableCoordinates.add(new Coordinate(x, y));
            }
        }
    }

    public void makeShot(){
        if(availableCoordinates.size() != 0){

            Coordinate coordinate = availableCoordinates.get(random.nextInt(availableCoordinates.size()));
            System.out.println("Computer shot: " + coordinate);
            availableCoordinates.remove(coordinate);
            field.makeShot(coordinate.getX(), coordinate.getY());

        }else {
            System.out.println("Game over");
        }
    }
}
