package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;

/**
 * Deletes a bookmark income identified using its displayed index from the finance tracker.
 */
public class DeleteBookmarkIncomeCommand extends DeleteBookmarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the bookmark income identified by the index number used in the displayed "
            + "bookmark income list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_BOOKMARK_INCOME_SUCCESS = "Deleted Bookmark Income: %1$s";

    private final Index targetIndex;

    public DeleteBookmarkIncomeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkIncome> lastShownList = model.getFilteredBookmarkIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
        }

        BookmarkIncome bookmarkIncomeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBookmarkIncome(bookmarkIncomeToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_BOOKMARK_INCOME_SUCCESS, bookmarkIncomeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookmarkIncomeCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBookmarkIncomeCommand) other).targetIndex)); // state check
    }
}
