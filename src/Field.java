import java.util.*;

public class Field {

    private Random random = new Random();

    private HashMap<String, Coordinate> allCoordinates = new HashMap<>();   // Координаты всех ячеек поля
    private HashMap<Coordinate, Ship> shipsCoordinates = new HashMap<>();  // Координаты всех кораблей
    private HashMap<Coordinate, Ship> shipsEdgesCoordinates = new HashMap<>(); // Координаты прилегающих к кораблям ячеек
    private HashSet<Coordinate> dotsCoordinates = new HashSet<>();  // Координаты пустых ячеек, в которые стреляли
    private HashSet<Ship> ships = new HashSet<>();  // Корабли, установленные на поле

    private final String dotCell = " • ";   // Отображение пустой ячейки, в которую стреляли
    private final String emptyCell = " - "; // Отображение пустой ячейки
    
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

    public void setShip(Ship ship) {

        int maxXCoordinate = fieldSizeX;
        int maxYCoordinate = fieldSizeY;

        if (ship.getOrientation().equals("h")) {    // Ограничиваем возможные координаты поля в зависимости от размера корабля
            maxYCoordinate = fieldSizeY - ship.getSize() + 1;
        } else {
            maxXCoordinate = fieldSizeX - ship.getSize() + 1;
        }


        if(!setShip(ship, random.nextInt(maxXCoordinate), random.nextInt(maxYCoordinate))){
            setShip(ship);
        }
    }

    // Установка корабля на поле.
    public boolean setShip(Ship ship, int x, int y){

        Coordinate[] coordinates = new Coordinate[ship.getSize()];  // Массив координат корабля

        for (int i = 0; i<ship.getSize(); i++) {        // Расчёт координат корабля в зависимости от его ориентации
            if (ship.getOrientation().equals("h")) {
                Coordinate coordinate = getCoordinateObject(x, y+i);
                if(shipsCoordinates.containsKey(coordinate) || shipsEdgesCoordinates.containsKey(coordinate)){
                    return false;
                }
                coordinates[i] = coordinate;
            } else {
                Coordinate coordinate = getCoordinateObject(x+i, y);
                if(shipsCoordinates.containsKey(coordinate) || shipsEdgesCoordinates.containsKey(coordinate)){
                    return false;
                }
                coordinates[i] = coordinate;
            }
        }

        ship.setCell(coordinates);  // Передаём кораблю его координаты
        ships.add(ship);    // Добавляем корабль в коллекцию

        for (Coordinate coordinate : coordinates) {
            shipsCoordinates.put(coordinate, ship);     // Записываем координаты корабля в коллекцию
        }

        calculateShipEdgesCoordinates(ship, x, y);
        return true;
    }

    private void calculateShipEdgesCoordinates(Ship ship, int headX, int headY){

        for (int x = (headX-1); x <= (headX+ship.getXSize()); x++){                 // Перебираем прилегающие к кораблю координаты
            for (int y = (headY-1); y <= (headY+ship.getYSize()); y++){             //
                if(!ship.getCoordinates().containsKey(getCoordinateObject(x, y))){  // Исключаем из набора координаты самого корабля
                    shipsEdgesCoordinates.put(getCoordinateObject(x, y), ship);     // Добавляем прилегающую координату в набор
                    ship.addEdgesCoordinate(getCoordinateObject(x, y));             // Передаём прилегающую координату кораблю
                }
            }
        }

    }

    public boolean makeShot(Coordinate coordinate) {

        if(shipsCoordinates.containsKey(coordinate)){       // Если набор координат кораблей содержит текущую координату
            Ship ship = shipsCoordinates.get(coordinate);
            ship.gotShot(coordinate);                       // делаем выстрел
            if(ship.isDead()){                              // Если корабль убит
                System.out.println("Kill!");
                for(Coordinate edgeCoordinate : ship.getEdgesCoordinates()){    // Помечаем прилегающие координаты точками
                    dotsCoordinates.add(edgeCoordinate);                        //
                }
            }else {
                System.out.println("Hit!");
            }
            drawField();
            return true;
        }else{
            dotsCoordinates.add(coordinate);  // Если в текущей координате нет корабля - добавляем её в набор с координатами "точек".
            System.out.println("Miss!");
            drawField();
            return false;
        }

    }

    public void drawField() {

//        System.out.println();

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
                    System.out.print(" "+ shipsCoordinates.get(getCoordinateObject(x, y)).drawShip(getCoordinateObject(x, y))+" ");  // Если в текущей ячейке поля корабль - рисуем его.
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

    // Возвращает объект, инкапсулирующий координаты ячейки.
    public Coordinate getCoordinateObject(int x, int y){
        return allCoordinates.get(x+":"+y);
    }

    public boolean isShipDead(Coordinate coordinate){
        if (shipsCoordinates.containsKey(coordinate)) {
            return shipsCoordinates.get(coordinate).isDead();
        }else {
            return false;
        }
    }

    public boolean isAllShipsDead(){
        int deadships = 0;
        for (Ship ship : ships) {
            if(ship.isDead()){
                deadships++;
            }
        }
        return deadships == ships.size();
    }

    // Возврат набора координат, прилегающих к кораблю по заданной координате.
    public HashSet<Coordinate> getShipEdgesCoordinates(Coordinate coordinate){
        Ship ship = shipsCoordinates.get(coordinate);
        if (ship.isDead()) {
            return ship.getEdgesCoordinates();
        }else{
            return null;
        }
    }

    public String getShipOrientation(Coordinate coordinate){
        Ship ship = shipsCoordinates.get(coordinate);
        if(ship.injuredCells > 1){
            return ship.getOrientation();
        }
        return null;
    }

}