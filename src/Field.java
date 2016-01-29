import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import Exceptions.*;

public class Field {

    Random random = new Random();

    HashMap<String, ShipAndCoordinate> shipsCoordinates = new HashMap<>();
    HashMap<String, ShipAndCoordinate> shipsEdgesCoordinates = new HashMap<>();
    HashMap<String, Coordinate> dotsCoordinates = new HashMap<>();

    private final String dotCell = " • ";
    private final String emptyCell = " - ";
    
    private final int fieldSizeX;
    private final int fieldSizeY;

    public Field(int fieldSizeX, int fieldSizeY) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
    }

    public int getFieldSizeX() {
        return fieldSizeX;
    }

    public int getFieldSizeY() {
        return fieldSizeY;
    }

    public void setShipRandom(Ship ship) {

        int maxXCoordinate = fieldSizeX;
        int maxYCoordinate = fieldSizeY;

        if (ship.getOrientation().equals("h")) {    // Ограничиваем возможные координаты поля в зависимости от размера корабля
            maxYCoordinate = fieldSizeY - ship.getSize() + 1;
        } else {
            maxXCoordinate = fieldSizeX - ship.getSize() + 1;
        }

        try {
            setShip(ship, random.nextInt(maxXCoordinate), random.nextInt(maxYCoordinate));
        }catch (CoordinateIsNotEmptyException e){
            this.setShipRandom(ship);
        }
    }

    // Установка корабля на поле.
    public void setShip(Ship ship, int x, int y) throws CoordinateIsNotEmptyException{

        Coordinate[] coordinates = new Coordinate[ship.getSize()];  // Массив координат корабля

        for (int i = 0; i<ship.getSize(); i++) {        // Расчёт координат корабля в зависимости от его ориентации
            if (ship.getOrientation().equals("h")) {
                checkCoordinates(x, y+i);
                coordinates[i] = new Coordinate(x, y+i);
            } else {
                checkCoordinates(x+i, y);
                coordinates[i] = new Coordinate(x+i, y);
            }
        }

        ship.setCell(coordinates);  // Передаём кораблю его координаты

        for (Coordinate coordinate : coordinates) {
            shipsCoordinates.put(coordinate.getX()+":"+coordinate.getY(), new ShipAndCoordinate(ship, coordinate));     // Записываем координаты корабля в коллекцию
        }

        for (Coordinate coordinate : ship.getSurroundCoordinates()) {
            shipsEdgesCoordinates.put(coordinate.getX()+":"+coordinate.getY(), new ShipAndCoordinate(ship, coordinate));    // Записываем координаты окружающих ячеек в коллекцию
        }
    }

    // Проверка, свободна ли ячейка поля.
    private boolean checkCoordinates(int x, int y) throws CoordinateIsNotEmptyException{
        if(shipsCoordinates.containsKey(x+":"+y) || shipsEdgesCoordinates.containsKey(x+":"+y) ) {
            throw new CoordinateIsNotEmptyException();
        }
        return true;
    }

    public void makeShot(int x, int y) {

        if(shipsCoordinates.containsKey(x+":"+y)){
            shipsCoordinates.get(x+":"+y).getShip().gotShot(x, y);  // Если набор координат кораблей содержит текущую координату - делаем выстрел.
        }else{
            dotsCoordinates.put(x+":"+y, new Coordinate(x, y));  // Если в текущей координате нет корабля - добавляем её в набор с координатами "точек".
        }
        drawField();
    }

    public void drawField() {

        System.out.println();

        System.out.print("Y ");

        for (int i = 0; i < fieldSizeX; i++) {  // Отрисовываем врхнюю строку с координатами Y
            System.out.print(" "+i+" ");
        }

        System.out.println("");
        System.out.print("X ");

        for (int i = 0; i < fieldSizeX; i++) {  // Отрисовываем горизонтальный разделитель
            System.out.print("---");
        }

        System.out.println("");

        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(i+"|");            // Отрисовываем вертикальный разделитель

            for (int j = 0; j < fieldSizeY; j++) {

                if(shipsCoordinates.containsKey(i+":"+j)){
                    System.out.print(" "+ shipsCoordinates.get(i+":"+j).getShip().drawShip(i, j)+" ");  // Если в текущей ячейке поля корабль - рисуем его.
                }else if(dotsCoordinates.containsKey(i+":"+j)){
                    System.out.print(dotCell);
                }else{
                    System.out.print(emptyCell);    // Если текущая ячейка поля пуста, рисуем дефолтное представление
                }
            }
            System.out.println();
        }
    }

    public void getShipsCoordinates(){
        for (String coordinate : shipsCoordinates.keySet()) {
            System.out.print(coordinate+" ");
        }
    }

    public void getShipsEdgesCoordinates(){
        for (String coordinate : shipsEdgesCoordinates.keySet()) {
            System.out.print(coordinate+" ");
        }
    }


}
