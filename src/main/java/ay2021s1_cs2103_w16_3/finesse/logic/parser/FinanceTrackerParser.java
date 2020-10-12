package ay2021s1_cs2103_w16_3.finesse.logic.parser;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_TAB_FORMAT;
import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ClearCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.Command;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.DeleteIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ExitCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.FindTransactionCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.HelpCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListExpenseCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.ListTransactionCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.TabCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState.Tab;

/**
 * Parses user input.
 */
public class FinanceTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput Full user input string.
     * @param uiState Current state of the UI.
     * @return The command based on the user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput, UiState uiState) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            final AddCommand baseAddCommand = new AddCommandParser().parse(arguments);
            final Transaction addCommandToAdd = baseAddCommand.getToAdd();
            switch (uiState.getCurrentTab()) {
            case EXPENSES:
                return new AddExpenseCommand(new Expense(
                        addCommandToAdd.getTitle(),
                        addCommandToAdd.getAmount(),
                        addCommandToAdd.getDate(),
                        addCommandToAdd.getCategories()
                ));
            case INCOME:
                return new AddIncomeCommand(new Income(
                        addCommandToAdd.getTitle(),
                        addCommandToAdd.getAmount(),
                        addCommandToAdd.getDate(),
                        addCommandToAdd.getCategories()
                ));
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.EXPENSES, Tab.INCOME));
            }

        case AddExpenseCommand.COMMAND_WORD:
        case AddExpenseCommand.COMMAND_ALIAS:
            return new AddExpenseCommandParser().parse(arguments);

        case AddIncomeCommand.COMMAND_WORD:
        case AddIncomeCommand.COMMAND_ALIAS:
            return new AddIncomeCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            final DeleteCommand baseDeleteCommand = new DeleteCommandParser().parse(arguments);
            switch (uiState.getCurrentTab()) {
            case EXPENSES:
                return new DeleteExpenseCommand(baseDeleteCommand);
            case INCOME:
                return new DeleteIncomeCommand(baseDeleteCommand);
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.EXPENSES, Tab.INCOME));
            }

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            final FindCommand baseFindCommand = new FindCommandParser().parse(arguments);
            switch (uiState.getCurrentTab()) {
            case OVERVIEW:
                return new FindTransactionCommand(baseFindCommand);
            case EXPENSES:
                return new FindExpenseCommand(baseFindCommand);
            case INCOME:
                return new FindIncomeCommand(baseFindCommand);
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.OVERVIEW, Tab.EXPENSES, Tab.INCOME));
            }

        case ListCommand.COMMAND_WORD:
            switch (uiState.getCurrentTab()) {
            case OVERVIEW:
                return new ListTransactionCommand();
            case EXPENSES:
                return new ListExpenseCommand();
            case INCOME:
                return new ListIncomeCommand();
            default:
                throw new ParseException(commandInvalidTabMessage(commandWord,
                        Tab.OVERVIEW, Tab.EXPENSES, Tab.INCOME));
            }

        case ListTransactionCommand.COMMAND_WORD:
        case ListTransactionCommand.COMMAND_ALIAS:
            return new ListTransactionCommand();

        case ListExpenseCommand.COMMAND_WORD:
        case ListExpenseCommand.COMMAND_ALIAS:
            return new ListExpenseCommand();

        case ListIncomeCommand.COMMAND_WORD:
        case ListIncomeCommand.COMMAND_ALIAS:
            return new ListIncomeCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case TabCommand.COMMAND_WORD:
            return new TabCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Error message to be used when a command is not applicable to the user's current tab.
     * @param command The command word that was used incorrectly.
     * @param tabs The tabs that the command is applicable to.
     * @return The error message to be displayed to the user.
     */
    private String commandInvalidTabMessage(String command, Tab... tabs) {
        return String.format(MESSAGE_INVALID_TAB_FORMAT, command,
                Stream.of(tabs).map(Tab::toString).collect(Collectors.joining(", ")));
    }

}
