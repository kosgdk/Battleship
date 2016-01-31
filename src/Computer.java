import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Computer {

    Random random = new Random();
    private Field field;

    ArrayList<Coordinate> availableCoordinates;
    ArrayList<Coordinate> possibleCoordinatesForSecondShot = new ArrayList<>();  // Набор возможных координат

    private boolean secondShotStarted, secondShotCompleted;

    public Computer(Field field) {
        this.field = field;
        int availableCoordinatesSize = field.getFieldSizeX()* field.getFieldSizeY();
        availableCoordinates = new ArrayList<Coordinate>(availableCoordinatesSize);

        for (int x = 0; x < field.getFieldSizeX(); x++) {
            for (int y = 0; y < field.getFieldSizeY(); y++) {
                availableCoordinates.add(field.getCoordinateObject(x, y));
            }
        }
    }

    public void makeShot(){
//        if(availableCoordinates.size() != 0){

        Coordinate coordinate = availableCoordinates.get(random.nextInt(availableCoordinates.size())); // Выбираем случайную координату из доступных
        System.out.println();
        System.out.println("Computer shot: " + coordinate);
        availableCoordinates.remove(coordinate);    // Удаляем текущую координату из доступных
        boolean shotResult = field.makeShot(coordinate);
        if (shotResult & !field.isShipDead(coordinate)){    // Если выстрел удачный и корабль ещё не убит
            System.out.println("Second shot!"); /**DEBUG*/
            secondShot(coordinate);
        }else if (shotResult & field.isShipDead(coordinate)){   // Если выстрел удачный и корабль убит
            removeShipEdgesCoordinatesFromAvailableCoordinates(coordinate);
        }

//        }else {
//            System.out.println("Game over");
//        }
    }

    private void secondShot(Coordinate coordinate){
        System.out.println("secondShotStarted = " + secondShotStarted); /**DEBUG*/
        System.out.println("Initial coordinate: " + coordinate); /**DEBUG*/
        if (!secondShotStarted){
            secondShotStarted = true;
            possibleCoordinatesForSecondShot.clear();
            int x = coordinate.getX();
            int y = coordinate.getY();
            possibleCoordinatesForSecondShot.add(field.getCoordinateObject(x-1, y)); // Добавляем в список возможные координаты для второго выстрела
            possibleCoordinatesForSecondShot.add(field.getCoordinateObject(x+1, y)); //
            possibleCoordinatesForSecondShot.add(field.getCoordinateObject(x, y-1)); //
            possibleCoordinatesForSecondShot.add(field.getCoordinateObject(x, y+1)); //
            checkPossibleCoordinates(possibleCoordinatesForSecondShot);
        }

        System.out.println("Possible coordinates: " + possibleCoordinatesForSecondShot); /**DEBUG*/

        Coordinate randomPossibleCoordinate = possibleCoordinatesForSecondShot.get(0);
        System.out.println("Computer shot: " + randomPossibleCoordinate);
        boolean shotResult = field.makeShot(randomPossibleCoordinate); // Стреляем в одну из возможных ячеек
        availableCoordinates.remove(randomPossibleCoordinate);
        possibleCoordinatesForSecondShot.remove(randomPossibleCoordinate);

        if(shotResult & field.isShipDead(randomPossibleCoordinate)){
            removeShipEdgesCoordinatesFromAvailableCoordinates(randomPossibleCoordinate);
            secondShotStarted = false;
        }else if(!shotResult){
            secondShot(coordinate);
        }/**TODO: else if(shotResult & !field.isShipDead(randomPossibleCoordinate) // Еслм выстрел удачный и корабль ещё жив - вызов nextShot(), который определяет положение корабля и выбирает соответствующие возможные координаты для выстрела */

        secondShotStarted = false; /** Just for code to compile. REMOVE to work on nextShot()*/

    }

    // Проверка набора возможных координат (не занята и валидна)
    private void checkPossibleCoordinates(ArrayList<Coordinate> possibleCoordinates){

        for(Iterator<Coordinate> iterator = possibleCoordinates.iterator(); iterator.hasNext(); ){  // Перебираем элементы списка
            Coordinate possibleCoordinate = iterator.next();

            if(!field.checkCoordinatesForNextShot(possibleCoordinate)){
                iterator.remove(); // Если координата занята - удаляем её из набора
                continue;
            }

            if(!possibleCoordinates.contains(possibleCoordinate)){
                iterator.remove(); // Если координата не валидна - удаляем её из набора
            }
        }

    }

    private void removeShipEdgesCoordinatesFromAvailableCoordinates(Coordinate coordinate){
        if(field.isShipDead(coordinate)){
            for (Coordinate shipEdgeCoordinate : field.getShipEdgesCoordinates(coordinate)){
                availableCoordinates.remove(shipEdgeCoordinate);
            }
        }
    }

}
