package intellishell.shell;

import intellishell.ml.MarkovModel;

import java.util.Collection;

public class SuggestionEngine {

    private final MarkovModel model;

    public SuggestionEngine(MarkovModel model) {
        this.model = model;
    }

    /**
     * Suggest either the most probable next command from the Markov model
     * (based on last command in history), or a fuzzy match from known commands.
     */
    public String suggest(String unknown, Collection<String> knownCommands, ShellState state) {
        // if prev command exist, use markov model
        String last = null;
        var hist = state.getHistory();
        if (!hist.isEmpty()) {
            String lastLine = hist.get(hist.size() - 1);
            // trim only the command name
            last = lastLine.trim().split("\\s+")[0];
        }
        if (last != null) {
            String pred = model.predictNext(last);
            if (pred != null && knownCommands.contains(pred)) {
                return pred;
            }
        }

        // fuzzy match on known commands using Levenshtein distance
        String best = null;
        int bestDist = Integer.MAX_VALUE;
        for (String kc : knownCommands) {
            int d = levenshtein(unknown, kc);
            if (d < bestDist) {
                bestDist = d;
                best = kc;
            }
        }
        // threshold for suggestion
        if (bestDist <= 2) {
            return best;
        }
        return null;
    }

    private int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[a.length()][b.length()];
    }
}
