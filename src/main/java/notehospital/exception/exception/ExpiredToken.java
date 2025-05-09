package notehospital.exception.exception;

public class ExpiredToken extends RuntimeException{

    public ExpiredToken(String message){
        super(message);
    }

}
