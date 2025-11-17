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
        return "Create directory";
    }

    @Override
    public void execute(String[] args, ShellState state) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: mkdir <dir>");
            return;
        }
        Path p = state.getCurrentDir().resolve(args[0]).normalize();
        if (Files.exists(p)) {
            System.out.println("Already exists: " + p.getFileName());
            return;
        }
        Files.createDirectories(p);
    }
}
