import java.util.HashMap;
import java.util.HashSet;

public class Ship {

    HashMap<Coordinate, ShipCell> cellsMap = new HashMap<>(); // Набор с ячейками корабля
    HashSet<Coordinate> edgesCoordinates = new HashSet<>();

    boolean dead;

    private int size;
    private int xSize;
    private int ySize;
    int injuredCells;

    private String orientation;

    //Конструктор с рандомной ориентацией
    public Ship(int size) {
        this.size = size;
        this.orientation = Math.random() > 0.5 ? "v" : "h";
        calculateSizes(size, orientation);
    }

    // Конструктор с задаваемой ориентацией
    public Ship(int size, String orientation) {
        this.size = size;
        this.orientation = orientation;
        calculateSizes(size, orientation);
    }

    // Определяем размеры корабля по Х и Y
    private void calculateSizes(int size, String orientation){
        if(orientation.equals("v")){
            this.ySize = 1;
            this.xSize = size;
        } else {
            this.ySize = size;
            this.xSize = 1;
        }
    }

    public void setCell(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) {
            cellsMap.put(coordinate, new ShipCell(coordinate.getX(), coordinate.getY()));
        }
    }

    public boolean isCellIdle(Coordinate coordinate){
        return cellsMap.get(coordinate).isIdle();
    }

    // Возвращаем визуализацию ячейки корабля
    public String drawShip (Coordinate coordinate){
        return cellsMap.get(coordinate).toString();
    }

    // Присваиваем ячейке корабля статус "ранен"
    public void gotShot(Coordinate coordinate){
        if(!isDead()){
            cellsMap.get(coordinate).setState("injured");
            injuredCells++;
            checkForDeath();
        }
    }

    // Проверяем, не "убит" ли корабль
    private void checkForDeath () {
       if (injuredCells == size) {                                          // Если все ячейки корабля подбиты
           dead = true;
           for (ShipCell shipCell : cellsMap.values()) {
               shipCell.setState("dead");                                   // Присваиваем ячейкам корабля статус "убит"
           }
       }
    }

    public HashMap<Coordinate, ShipCell> getCoordinates(){
        return cellsMap;
    }

    String getOrientation() {
        return orientation;
    }

    public int getSize() {
        return size;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public boolean isDead(){
        return dead;
    }

    public void addEdgesCoordinate (Coordinate coordinate){
        edgesCoordinates.add(coordinate);
    }

    public HashSet<Coordinate> getEdgesCoordinates(){
        return edgesCoordinates;
    }
}
