package chad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving notes to disk.
 */
public class NoteStorage {
    private final Path filePath;

    /** Creates a note storage using the default notes file path. */
    public NoteStorage() {
        this(Path.of("data", "notes.txt"));
    }

    /**
     * Creates a note storage using the given file path.
     *
     * @param filePath Path to notes file.
     */
    public NoteStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads notes from disk. If the file does not exist, returns an empty list.
     *
     * @return Loaded note list.
     * @throws ChadException If file reading fails.
     */
    public NoteList load() throws ChadException {
        try {
            if (!Files.exists(filePath)) {
                return new NoteList();
            }

            List<String> lines = Files.readAllLines(filePath);
            ArrayList<Note> notes = new ArrayList<>();

            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                // Expected: N | <text>
                String[] parts = trimmed.split("\\|", 2);
                if (parts.length < 2 || !parts[0].trim().equals("N")) {
                    throw new ChadException("OOPS!!! Corrupted notes file.");
                }

                String text = parts[1].trim();
                notes.add(new Note(text));
            }

            return new NoteList(notes);
        } catch (IOException e) {
            throw new ChadException("OOPS!!! Failed to load notes.");
        }
    }

    /**
     * Saves notes to disk.
     *
     * @param notes Notes to save.
     * @throws ChadException If saving fails.
     */
    public void save(ArrayList<Note> notes) throws ChadException {
        try {
            Files.createDirectories(filePath.getParent());
            List<String> lines = notes.stream()
                    .map(Note::toSaveFormat)
                    .toList();
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new ChadException("OOPS!!! Failed to save notes.");
        }
    }
}
