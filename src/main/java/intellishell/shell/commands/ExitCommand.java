package intellishell.shell.commands;

import intellishell.shell.ShellState;

public class ExitCommand extends ShellCommand {
    @Override
    public String getName() { return "exit"; }

    @Override
    public String getDescription() { return "Exit the shell"; }

    @Override
    public void execute(String[] args, ShellState state) {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}