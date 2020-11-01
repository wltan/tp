package ay2021s1_cs2103_w16_3.finesse.ui;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.command.history.CommandHistory;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandHistory commandHistory;

    @FXML
    private HBox commandBoxContainer;
    @FXML
    private TextField commandTextField;
    @FXML
    private Label commandBoxLabel;
    @FXML
    private Button commandBoxButton;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, CommandHistory commandHistory) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = commandHistory;

        // Set up cycling through command history.
        commandTextField.setOnKeyPressed(keyEvent -> {
            String command;
            switch (keyEvent.getCode()) {
            case UP:
                command = commandHistory.navigateUp();
                break;
            case DOWN:
                command = commandHistory.navigateDown();
                break;
            default:
                return;
            }

            if (command == null) {
                commandTextField.clear();
            } else {
                // If 'command' is the empty string, 'setText' does nothing.
                commandTextField.setText(command);
            }

            // Set the caret position to the end of the text field.
            commandTextField.positionCaret(commandTextField.getLength());
        });

        commandBoxContainer.setSpacing(10);
        BooleanBinding isUserInputEmpty = new BooleanBinding() {
            {
                super.bind(commandTextField.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return commandTextField.getText().isEmpty();
            }
        };
        commandBoxButton.disableProperty().bind(isUserInputEmpty);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String userInput = commandTextField.getText();
            if (userInput.isEmpty()) {
                return;
            }

            // Push user input into command history.
            commandHistory.addCommand(userInput);

            // Clear text field.
            commandTextField.clear();

            commandExecutor.execute(userInput);
        } catch (CommandException | ParseException ignored) {
            // Do nothing.
        }
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see ay2021s1_cs2103_w16_3.finesse.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
