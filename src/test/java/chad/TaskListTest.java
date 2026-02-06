package chad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void getValidated_invalidIndex_throwsChadException() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertThrows(ChadException.class, () -> tasks.get(-1));
        assertThrows(ChadException.class, () -> tasks.get(1));
    }

    @Test
    public void removeValidated_validIndex_removesCorrectTask() throws ChadException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("a"));
        tasks.add(new Todo("b"));

        Task removed = tasks.remove(0);

        assertEquals("[T][ ] a", removed.toString());
        assertEquals(1, tasks.size());
        assertEquals("[T][ ] b", tasks.get(0).toString());
    }
}
