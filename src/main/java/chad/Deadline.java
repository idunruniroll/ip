package chad;

import java.time.LocalDate;

public class Deadline extends Task {
    private final LocalDate doneBy;

    public Deadline(String description, LocalDate doneBy) {
        super(description);
        this.doneBy = doneBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + Date.outputDate(doneBy) + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + (getIsDone() ? "1" : "0") + " | " + getDescription() + " | " + doneBy;
    }
}