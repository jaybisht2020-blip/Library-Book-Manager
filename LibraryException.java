package library.util;

/**
 * Custom exception for library-related errors.
 */
public class LibraryException extends Exception {
    public LibraryException(String message) {
        super(message);
    }
}
