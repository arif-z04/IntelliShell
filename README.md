# IntelliShell

IntelliShell is a small, object-oriented Java console shell with a simple machine-learning suggestion engine using a first-order Markov model.

## Features
- Built-in commands: help, ls, cd, mkdir, exit
- Pluggable command architecture via an abstract `ShellCommand`
- Markov model trained on the session command history to predict next commands and suggest alternatives for unknown commands
- Persistent history saved to `~/.intellishell_history` across sessions
- Full JUnit 5 test suite for core components

## Build & Run

**Requirements:** Java 21+ and Maven 3.6+

### Option 1: One-Click Run (Recommended) ⭐

#### **Linux / Mac:**
```bash
./run.sh
```

Or double-click `run.sh` in your file manager.

#### **Windows:**
```cmd
run.bat
```

Or double-click `run.bat` in File Explorer.

### Option 2: Build & Run Manually:

Build:
```bash
mvn clean package
```

Run:
```bash
java -jar target/intellishell-0.1.0-jar-with-dependencies.jar
```

### Run tests:

```bash
mvn test
```

All 12 tests pass (MarkovModel training/prediction, CommandParser, SuggestionEngine).

## Usage Example

```bash
$ java -jar target/intellishell-0.1.0-jar-with-dependencies.jar
Welcome to IntelliShell (type 'help' for commands)
/tmp $ help
Available commands:
  help     - Show available commands
  exit     - Exit the shell
  ls       - List directory contents
  cd       - Change directory
  mkdir    - Create directory
/tmp $ ls
...
/tmp $ mkdir testdir
/tmp $ cd testdir
/testdir $ lss
Unknown command 'lss'. Did you mean: ls ?
/testdir $ exit
Goodbye.
```

When you enter an unknown command, IntelliShell tries to:
1. Suggest the most likely next command (based on Markov transitions from history)
2. Offer a fuzzy-matched command from known commands (Levenshtein distance ≤ 2)

History is automatically saved to `~/.intellishell_history` and reloaded on startup, improving suggestions over time.

## Project Structure

```
src/main/java/intellishell/
├─ shell/
│  ├─ IntelliShell.java        (main loop, command registry)
│  ├─ ShellState.java          (current dir, history)
│  ├─ CommandParser.java       (parse input)
│  ├─ SuggestionEngine.java    (ML + fuzzy matching)
│  ├─ HistoryManager.java      (persist to ~/.intellishell_history)
│  └─ commands/
│     ├─ ShellCommand.java     (abstract base)
│     ├─ HelpCommand.java
│     ├─ ExitCommand.java
│     ├─ ListCommand.java      (ls)
│     ├─ CdCommand.java        (cd)
│     └─ MkdirCommand.java     (mkdir)
└─ ml/
   └─ MarkovModel.java         (first-order transitions)

src/test/java/intellishell/
├─ shell/
│  ├─ CommandParserTest.java    (5 tests)
│  └─ SuggestionEngineTest.java (3 tests)
└─ ml/
   └─ MarkovModelTest.java      (4 tests)
```

## OOP Concepts Demonstrated

| Concept       | Where Used                                                 |
|---------------|-------------------------------------------------------------|
| Abstraction   | `ShellCommand` abstract class                              |
| Inheritance   | Specific commands extend `ShellCommand`                     |
| Polymorphism  | Each command overrides `execute()`                          |
| Encapsulation | `ShellState` hides internal state, accessed via getters     |
| Composition   | `IntelliShell` contains parser, state, ML engine            |

## Design Highlights

- **Modular**: Commands are plugins (extend `ShellCommand`).
- **Testable**: All core logic is covered by unit tests.
- **Persistent**: History learned across sessions.
- **Smart suggestions**: Combines Markov prediction + fuzzy matching.
- **Clean code**: Proper separation of concerns (shell, commands, ML).

Perfect for:
- Academic submission (Java OOP + ML)
- Portfolio demonstration
- Learning object-oriented design patterns
- Understanding custom shell/REPL architectures

## Future Enhancements

- Command aliases and shell builtins (pushd, popd, etc.)
- Quoted argument parsing
- Command pipes and redirects (advanced)
- Readline/Jline integration for better UX
- Plugin system for custom commands
- Performance optimizations for large history

