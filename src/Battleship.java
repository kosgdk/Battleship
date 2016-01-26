public class Battleship {
    public static void main(String[] args) {
        Ship ship1 = new Ship();
        Ship[] field = new Ship[10];
        field[2] = ship1;
        field[4] = ship1;
        for (Ship ship : field) {
            if (ship == null){
                System.out.print('-');
            }
            else {
                System.out.print(ship);
            }
        }
    }
}
