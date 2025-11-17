package intellishell.shell;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    @Test
    public void testParseSimpleCommand() {
        CommandParser.Parsed p = CommandParser.parse("ls");
        assertEquals("ls", p.name);
        assertEquals(0, p.args.length);
    }

    @Test
    public void testParseCommandWithArgs() {
        CommandParser.Parsed p = CommandParser.parse("cd /home/user");
        assertEquals("cd", p.name);
        assertEquals(1, p.args.length);
        assertEquals("/home/user", p.args[0]);
    }

    @Test
    public void testParseCommandWithMultipleArgs() {
        CommandParser.Parsed p = CommandParser.parse("mkdir dir1 dir2 dir3");
        assertEquals("mkdir", p.name);
        assertEquals(3, p.args.length);
        assertEquals("dir1", p.args[0]);
        assertEquals("dir2", p.args[1]);
        assertEquals("dir3", p.args[2]);
    }

    @Test
    public void testParseWithExtraWhitespace() {
        CommandParser.Parsed p = CommandParser.parse("  ls   arg1   arg2  ");
        assertEquals("ls", p.name);
        assertEquals(2, p.args.length);
        assertEquals("arg1", p.args[0]);
    }

    @Test
    public void testParseEmpty() {
        CommandParser.Parsed p = CommandParser.parse("");
        assertEquals("", p.name);
        assertEquals(0, p.args.length);
    }
}
