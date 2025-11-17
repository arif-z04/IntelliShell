package intellishell.ml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MarkovModelTest {
    private MarkovModel model;

    @BeforeEach
    public void setUp() {
        model = new MarkovModel();
    }

    @Test
    public void testTrain() {
        List<String> history = List.of("ls", "cd", "mkdir", "ls");
        model.train(history);

        var trans = model.getTransitions();
        assertTrue(trans.containsKey("ls"));
        assertTrue(trans.containsKey("cd"));
        assertTrue(trans.containsKey("mkdir"));
    }

    @Test
    public void testPredictNext() {
        List<String> history = List.of("ls", "cd", "cd", "mkdir", "ls", "cd");
        model.train(history);

        // After "cd" we have: mkdir (1), cd (1) => could be either
        // After "ls" we have: cd (2) => should predict cd
        String next = model.predictNext("ls");
        assertEquals("cd", next);
    }

    @Test
    public void testPredictNextUnknown() {
        List<String> history = List.of("ls", "cd");
        model.train(history);

        String next = model.predictNext("mkdir"); // never seen
        assertNull(next);
    }

    @Test
    public void testEmptyHistory() {
        model.train(List.of());
        var trans = model.getTransitions();
        assertTrue(trans.isEmpty());
    }
}
