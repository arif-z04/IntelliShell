package intellishell.shell;

import java.nio.file.Path;
import java.util.*;

/*
 * keep track of:
 * 1. Current working directory
 * 2. Command History
 */
public class ShellState {
    private Path currentDir;

    @SuppressWarnings({ "rawtypes", "unchecked" }) // to surpress rawtypes and unchecked casting warningsJaa
    private final List<String> history = new ArrayList();

    
    public ShellState(Path initialDir){
        this.currentDir = initialDir;
    }
    public Path getCurrentDir(){
        return currentDir;
    }
    public void setCurrentDir(Path currentDir){
        this.currentDir = currentDir;
    }
    public void addHistory(String commandLine){
        history.add(commandLine);
    }
    public List<String> getHistory(){
        return Collections.unmodifiableList(history);
    }
}
