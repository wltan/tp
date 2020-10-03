package ay2021s1_cs2103_w16_3.finesse.logic.commands;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;

/**
 * Adds a transaction to the address book.
 */
public class AddIncomeCommand extends Command {

    public static final String COMMAND_WORD = "add-income";
    public static final String COMMAND_ALIAS = "addi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an income to the finance tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Internship "
            + PREFIX_AMOUNT + "560 "
            + PREFIX_DATE + "03/10/2020 "
            + PREFIX_CATEGORY + "Work";

    public static final String MESSAGE_SUCCESS = "New income added: %1$s";
    public static final String MESSAGE_DUPLICATE_TRANSACTION = "This income already exists in the finance tracker";

    private final Income toAdd;

    /**
     * Creates an AddIncomeCommand to add the specified {@code Income}
     */
    public AddIncomeCommand(Income income) {
        requireNonNull(income);
        toAdd = income;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTransaction(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSACTION);
        }

        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIncomeCommand // instanceof handles nulls
                && toAdd.equals(((AddIncomeCommand) other).toAdd));
    }
}
