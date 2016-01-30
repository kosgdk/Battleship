import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import Exceptions.*;

public class Field {

    Random random = new Random();

    HashMap<String, Coordinate> allCoordinates = new HashMap<>();
    HashMap<Coordinate, ShipAndCoordinate> shipsCoordinates = new HashMap<>();
    HashMap<Coordinate, ShipAndCoordinate> shipsEdgesCoordinates = new HashMap<>();
    HashSet<Coordinate> dotsCoordinates = new HashSet<>();

    private final String dotCell = " • ";
    private final String emptyCell = " - ";
    
    private final int fieldSizeX;
    private final int fieldSizeY;

    public Field(int fieldSizeX, int fieldSizeY) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;

        // Наполняем набор всех возможных координат ячеек поля
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                allCoordinates.put(x+":"+y, new Coordinate(x, y));
            }

        }
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
//                coordinates[i] = new Coordinate(x, y+i);
                coordinates[i] = getCoordinateObject(x, y+i);
            } else {
                checkCoordinates(x+i, y);
//                coordinates[i] = new Coordinate(x+i, y);
                coordinates[i] = getCoordinateObject(x+i, y);
            }
        }

        ship.setCell(coordinates);  // Передаём кораблю его координаты

        for (Coordinate coordinate : coordinates) {
            shipsCoordinates.put(coordinate, new ShipAndCoordinate(ship, coordinate));     // Записываем координаты корабля в коллекцию
        }

        calculateShipEdgesCoordinates(ship, x, y);

    }

    private void calculateShipEdgesCoordinates(Ship ship, int headX, int headY){

        for (int x = (headX-1); x <= (headX+ship.getXSize()); x++){           // Перебираем прилегающие к кораблю координаты
            for (int y = (headY-1); y <= (headY+ship.getYSize()); y++){
                if(!ship.getCoordinates().containsKey(getCoordinateObject(x, y))){              // Исключаем из набора координаты самого корабля
                    shipsEdgesCoordinates.put(getCoordinateObject(x, y), new ShipAndCoordinate(ship, getCoordinateObject(x, y)));
                }
            }
        }

    }

    // Проверка, свободна ли ячейка поля.
    private boolean checkCoordinates(int x, int y) throws CoordinateIsNotEmptyException{
        if(shipsCoordinates.containsKey(getCoordinateObject(x, y)) || shipsEdgesCoordinates.containsKey(getCoordinateObject(x, y)) ) {
            throw new CoordinateIsNotEmptyException();
        }
        return true;
    }

    public void makeShot(int x, int y) {

        if(shipsCoordinates.containsKey(getCoordinateObject(x, y))){
            shipsCoordinates.get(getCoordinateObject(x, y)).getShip().gotShot(getCoordinateObject(x, y));  // Если набор координат кораблей содержит текущую координату - делаем выстрел.
        }else{
            dotsCoordinates.add(getCoordinateObject(x, y));  // Если в текущей координате нет корабля - добавляем её в набор с координатами "точек".
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

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x+"|");            // Отрисовываем вертикальный разделитель

            for (int y = 0; y < fieldSizeY; y++) {

                if(shipsCoordinates.containsKey(getCoordinateObject(x, y))){
                    System.out.print(" "+ shipsCoordinates.get(getCoordinateObject(x, y)).getShip().drawShip(getCoordinateObject(x, y))+" ");  // Если в текущей ячейке поля корабль - рисуем его.
                }else if(dotsCoordinates.contains(getCoordinateObject(x, y))){
                    System.out.print(dotCell);
                }else{
                    System.out.print(emptyCell);    // Если текущая ячейка поля пуста, рисуем дефолтное представление
                }
            }
            System.out.println();
        }
    }

    public int getFieldSizeX() {
        return fieldSizeX;
    }

    public int getFieldSizeY() {
        return fieldSizeY;
    }

    public Coordinate getCoordinateObject(int x, int y){
        return allCoordinates.get(x+":"+y);
    }

    public void getShipsCoordinates(){
        for (Coordinate coordinate : shipsCoordinates.keySet()) {
            System.out.print(coordinate);
        }
    }

    public void getShipsEdgesCoordinates(){
        for (Coordinate coordinate : shipsEdgesCoordinates.keySet()) {
            System.out.print(coordinate);
        }
    }

}
