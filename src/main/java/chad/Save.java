package chad;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves the current task list to disk.
 * 
 * @author Yi Qian
 * @version 1.0
 * @throws ChadException If saving fails.
 * @since 2025-01-30
 */
public class Save {
    private final Path filePath;

    public Save(Path filePath) {
        assert filePath != null : "File path should not be null";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file.
     *
     * @return The list of tasks loaded from disk. Returns an empty list if the data
     *         file does not exist.
     * @throws ChadException If an I/O error occurs or the data file contains a
     *                       corrupted line.
     */
    public ArrayList<Task> load() throws ChadException {
        try {
            if (!Files.exists(filePath)) {
                Path parent = filePath.getParent();
                if (parent != null) {
                    Files.createDirectories(parent);
                }
                return new ArrayList<>();
            }
            List<String> lines = Files.readAllLines(filePath);
            ArrayList<Task> tasks = new ArrayList<>();

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                tasks.add(parseTask(line));
            }
            return tasks;
        } catch (IOException e) {
            throw new ChadException("Failed to load data file: " + filePath);
        }
    }

    /**
     * Saves the given list of tasks to the data file.
     *
     * @param tasks The list of tasks to save.
     * @throws ChadException If an I/O error occurs while saving.
     */
    public void save(List<Task> tasks) throws ChadException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            ArrayList<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(t.toSaveString());
            }
            Files.write(filePath, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new ChadException("Failed to save data file: " + filePath);
        }
    }

    private Task parseTask(String line) throws ChadException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new ChadException("Corrupted data line: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task task;

        if (type.equals("T")) {
            task = new Todo(desc);

        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new ChadException("Corrupted deadline line: " + line);
            }
            task = new Deadline(desc, Date.inputDate(parts[3]));

        } else if (type.equals("E")) {
            if (parts.length < 5) {
                throw new ChadException("Corrupted event line: " + line);
            }
            task = new Event(desc, Date.inputDate(parts[3]), Date.inputDate(parts[4]));

        } else {
            throw new ChadException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
