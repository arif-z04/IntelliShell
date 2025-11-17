package intellishell.shell.commands;

import java.util.Map;
import intellishell.shell.ShellState;

public class HelpCommand extends ShellCommand {

    private final Map<String, ShellCommand> commands;

    public HelpCommand(Map<String, ShellCommand> commands) {
        this.commands = commands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display available commands";
    }

    @Override
    public void execute(String[] args, ShellState state) {
        System.out.println("\n=== Available Commands ===");
        for (var entry : commands.entrySet()) {
            ShellCommand cmd = entry.getValue();
            System.out.printf("  %-10s %s\n", entry.getKey(), cmd.getDescription());
        }
        System.out.println();
    }
}
