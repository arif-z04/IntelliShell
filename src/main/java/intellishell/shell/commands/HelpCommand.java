package intellishell.shell.commands;

import intellishell.shell.ShellState;

import java.util.Map;

public class HelpCommand extends ShellCommand {
    private final Map<String, ShellCommand> registry;

    public HelpCommand(Map<String, ShellCommand> registry) {
        this.registry = registry;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show available commands";
    }

    @Override
    public void execute(String[] args, ShellState state) {
        System.out.println("Available commands:");
        for (ShellCommand c : registry.values()) {
            System.out.printf("  %-10s - %s%n", c.getName(), c.getDescription());
        }
    }
}
