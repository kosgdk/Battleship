import java.util.LinkedHashSet;
import java.util.TreeMap;


public class Ship {

    TreeMap<String, ShipCell> cellsMap = new TreeMap<>();                   // Набор с ячейками корабля
    LinkedHashSet<Coordinate> surroundCoordinates = new LinkedHashSet<>();  // Набор с координатами прилегающих ячеек поля

    boolean dead;

    int size;
    int xSize;
    int ySize;
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

    public String getOrientation() {
        return orientation;
    }

    public int getSize() {
        return size;
    }

    public void setCell(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) {
            int x = coordinate.getX();
            int y = coordinate.getY();
            cellsMap.put(x+":"+y, new ShipCell(x, y));
        }
    }

    // Возвращаем визуализацию ячейки корабля
    public String drawShip (int x, int y){
        return cellsMap.get(x+":"+y).toString();
    }

    // Присваиваем ячейке корабля статус "ранен"
    public void gotShot(int x, int y){
        cellsMap.get(x+":"+y).setState("injured");
        injuredCells++;
        checkForDeath();
    }

    // Проверяем, не "убит" ли корабль
    private void checkForDeath () {
       if (injuredCells == size) {
           dead = true;
           for (ShipCell shipCell : cellsMap.values()) {
               shipCell.setState("dead");                                   // Присваиваем ячейкам корабля статус "убит"
           }
       }
    }

    // Возвращаем набор координат прилегающих к кораблю ячеек поля
    public LinkedHashSet<Coordinate> getSurroundCoordinates() {
        int headX = cellsMap.get(cellsMap.firstKey()).getX();       // Получаем координаты "головы" корабля
        int headY = cellsMap.get(cellsMap.firstKey()).getY();       //

        for (int x = (headX-1); x <= (headX+xSize); x++){           // Перебираем прилегающие к кораблю координаты
           for (int y = (headY-1); y <= (headY+ySize); y++){
               if(!cellsMap.containsKey(x+":"+y)){                  // Исключаем из набора координаты самого корабля
                   surroundCoordinates.add(new Coordinate(x,y));
               }
           }
        }

        return surroundCoordinates;
    }

    public void printCoordinates() {
        for (ShipCell shipCell : cellsMap.values()) {
            System.out.println(shipCell.getX()+":"+shipCell.getY());
        }
    }

}
