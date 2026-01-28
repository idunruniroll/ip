import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Save {
    private final Path filePath;

    public Save(Path filePath) {
        this.filePath = filePath;
    }

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
            Files.write(filePath, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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
            task = new Deadline(desc, parts[3]);

        } else if (type.equals("E")) {
            if (parts.length < 5) {
                throw new ChadException("Corrupted event line: " + line);
            }
            task = new Event(desc, parts[3], parts[4]);

        } else {
            throw new ChadException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
