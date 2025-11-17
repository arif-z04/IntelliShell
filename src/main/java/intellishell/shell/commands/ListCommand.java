package intellishell.shell.commands;

import intellishell.shell.ShellState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class ListCommand extends ShellCommand {
    @Override
    public String getName() {
        return "ls";
    }

    @Override
    public String getDescription() {
        return "List directory contents";
    }

    @Override
    public void execute(String[] args, ShellState state) throws IOException {
        Path dir = state.getCurrentDir();
        Files.list(dir).sorted(Comparator.naturalOrder()).forEach(p -> {
            try {
                String name = p.getFileName().toString();
                if (Files.isDirectory(p)) name += "/";
                System.out.println(name);
            } catch (Exception e) {
                System.out.println(p.getFileName());
            }
        });
    }
}
