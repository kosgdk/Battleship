import java.util.Random;

public class Field {

    Random random = new Random();

    Ship[][] field = new Ship[10][10];

    public void setShip(Ship ship, int x, int y){

        for (int i = 0; i<ship.getSize(); i++) {

            if (ship.getOrientation().equals("h")) {
                field[x][y+i] = ship;
                ship.setCell(x, y+i);
            }
            else {
                field[x+i][y] = ship;
                ship.setCell(x+i, y);
            }
        }
    }

    public void setShipRandom(Ship ship) {
        int maxXCoordinate = field.length;
        int maxYCoordinate = field[0].length;
        if (ship.getOrientation().equals("h")) {
            maxYCoordinate = field[0].length - ship.getSize() + 1;
        } else {
            maxXCoordinate = field.length - ship.getSize() + 1;
        }
        setShip(ship, random.nextInt(maxXCoordinate), random.nextInt(maxYCoordinate));
    }

    public void drawField() {

        System.out.print("Y ");

        for (int i = 0; i < field.length; i++) {
            System.out.print(" "+i+" ");
        }

        System.out.println("");
        System.out.print("X ");

        for (int i = 0; i < field.length; i++) {
            System.out.print("---");
        }

        System.out.println("");

        for (int i = 0; i < field.length; i++) {
            System.out.print(i+"|");

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
