public class Deadline extends Task {
    private final String doneBy;

    public Deadline(String description, String doneBy) {
        super(description);
        this.doneBy = doneBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + doneBy + ")";
    }
}