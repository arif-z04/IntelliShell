package intellishell.shell;

import java.util.ArrayList;
import java.util.List;
/*
    Shell implementation:

 * Take line of a text from a shell breaks it into:
 * 1. command name
 * 2. arguments
 */

public class CommandParser {
    public static class Parsed {
        public final String name;
        public final String[] args;

        public Parsed(String name, String[] args) {
            this.name = name;
            this.args = args;
        }
    }

    // Very simple parser: splits by whitespace; future: handle quotes
    public static Parsed parse(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length == 0) return new Parsed("", new String[0]);
        String name = parts[0];
        List<String> args = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) args.add(parts[i]);
        return new Parsed(name, args.toArray(String[]::new));
    }
}
