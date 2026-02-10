package chad;

/**
 * Represents a note consisting of a single text snippet.
 * 
 * @author Yi Qian
 * @version 1.0
 * @since 2025-02-11
 * 
 */
public class Note {
    private final String text;

    /**
     * Creates a note with the given text.
     *
     * @param text Note content.
     */
    public Note(String text) {
        this.text = text;
    }

    /**
     * Returns the text of the note.
     *
     * @return Note text.
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Converts this note to a format suitable for saving to disk.
     *
     * @return Save-formatted line.
     */
    public String toSaveFormat() {
        return "N | " + text;
    }
}
