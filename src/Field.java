public class Field {


    Ship[][] field = new Ship[10][10];

    public void setShip(Ship ship, int x, int y){

        for (int i = 0; i<ship.getSize(); i++) {

            if (ship.getOrientation().equals("horizontal")) {
                field[x][y+i] = ship;
            }
            else {
                field[x+i][y] = ship;
            }
        }
    }

    public void drawField() {
        for (Ship[] ints : field) {
            for (Ship anInt : ints) {
                if (anInt == null){
                    System.out.print(" - ");
                }
                else {
                    System.out.print(" "+anInt+" ");
                }
            }
            System.out.println();
        }
    }

}
