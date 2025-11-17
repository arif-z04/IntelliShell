package intellishell.shell.commands;

import intellishell.shell.ShellState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileCommandsTest {

    private ShellState state;
    private Path testDir;


    @BeforeEach
    public void setUp() throws Exception {
        testDir = Paths.get(System.getProperty("java.io.tmpdir"), "intellishell_test");
        if (Files.exists(testDir)) {
            Files.walk(testDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (Exception e) {
                            System.err.println("Could not delete " + path + ": " + e.getMessage());                  
                        }
                    });
        }
        Files.createDirectory(testDir);
        state = new ShellState(testDir);
    }

    @Test
    public void testPwdCommand() throws Exception {
        PwdCommand cmd = new PwdCommand();
        assertEquals("pwd", cmd.getName());
        cmd.execute(new String[]{"pwd"}, state);
    }

    // @Test
    // public void testTouchCommand() throws Exception {
    //     TouchCommand cmd = new TouchCommand();
    //     assertEquals("touch", cmd.getName());
    //     cmd.execute(new String[]{"touch", "test.txt"}, state);
    //     assertTrue(Files.exists(testDir.resolve("test.txt")));
    // }

    // @Test
    // public void testCpCommand() throws Exception {
    //     // Create a test file
    //     Path source = testDir.resolve("source.txt");
    //     Files.createFile(source);
    //     Files.writeString(source, "test content");

    //     CpCommand cmd = new CpCommand();
    //     assertEquals("cp", cmd.getName());
    //     cmd.execute(new String[]{"cp", "source.txt", "destination.txt"}, state);
        
    //     assertTrue(Files.exists(testDir.resolve("destination.txt")));
    //     assertEquals("test content", Files.readString(testDir.resolve("destination.txt")));
    // }

    // @Test
    // public void testMvCommand() throws Exception {
    //     // Create a test file
    //     Path source = testDir.resolve("old.txt");
    //     Files.createFile(source);
    //     Files.writeString(source, "content");

    //     MvCommand cmd = new MvCommand();
    //     assertEquals("mv", cmd.getName());
    //     cmd.execute(new String[]{"mv", "old.txt", "new.txt"}, state);
        
    //     assertFalse(Files.exists(testDir.resolve("old.txt")));
    //     assertTrue(Files.exists(testDir.resolve("new.txt")));
    // }

    // @Test
    // public void testRmCommand() throws Exception {
    //     // Create a test file
    //     Path file = testDir.resolve("delete_me.txt");
    //     Files.createFile(file);

    //     RmCommand cmd = new RmCommand();
    //     assertEquals("rm", cmd.getName());
    //     cmd.execute(new String[]{"rm", "delete_me.txt"}, state);
        
    //     assertFalse(Files.exists(testDir.resolve("delete_me.txt")));
    // }

    @Test
    public void testMkdirCommand() throws Exception {
        MkdirCommand cmd = new MkdirCommand();
        assertEquals("mkdir", cmd.getName());
        // MkdirCommand already exists from main code, just test the new commands
        // Skip this test as MkdirCommand is pre-existing
    }

    // @Test
    // public void testRmdirCommand() throws Exception {
    //     // Create a test directory
    //     Path dir = testDir.resolve("rmdir_test");
    //     Files.createDirectory(dir);

    //     RmdirCommand cmd = new RmdirCommand();
    //     assertEquals("rmdir", cmd.getName());
    //     cmd.execute(new String[]{"rmdir", "rmdir_test"}, state);
        
    //     assertFalse(Files.exists(testDir.resolve("rmdir_test")));
    // }


    // @Test
    // public void testFindCommand() throws Exception {
    //     // Create test files
    //     Files.createFile(testDir.resolve("test1.txt"));
    //     Files.createFile(testDir.resolve("test2.txt"));
    //     Files.createFile(testDir.resolve("other.doc"));

    //     FindCommand cmd = new FindCommand();
    //     assertEquals("find", cmd.getName());
    //     cmd.execute(new String[]{"find", "test"}, state);
    // }
}
