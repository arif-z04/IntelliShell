package intellishell.shell;

import intellishell.ml.MarkovModel;
import intellishell.shell.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;

public class IntelliShell {
    public static void main(String[] args) throws IOException {
        // ML components

        ShellState state = new ShellState(Path.of(System.getProperty("user.dir")));
    }
}
