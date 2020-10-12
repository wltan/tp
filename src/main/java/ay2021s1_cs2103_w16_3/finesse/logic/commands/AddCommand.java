package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * Adds a transaction to the finance tracker.
 * Depending on the current tab, either an Expense or an Income will be created.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the finance tracker, "
            + " based on the current tab."
            + "When on Income tab: Adds an income.\n"
            + "When on Expenses tab: Adds an expense.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Lunch "
            + PREFIX_AMOUNT + "$5 "
            + PREFIX_DATE + "13/10/2020 "
            + PREFIX_CATEGORY + "Food & Beverage";

    public static final String MESSAGE_SUCCESS = "New transaction added: %1$s";

    private final Transaction toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    public Transaction getToAdd() {
        return toAdd;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
