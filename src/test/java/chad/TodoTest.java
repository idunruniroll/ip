package chad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void markAsDone_changesToString() {
        Todo t = new Todo("read book");
        assertEquals("[T][ ] read book", t.toString());

        t.markAsDone();
        assertEquals("[T][X] read book", t.toString());
    }
}
