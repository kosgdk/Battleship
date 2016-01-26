public class Field {


    Ship[][] field = new Ship[10][10];

    public void setShip(Ship ship, int x, int y){

        for (int i = 0; i<ship.getSize(); i++) {

            if (ship.getOrientation().equals("h")) {
                field[x][y+i] = ship;
                ship.setCoordinates(x, y+i);
            }
            else {
                field[x+i][y] = ship;
                ship.setCoordinates(x+i, y);
            }
        }
    }

    public void drawField() {

        for (int i = 0; i < field.length; i++) {

            for (int j = 0; j < field[0].length; j++) {

                    if (field[i][j] == null){
                    System.out.print(" - ");
                }

                else {

                    System.out.print(" "+ field[i][j].drawShip(i, j)+" ");

                }

            }
            System.out.println();
        }
    }

    public void shot(int x, int y) {
        if (field[x][y] != null) {
            field[x][y].gotShot(x,y);
        }
    }

}
