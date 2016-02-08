
public class ShipCell {

    final int x;
    final int y;

    final String symbolIdle = "O";
    final String symbolInjured = "x";
    final String symbolDead = "X";

    private String state = "idle";

    public ShipCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isIdle(){
        return state.equals("idle");
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
