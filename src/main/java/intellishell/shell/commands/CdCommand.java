package intellishell.shell.commands;

import intellishell.shell.ShellState;
import java.nio.file.Files;
import java.nio.file.Path;

public class CdCommand extends ShellCommand {
    @Override
    public String getName() { return "cd"; }

    @Override
    public String getDescription() { return "Change directory"; }

    @Override
    public void execute(String[] args, ShellState state) throws Exception {
        if (args.length == 0) throw new IllegalArgumentException("Usage: cd <path>");
        
        Path target = state.getCurrentDir().resolve(args[0]).normalize();
        if (!Files.exists(target)) {
            throw new IllegalArgumentException("Path does not exist: " + target);
        }
        if (!Files.isDirectory(target)) {
            throw new IllegalArgumentException("Not a directory: " + target);
        }
        state.setCurrentDir(target);
    }
}