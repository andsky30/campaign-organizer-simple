package service.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException() {
        super("Invalid status campaign!");
    }
}
