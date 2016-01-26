public class Battleship {


    public static void main(String[] args) {
        Ship ship1 = new Ship(4, "vertical");
        Field field = new Field();
        field.setShip(ship1, 0, 6);
        field.drawField();

    }


}
