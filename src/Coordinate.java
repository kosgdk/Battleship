public class Coordinate implements Comparable<Coordinate>{

    private final int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString(){
        return x + ":" + y;
    }

    @Override
    public int compareTo(Coordinate coordinate){
        if(x > coordinate.getX()){
            return 1;
        }else if(x < coordinate.getX()){
            return -1;
        }else {
            if(y > coordinate.getY()){
                return 1;
            }else if(y < coordinate.getY()){
                return -1;
            }else {
                return 0;
            }
        }
    }

}
