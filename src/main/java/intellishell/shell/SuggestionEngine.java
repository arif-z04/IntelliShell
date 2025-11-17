package intellishell.shell;

import intellishell.ml.MarkovModel;

import java.util.*;

public class SuggestionEngine {

    private final MarkovModel model;
    private static final int MAX_DISTANCE = 2;

    public SuggestionEngine(MarkovModel model) {
        this.model = model;
    }

    /*
     * Suggest command; first try Markov, then fuzzy match
     */
    public String suggest(String typo, Set<String> validCommands, ShellState state) {
        String[] history = state.getHistory().toArray(new String[0]);
        if (history.length > 0) {
            String lastCmd = history[history.length - 1].trim().split("\\s+")[0];
            String next = model.predictNext(lastCmd);
            if (next != null && validCommands.contains(next)) {
                return next;
            }
        }

        for (String cmd : validCommands) {
            if (levenshtein(typo, cmd) <= MAX_DISTANCE) {
                return cmd;
            }
        }
        return null;
    }
    /**
     * Levenshtein distance: minimum edits to transform s1 → s2.
     * Handles typos (character substitutions, insertions, deletions).
     */
    private int levenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, // deletion
                        dp[i][j - 1] + 1),                     // insertion
                        dp[i - 1][j - 1] + cost);              // substitution
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
