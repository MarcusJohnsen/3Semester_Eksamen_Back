package errorhandling;

public class InvalidInputException extends Exception{

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException() {
        super("Yea, wrong input man.. Try again");
    }  
}