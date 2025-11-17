package intellishell.shell.commands;

import intellishell.shell.ShellState;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.stream.Stream;

public class ListCommand extends ShellCommand {
    @Override
    public String getName() { return "ls"; }

    @Override
    public String getDescription() { return "List files in current directory"; }

    @Override
    public void execute(String[] args, ShellState state) throws Exception {
        Path dir = state.getCurrentDir();
        try (Stream<Path> stream = Files.list(dir)) {
            stream.sorted()
                  .forEach(p -> System.out.println(
                      Files.isDirectory(p) ? p.getFileName() + "/" : p.getFileName().toString()
                  ));
        }
    }
}