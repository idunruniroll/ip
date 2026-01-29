package chad;

public abstract class Task {
        private final String description;
        private boolean isDone;

        public Task (String description) {
            this.description = description;
            this.isDone = false;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public void markAsNotDone() {
            this.isDone = false;
        }

        public String getDescription() {
            return description;
        }

        public boolean getIsDone() {
            return isDone;
        }

        @Override
        public String toString() {
            return (isDone ? "[X] " : "[ ] ") + description;
        }

        public abstract String toSaveString();
    }