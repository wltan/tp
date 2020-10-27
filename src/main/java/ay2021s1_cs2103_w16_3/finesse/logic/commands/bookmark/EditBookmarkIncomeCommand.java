package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static ay2021s1_cs2103_w16_3.finesse.model.Model.PREDICATE_SHOW_ALL_BOOKMARK_INCOMES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * Edits the details of an existing bookmark income using its displayed index from the bookmark income list
 * in the income tab.
 */
public class EditBookmarkIncomeCommand extends EditBookmarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bookmark income identified "
            + "by the index number used in the displayed bookmark income list on the income tab. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5 ";

    public static final String MESSAGE_EDIT_BOOKMARK_INCOME_SUCCESS = "Edited Bookmark Income: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final EditBookmarkTransactionDescriptor editBookmarkIncomeDescriptor;

    /**
     * @param targetIndex Index of the bookmark income in the filtered bookmark income list to edit.
     * @param editBookmarkIncomeDescriptor Details to edit the bookmark income with.
     */
    public EditBookmarkIncomeCommand(Index targetIndex,
                                     EditBookmarkTransactionDescriptor editBookmarkIncomeDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editBookmarkIncomeDescriptor);

        this.targetIndex = targetIndex;
        this.editBookmarkIncomeDescriptor = editBookmarkIncomeDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkIncome> lastShownList = model.getFilteredBookmarkIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
        }

        BookmarkIncome bookmarkIncomeToEdit = lastShownList.get(targetIndex.getZeroBased());
        BookmarkIncome editedBookmarkIncome = createdEditedBookmarkIncome(bookmarkIncomeToEdit,
                editBookmarkIncomeDescriptor);

        model.setBookmarkIncome(bookmarkIncomeToEdit, editedBookmarkIncome);
        model.updateFilteredBookmarkIncomeList(PREDICATE_SHOW_ALL_BOOKMARK_INCOMES);
        return new CommandResult(String.format(MESSAGE_EDIT_BOOKMARK_INCOME_SUCCESS, editedBookmarkIncome));
    }

    /**
     * Creates and returns a {@code BookmarkIncome} with the details of {@code bookmarkIncomeToEdit}
     * edited with {@code editBookmarkIncomeDescriptor}.
     */
    private static BookmarkIncome createdEditedBookmarkIncome(BookmarkIncome bookmarkIncomeToEdit,
                                                              EditBookmarkTransactionDescriptor
                                                              editBookmarkIncomeDescriptor) {
        assert bookmarkIncomeToEdit != null;

        Title updatedTitle = editBookmarkIncomeDescriptor.getTitle().orElse(bookmarkIncomeToEdit.getTitle());
        Amount updatedAmount = editBookmarkIncomeDescriptor.getAmount().orElse(bookmarkIncomeToEdit.getAmount());
        Set<Category> updatedCategories = editBookmarkIncomeDescriptor.getCategories()
                .orElse(bookmarkIncomeToEdit.getCategories());

        return new BookmarkIncome(updatedTitle, updatedAmount, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookmarkIncomeCommand)) {
            return false;
        }

        // state check
        EditBookmarkIncomeCommand otherEditBookmarkIncomeCommand = (EditBookmarkIncomeCommand) other;
        return targetIndex.equals(otherEditBookmarkIncomeCommand.targetIndex)
                && editBookmarkIncomeDescriptor.equals(otherEditBookmarkIncomeCommand.editBookmarkIncomeDescriptor);
    }

}
