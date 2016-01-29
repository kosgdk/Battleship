package Exceptions;

public class CoordinateIsNotEmptyException extends Throwable{
    public String toString(){
        return ("This field coordinates is not empty. Choose another coordinates");
    }
}
