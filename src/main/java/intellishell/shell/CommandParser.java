package intellishell.shell;

/*
    Shell implementation:

 * Take line of a text from a shell breaks it into:
 * 1. command name
 * 2. arguments
 */

 public class CommandParser {
    public record Parsed(String name, String[] args){}

    public static Parsed parse(String line){
        String[] tokens = line.trim().split("\\s+");
        String name = tokens.length > 0 ? tokens[0]: "";
        String[] args = tokens.length > 1 ? java.util.Arrays.copyOfRange(tokens, 1, tokens.length) : new String[0];
        return new Parsed(name, args);
    }
}
