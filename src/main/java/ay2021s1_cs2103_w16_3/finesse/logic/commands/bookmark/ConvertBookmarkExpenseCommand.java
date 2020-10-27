package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Expense;

/**
 * Converts a specified bookmark expense and adds it as an expense to the finance tracker.
 */
public class ConvertBookmarkExpenseCommand extends ConvertBookmarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts the specified bookmark expense and adds"
            + " it as an expense to the finance tracker.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "03/10/2020 ";

    public static final String MESSAGE_CONVERT_BOOKMARK_EXPENSE_SUCCESS = "Bookmark expense has been converted "
            + "and successfully added to finance tracker: %1$s";

    private final Index targetIndex;
    private final Date date;

    /**
     * @param targetIndex Index of the bookmark expense in the filtered bookmark expense list to convert.
     * @param convertDate Date of converting a bookmark expense to an expense and adding it to the finance tracker.
     */
    public ConvertBookmarkExpenseCommand(Index targetIndex, Date convertDate) {
        this.targetIndex = targetIndex;
        this.date = convertDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkExpense> lastShownList = model.getFilteredBookmarkExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
        }

        BookmarkExpense bookmarkExpenseToBeConverted = lastShownList.get(targetIndex.getZeroBased());
        Expense newExpenseToAdd = bookmarkExpenseToBeConverted.convert(date);
        model.addExpense(newExpenseToAdd);
        return new CommandResult(String.format(MESSAGE_CONVERT_BOOKMARK_EXPENSE_SUCCESS, newExpenseToAdd), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConvertBookmarkExpenseCommand)) {
            return false;
        }

        ConvertBookmarkExpenseCommand otherConvertBookmarkExpenseCommand = (ConvertBookmarkExpenseCommand) other;
        return targetIndex.equals(otherConvertBookmarkExpenseCommand.targetIndex)
                && date.equals(otherConvertBookmarkExpenseCommand.date);
    }
}
