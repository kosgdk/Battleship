import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class Computer {

    Random random = new Random();
    private Field field;

    Coordinate lastCoordinate;

    ArrayList<Coordinate> availableCoordinates = new ArrayList<>(); // Все возможные для выстрела координаты
    TreeSet<Coordinate> currentShipCoordinates = new TreeSet<>();   // Координаты подбитых ячеек текущего корабля
    ArrayList<Coordinate> possibleCoordinatesForShot = new ArrayList<>();  // Возможные координаты для второго выстрела

    private boolean secondShotStarted, nextShotStarted;

    public Computer(Field field) {
        this.field = field;
        for (int x = 0; x < field.getFieldSizeX(); x++) {
            for (int y = 0; y < field.getFieldSizeY(); y++) {
                availableCoordinates.add(field.getCoordinateObject(x, y));
            }
        }
    }

    public void makeShot(){

        if(secondShotStarted){
            secondShot(lastCoordinate);
        }else if(nextShotStarted){
            nextShot(lastCoordinate);
        }

        currentShipCoordinates.clear();
        System.out.println("PC size" + possibleCoordinatesForShot.size());

        Coordinate coordinate = availableCoordinates.get(random.nextInt(availableCoordinates.size())); // Выбираем случайную координату из доступных

        System.out.println();
        System.out.println("Computer shot: " + coordinate);

        availableCoordinates.remove(coordinate);    // Удаляем текущую координату из доступных
        boolean shotResult = field.makeShot(coordinate);

        if (shotResult & !field.isShipDead(coordinate)){    // Если выстрел удачный и корабль ещё не убит
            currentShipCoordinates.add(coordinate); // Добавляем текущую координату в набор координат текущего корабля
            secondShot(coordinate);
        }else if (shotResult & field.isShipDead(coordinate)){   // Если выстрел удачный и корабль убит
            removeShipEdgesCoordinatesFromAvailableCoordinates(coordinate);
        }

    }

    private void secondShot(Coordinate coordinate){
        System.out.println("Second shot!"); /**DEBUG*/

        if (!secondShotStarted){
            secondShotStarted = true;
            possibleCoordinatesForShot.clear();
            int x = coordinate.getX();
            int y = coordinate.getY();
            possibleCoordinatesForShot.add(field.getCoordinateObject(x-1, y)); // Добавляем в список возможные координаты для второго выстрела
            possibleCoordinatesForShot.add(field.getCoordinateObject(x+1, y)); //
            possibleCoordinatesForShot.add(field.getCoordinateObject(x, y-1)); //
            possibleCoordinatesForShot.add(field.getCoordinateObject(x, y+1)); //
            checkPossibleCoordinates(possibleCoordinatesForShot);
            if(possibleCoordinatesForShot.size() == 0){
                makeShot();
            }
        }

        System.out.println("secondShotStarted = " + secondShotStarted); /**DEBUG*/
        System.out.println("Initial coordinate: " + coordinate); /**DEBUG*/
        System.out.println("Possible coordinates: " + possibleCoordinatesForShot); /**DEBUG*/

        Coordinate randomPossibleCoordinate = possibleCoordinatesForShot.get(0);
        System.out.println("Computer shot: " + randomPossibleCoordinate);
        availableCoordinates.remove(randomPossibleCoordinate);
        boolean shotResult = field.makeShot(randomPossibleCoordinate); // Стреляем в одну из возможных ячеек
        availableCoordinates.remove(randomPossibleCoordinate);
        possibleCoordinatesForShot.remove(randomPossibleCoordinate);

        if(shotResult & field.isShipDead(randomPossibleCoordinate)){         // Еслм выстрел удачный и корабль убит
            removeShipEdgesCoordinatesFromAvailableCoordinates(randomPossibleCoordinate);
            secondShotStarted = false;
            possibleCoordinatesForShot.clear();
        }else if(shotResult & !field.isShipDead(randomPossibleCoordinate)){ // Еслм выстрел удачный и корабль ещё жив
            currentShipCoordinates.add(randomPossibleCoordinate);
            nextShot(randomPossibleCoordinate);
        }else if(!shotResult){                                              // Если промах
            lastCoordinate = coordinate;
            secondShot(coordinate);
        }

    }

    private void nextShot(Coordinate coordinate){
        lastCoordinate = null;
        secondShotStarted = false;
        nextShotStarted = true;
        System.out.println("Next shot!"); /**DEBUG*/
        possibleCoordinatesForShot.clear();
        String shipOrientation = field.getShipOrientation(coordinate);

        // Определяем следующие крайние координаты в зависимости от ориентации корабля
        if(shipOrientation.equals("h")){
            possibleCoordinatesForShot.add(field.getCoordinateObject(currentShipCoordinates.first().getX(), currentShipCoordinates.first().getY()-1));
            possibleCoordinatesForShot.add(field.getCoordinateObject(currentShipCoordinates.last().getX(), currentShipCoordinates.last().getY()+1));
        }else{
            possibleCoordinatesForShot.add(field.getCoordinateObject(currentShipCoordinates.first().getX()-1, currentShipCoordinates.first().getY()));
            possibleCoordinatesForShot.add(field.getCoordinateObject(currentShipCoordinates.last().getX()+1, currentShipCoordinates.last().getY()));
        }
        checkPossibleCoordinates(possibleCoordinatesForShot);
        System.out.println("Possible coordinates: " + possibleCoordinatesForShot); /**DEBUG*/
        Coordinate randomPossibleCoordinate = possibleCoordinatesForShot.get(random.nextInt(possibleCoordinatesForShot.size())); // TODO: вынести randomPossibleCoordinate в класс
        System.out.println("Computer shot: " + randomPossibleCoordinate);
        availableCoordinates.remove(randomPossibleCoordinate);
        boolean shotResult = field.makeShot(randomPossibleCoordinate);

        if(shotResult & field.isShipDead(randomPossibleCoordinate)){            // Еслм выстрел удачный и корабль убит
            removeShipEdgesCoordinatesFromAvailableCoordinates(coordinate);
            nextShotStarted = false;
            possibleCoordinatesForShot.clear();
        }else if(shotResult & !field.isShipDead(randomPossibleCoordinate)){     // Еслм выстрел удачный и корабль ещё жив
            currentShipCoordinates.add(randomPossibleCoordinate);
            nextShot(randomPossibleCoordinate);
        }else if (!shotResult){                                                 // Если промах
            nextShot(coordinate);
        }
    }

    // Проверка набора возможных координат (не занята и валидна)
    private void checkPossibleCoordinates(ArrayList<Coordinate> possibleCoordinates){
        for(Iterator<Coordinate> iterator = possibleCoordinates.iterator(); iterator.hasNext(); ){  // Перебираем элементы списка
            Coordinate possibleCoordinate = iterator.next();

            if(!availableCoordinates.contains(possibleCoordinate)){
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
