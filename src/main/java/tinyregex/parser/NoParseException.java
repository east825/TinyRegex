package tinyregex.parser;

public class NoParseException extends Exception {
    public NoParseException() {
    }

    public NoParseException(String message) {
        super(message);
    }

    public NoParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoParseException(Throwable cause) {
        super(cause);
    }
}
