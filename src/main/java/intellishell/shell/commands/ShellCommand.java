package intellishell.shell.commands;

import intellishell.shell.ShellState;

public abstract class ShellCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute(String[] args, ShellState state) throws Exception;
}
