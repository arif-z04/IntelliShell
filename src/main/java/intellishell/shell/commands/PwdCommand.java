package intellishell.shell.commands;

import intellishell.shell.ShellState;

public class PwdCommand extends ShellCommand{
    @Override
    public String getName(){
        return "pwd";
    }    
    @Override
    public String getDescription(){
        return "Show current working directory";
    }

    @Override
    public void execute(String[] args, ShellState state) throws Exception {
        System.out.println(state.getCurrentDir().toAbsolutePath());
    }

    
}
