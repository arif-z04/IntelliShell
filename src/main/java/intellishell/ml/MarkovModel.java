package intellishell.ml;

import java.util.*;

/**
 * Simple first-order Markov model for command transitions. Tracks which
 * commands typically follow other commands.
 */
public class MarkovModel {

    // previous command -> map(next command -> count)
    private final Map<String, Map<String, Integer>> transitions = new HashMap<>();

    /**
     * Train model on command history. Extracts first token (command) from each
     * history line.
     */
    public synchronized void train(List<String> history) {
        transitions.clear();
        String prev = null;
        for (String line : history) {
            if (line == null || line.isBlank()) {
                continue;
            }
            String cmd = line.trim().split("\\s+")[0];
            if (prev != null) {
                transitions.computeIfAbsent(prev, k -> new HashMap<>())
                        .merge(cmd, 1, Integer::sum);
            }
            prev = cmd;
        }
    }

    /**
     * Predict most likely next command given previous. Returns null if no
     * history available.
     */
    public synchronized String predictNext(String previous) {
        Map<String, Integer> m = transitions.get(previous);
        if (m == null || m.isEmpty()) {
            return null;
        }
        return m.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public synchronized Map<String, Map<String, Integer>> getTransitions() {
        Map<String, Map<String, Integer>> copy = new HashMap<>();
        for (var e : transitions.entrySet()) {
            copy.put(e.getKey(), Map.copyOf(e.getValue()));
        }
        return Map.copyOf(copy);
    }
}
