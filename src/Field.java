import java.util.HashMap;
import java.util.Random;
import Exceptions.*;

public class Field {

    Random random = new Random();

    HashMap<String, ShipAndCoordinate> shipsCoordinates = new HashMap<>();
    HashMap<String, ShipAndCoordinate> shipsEdgesCoordinates = new HashMap<>();

    private final String dotCell = " • ";
    private final String emptyCell = " - ";
    
    private final int FIELDSIZEX;
    private final int FIELDSIZEY;

    public Field(int FIELDSIZEX, int FIELDSIZEY) {
        this.FIELDSIZEX = FIELDSIZEX;
        this.FIELDSIZEY = FIELDSIZEY;
    }

    public void setShipRandom(Ship ship) {

        int maxXCoordinate = FIELDSIZEX;
        int maxYCoordinate = FIELDSIZEY;

        if (ship.getOrientation().equals("h")) {    // Ограничиваем возможные координаты поля в зависимости от размера корабля
            maxYCoordinate = FIELDSIZEY - ship.getSize() + 1;
        } else {
            maxXCoordinate = FIELDSIZEX - ship.getSize() + 1;
        }

        try {
            setShip(ship, random.nextInt(maxXCoordinate), random.nextInt(maxYCoordinate));
        }catch (CoordinateIsNotEmptyException e){
            this.setShipRandom(ship);
        }
    }

    //Установка корабля на поле.
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

    private boolean checkCoordinates(int x, int y) throws CoordinateIsNotEmptyException{
        if(shipsCoordinates.containsKey(x+":"+y) || shipsEdgesCoordinates.containsKey(x+":"+y) ) {
//            System.out.println("Exception!");
            throw new CoordinateIsNotEmptyException();

        }
        return true;
    }

    public void makeShot(int x, int y) {

        if(shipsCoordinates.containsKey(x+":"+y)){
            shipsCoordinates.get(x+":"+y).getShip().gotShot(x, y);
        }
        drawField();
    }

    public void drawField() {

        System.out.println();

        System.out.print("Y ");

        for (int i = 0; i < FIELDSIZEX; i++) {  // Отрисовываем врхнюю строку с координатами Y
            System.out.print(" "+i+" ");
        }

        System.out.println("");
        System.out.print("X ");

        for (int i = 0; i < FIELDSIZEX; i++) {  // Отрисовываем горизонтальный разделитель
            System.out.print("---");
        }

        System.out.println("");

        for (int i = 0; i < FIELDSIZEX; i++) {
            System.out.print(i+"|");            // Отрисовываем вертикальный разделитель

            for (int j = 0; j < FIELDSIZEY; j++) {

                if(shipsCoordinates.containsKey(i+":"+j)){
                    System.out.print(" "+ shipsCoordinates.get(i+":"+j).getShip().drawShip(i, j)+" ");  // Если в текущей ячейке поля корабль - рисуем его.
//                }else if(shipsEdgesCoordinates.containsKey(i+":"+j)){
//                    System.out.print(dotCell);
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
