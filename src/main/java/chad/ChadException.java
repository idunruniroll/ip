package chad;

/**
 * Represents an application-specific exception for Chad.
 * Used to signal invalid user commands, invalid indexes, and storage errors.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-01-30
 */
public class ChadException extends Exception {

    /**
     * Creates a ChadException with the specified message.
     *
     * @param message Error message to show to the user.
     */
    public ChadException(String message) {
        super(message);
    }
}
