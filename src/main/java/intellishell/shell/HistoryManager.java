package intellishell.shell;

import java.nio.file.*;
import java.util.*;

public class HistoryManager {
    private final Path historyFile = Paths.get(
        System.getProperty("user.home"), 
        ".intellishell_history");


    @SuppressWarnings("UseSpecificCatch") // supresses warning for specific error catch
    public List<String> load(){
        try {
            if(!Files.exists(historyFile)) return new ArrayList<>();
            return Files.readAllLines(historyFile);
        } catch (Exception e) {
            System.err.println("Warning: Coult not load history: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
