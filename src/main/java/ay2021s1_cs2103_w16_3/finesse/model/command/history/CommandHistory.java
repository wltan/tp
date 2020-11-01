package ay2021s1_cs2103_w16_3.finesse.model.command.history;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Keeps track of the user input history as well as the command history navigation state.
 */
public class CommandHistory {
    /** An {@code EvictingStack} to keep track of the command history. **/
    private final EvictingStack<String> commandHistory;
    /** A reference to the current {@code Node} in the current command navigation state. **/
    private Node<String> currentNode;

    /**
     * Creates a new {@code CommandHistory} which keeps track of the most recent commands.
     *
     * @param maxCommands The number of recent commands to keep track of.
     * @throws IllegalArgumentException If the specified maximum commands is not at least 1.
     */
    public CommandHistory(int maxCommands) {
        if (maxCommands < 1) {
            throw new IllegalArgumentException("Maximum number of commands must be at least 1.");
        }
        commandHistory = new EvictingStack<>(maxCommands);
    }

    /**
     * Adds a command to the command history and resets the state of the command navigation.
     *
     * @param command The command to be added.
     */
    public void addCommand(String command) {
        commandHistory.push(command);
        currentNode = null;
    }

    /**
     * Returns the previous command in the current command navigation state. If the current
     * command is already the earliest tracked command, then the current command is returned.
     * If the command history is empty, returns an empty string.
     *
     * @return The previous command.
     */
    public String navigateUp() {
        if (currentNode == null && commandHistory.isEmpty()) {
            return "";
        } else if (currentNode == null) {
            currentNode = commandHistory.getTopNode();
        } else if (currentNode.hasPreviousNode()) {
            currentNode = currentNode.getPreviousNode();
        }
        return currentNode.getValue();
    }

    /**
     * Returns the next command in the current command navigation state. If the current
     * command is already the latest tracked command, then {@code null} is returned.
     * If the command history is empty, returns an empty string.
     *
     * @return The next command.
     */
    public String navigateDown() {
        if (currentNode == null) {
            return "";
        } else if (currentNode.hasNextNode()) {
            currentNode = currentNode.getNextNode();
        } else {
            return null;
        }
        return currentNode.getValue();
    }

    /**
     * Returns a list of the most recent commands, up to a specified limit.
     * The list is ordered such that the first element is the most recently entered command,
     * and the last element is the least recently entered command.
     * If there are fewer commands stored in the command history than the specified limit,
     * the returned list will be smaller than the specified limit.
     *
     * @param range The maximum number of commands to be retrieved.
     * @return A list of the most recent commands, limited by {@code range} and the
     * number of commands stored in the command history.
     */
    public List<String> recentCommands(int range) {
        assert range >= 0;
        return stream().limit(range).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Returns a stream of command strings, from most recent to least recent.
     * Note that due to the lazy nature of streams, undefined behavior may occur
     * if the stream is not consumed immediately.
     *
     * @return A stream of command strings, from most recent to least recent.
     */
    private Stream<String> stream() {
        return Stream.iterate(commandHistory.getTopNode(), Objects::nonNull, Node::getPreviousNode).map(Node::getValue);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        List<String> myCommands = this.stream().collect(Collectors.toUnmodifiableList());
        List<String> otherCommands = other.stream().collect(Collectors.toUnmodifiableList());
        return myCommands.equals(otherCommands);
    }
}
