/**
 * Обёрточный класс, хранящий в себе корабль и координаты
 */

public class ShipAndCoordinate {
    Ship ship;
    Coordinate coordinate;

    public ShipAndCoordinate(Ship ship, Coordinate coordinate) {
        this.ship = ship;
        this.coordinate = coordinate;
    }

    public Ship getShip() {
        return ship;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getX(){
        return coordinate.getX();
    }

    public int getY(){
        return coordinate.getY();
    }
}
