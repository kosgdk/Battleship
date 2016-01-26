
public class ShipCell {

    final String symbolIdle = "O";
    final String symbolInjured = "x";
    final String symbolDead = "X";
//    final String symbolShot = ".";

    private String state = "idle";

    public void setState(String state) {
        this.state = state;
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
