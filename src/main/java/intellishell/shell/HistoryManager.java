package intellishell.shell;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages persistence of command history to ~/.intellishell_history.
 */
public class HistoryManager {
    private static final String HISTORY_FILE = ".intellishell_history";
    private final Path historyPath;

    public HistoryManager() {
        this.historyPath = Path.of(System.getProperty("user.home")).resolve(HISTORY_FILE);
    }

    /**
     * Load history from disk. If file does not exist, returns empty list.
     */
    public List<String> load() {
        if (Files.exists(historyPath)) {
            try {
                return new ArrayList<>(Files.readAllLines(historyPath, StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.err.println("Warning: could not load history: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    /**
     * Save history to disk, overwriting existing file.
     */
    public void save(List<String> history) {
        try {
            Files.write(historyPath, history, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Warning: could not save history: " + e.getMessage());
        }
    }
}
