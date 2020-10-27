package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;

/**
 * Deletes a bookmark expense identified using its displayed index from the finance tracker.
 */
public class DeleteBookmarkExpenseCommand extends DeleteBookmarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the bookmark expense identified by the index number used in the displayed "
            + "bookmark expense list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_BOOKMARK_EXPENSE_SUCCESS = "Deleted Bookmark Expense: %1$s";

    private final Index targetIndex;

    public DeleteBookmarkExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkExpense> lastShownList = model.getFilteredBookmarkExpenseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_EXPENSE_DISPLAYED_INDEX);
        }

        BookmarkExpense bookmarkExpenseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBookmarkExpense(bookmarkExpenseToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BOOKMARK_EXPENSE_SUCCESS, bookmarkExpenseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookmarkExpenseCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBookmarkExpenseCommand) other).targetIndex)); // state check
    }
}
