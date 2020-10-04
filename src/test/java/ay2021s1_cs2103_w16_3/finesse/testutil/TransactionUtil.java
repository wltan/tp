package ay2021s1_cs2103_w16_3.finesse.testutil;

import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_DATE;
import static ay2021s1_cs2103_w16_3.finesse.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;

import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.AddIncomeCommand;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.EditCommand;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Income;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code transaction}.
     */
    public static String getAddCommand(Transaction transaction) {
        return AddCommand.COMMAND_WORD + " " + getTransactionDetails(transaction);
    }

    /**
     * Returns an add income command string for adding the {@code transaction}.
     */
    public static String getAddIncomeCommand(Income income) {
        return AddIncomeCommand.COMMAND_WORD + " " + getTransactionDetails(income);
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + transaction.getName().fullName + " ");
        sb.append(PREFIX_AMOUNT + transaction.getAmount().value + " ");
        sb.append(PREFIX_DATE + transaction.getDate().value + " ");
        transaction.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.categoryName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTransactionDescriptor}'s details.
     */
    public static String getEditTransactionDescriptorDetails(EditCommand.EditTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> categories = descriptor.getCategories().get();
            if (categories.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                categories.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.categoryName).append(" "));
            }
        }
        return sb.toString();
    }
}
