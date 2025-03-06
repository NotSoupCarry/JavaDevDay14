public class ProvaCustomException {
    
}

class MyCustomException extends Exception {
    public MyCustomException(String message) {
        super(message);
    }
}