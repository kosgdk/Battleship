
public class ShipCell {

    final int x;
    final int y;

    final String symbolIdle = "O";
    final String symbolInjured = "x";
    final String symbolDead = "X";
//    final String symbolShot = ".";

    private String state = "idle";

    public ShipCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isInjured () {
        return state.equals("injured");
    }

    public String toString(){
        switch (state){
            case "injured":
                return symbolInjured;
            case "dead":
                return symbolDead;
            default:
                return symbolIdle;
        }
    }


}
