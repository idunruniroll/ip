package chad;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Stores and manages a list of notes.
 *
 * @author Yi Qian
 * @version 1.0
 * @since 2025-02-11
 */
public class NoteList {

    private final ArrayList<Note> notes;

    /**
     * Creates an empty note list.
     */
    public NoteList() {
        this.notes = new ArrayList<>();
    }

    /**
     * Creates a note list with initial notes.
     *
     * @param notes Initial notes.
     */
    public NoteList(ArrayList<Note> notes) {
        assert notes != null : "notes should not be null";
        this.notes = notes;
    }

    /**
     * Returns the number of notes stored.
     *
     * @return Number of notes.
     */
    public int size() {
        return notes.size();
    }

    /**
     * Returns the backing list of notes.
     *
     * @return Notes list.
     */
    public ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * Adds a note to the list.
     *
     * @param note Note to add.
     */
    public void add(Note note) {
        assert note != null : "note should not be null";
        notes.add(note);
        assert notes.contains(note) : "note should be in list after add";
    }

    /**
     * Returns the note at the given index.
     *
     * @param index Zero-based index.
     * @return Note at the index.
     * @throws ChadException If index is out of bounds.
     */
    public Note get(int index) throws ChadException {
        validateIndex(index);
        return notes.get(index);
    }

    /**
     * Removes the note at the given index.
     *
     * @param index Zero-based index.
     * @return Removed note.
     * @throws ChadException If index is out of bounds.
     */
    public Note remove(int index) throws ChadException {
        validateIndex(index);
        return notes.remove(index);
    }

    /**
     * Finds notes whose text contains the keyword (case-insensitive).
     *
     * @param keyword Keyword to search for.
     * @return Matching notes.
     */
    public ArrayList<Note> find(String keyword) {
        assert keyword != null : "keyword should not be null";
        String k = keyword.toLowerCase();
        return notes.stream()
                .filter(n -> n.getText().toLowerCase().contains(k))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Validates that the index is within bounds.
     *
     * @param index Zero-based index.
     * @throws ChadException If index is out of bounds.
     */
    private void validateIndex(int index) throws ChadException {
        if (index < 0 || index >= notes.size()) {
            throw new ChadException("OOPS!!! Invalid note number.");
        }
        assert index >= 0 : "index should be non-negative";
        assert index < notes.size() : "index should be within bounds";
    }
}
