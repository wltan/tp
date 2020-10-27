package ay2021s1_cs2103_w16_3.finesse.logic.commands.bookmark;

import static ay2021s1_cs2103_w16_3.finesse.commons.core.Messages.MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkIncome;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Date;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Converts a specified bookmark income and adds it as an income to the finance tracker.
 */
public class ConvertBookmarkIncomeCommand extends ConvertBookmarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts the specified bookmark income and adds"
            + " it as an income to the finance tracker.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DATE + "03/10/2020 ";

    public static final String MESSAGE_CONVERT_BOOKMARK_INCOME_SUCCESS = "Bookmark income has been converted "
            + "and successfully added to finance tracker: %1$s";

    private final Index targetIndex;
    private final Date date;

    /**
     * @param targetIndex Index of the bookmark income in the filtered bookmark income list to convert.
     * @param convertDate Date of converting a bookmark income to an income and adding it to the finance tracker.
     */
    public ConvertBookmarkIncomeCommand(Index targetIndex, Date convertDate) {
        this.targetIndex = targetIndex;
        this.date = convertDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<BookmarkIncome> lastShownList = model.getFilteredBookmarkIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_BOOKMARK_INCOME_DISPLAYED_INDEX);
        }

        BookmarkIncome bookmarkIncomeToBeConverted = lastShownList.get(targetIndex.getZeroBased());
        Income newIncomeToAdd = bookmarkIncomeToBeConverted.convert(date);
        model.addIncome(newIncomeToAdd);
        return new CommandResult(String.format(MESSAGE_CONVERT_BOOKMARK_INCOME_SUCCESS, newIncomeToAdd), true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ConvertBookmarkIncomeCommand)) {
            return false;
        }

        ConvertBookmarkIncomeCommand otherConvertBookmarkIncomeCommand = (ConvertBookmarkIncomeCommand) other;
        return targetIndex.equals(otherConvertBookmarkIncomeCommand.targetIndex)
                && date.equals(otherConvertBookmarkIncomeCommand.date);
    }

}
