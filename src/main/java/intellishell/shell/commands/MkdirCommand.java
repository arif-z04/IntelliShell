package intellishell.shell.commands;

import intellishell.shell.ShellState;
import java.nio.file.Files;
import java.nio.file.Path;

public class MkdirCommand extends ShellCommand {

    @Override
    public String getName() {
        return "mkdir";
    }

    @Override
    public String getDescription() {
        return "Create a new directory";
    }

    @Override
    public void execute(String[] args, ShellState state) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Usage: mkdir <path>");
        }

        Path target = state.getCurrentDir().resolve(args[0]);
        // create directory 
        Files.createDirectories(target);
        System.out.println("Directory created: " + target);
    }
}
