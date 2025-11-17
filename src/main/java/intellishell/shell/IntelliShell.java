package intellishell.shell;

import intellishell.ml.MarkovModel;
import intellishell.shell.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;


public class IntelliShell {
    public static void main(String[] args) throws IOException {
        ShellState state = new ShellState(Path.of(System.getProperty("user.dir")));

        // Commands registry
        Map<String, ShellCommand> commands = new LinkedHashMap<>();

        // ML components
        MarkovModel model = new MarkovModel();
        SuggestionEngine sugg = new SuggestionEngine(model);

        // History persistence
        HistoryManager histMgr = new HistoryManager();
        var loadedHist = histMgr.load();
        for (String h : loadedHist) state.addHistory(h);
        model.train(state.getHistory());

        // register commands
        commands.put("help", new HelpCommand(commands));
        commands.put("exit", new ExitCommand());
        commands.put("ls", new ListCommand());
        commands.put("cd", new CdCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("pwd", new PwdCommand());

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // show the welcome message from menu.txt file
        try {
            List<String> menuLines = Files.readAllLines(Path.of("menu.txt"));
            for (String menuLine : menuLines) {
                System.out.println(menuLine);
            }
        } catch (IOException e) {
            System.out.println("Welcome to IntelliShell! (menu.txt not found)");
        }
        
        while (true) {
            System.out.print(state.getCurrentDir().toString() + " $ ");
            String line = in.readLine();
            if (line == null) {
                System.out.println();
                break; // EOF
            }
            line = line.trim();
            if (line.isEmpty()) continue;

            // Save history and train model
            state.addHistory(line);
            model.train(state.getHistory());

            CommandParser.Parsed parsed = CommandParser.parse(line);
            ShellCommand cmd = commands.get(parsed.name);
            if (cmd != null) {
                try {
                    cmd.execute(parsed.args, state);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                // unknown command - ask ML for suggestion
                String suggestion = sugg.suggest(parsed.name, commands.keySet(), state);
                if (suggestion != null) {
                    System.out.println("Unknown command '" + parsed.name + "'. Did you mean: " + suggestion + " ?");
                } else {
                    System.out.println("Unknown command: " + parsed.name + " (type 'help' for a list)");
                }
            }
        }
        // Save history before exit
        histMgr.save(state.getHistory());
    }
}
