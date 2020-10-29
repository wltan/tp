package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

/**
 * Represents a Transaction's amount in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount implements Comparable<Amount> {

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts should only contain non-negative numbers up to 8 digits, with an optional 2 decimal places or '$' prefix";
    public static final String VALIDATION_REGEX = "^\\$?\\d{1,8}(\\.\\d{2})?$";
    private final BigDecimal value;

    /**
     * Constructs an {@code Amount} from a string.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = new BigDecimal(amount.replaceFirst("^\\$", ""));
        assert value.compareTo(BigDecimal.ZERO) >= 0; // amount should be non-negative
    }

    /**
     * Constructs an {@code Amount} from a BigDecimal value.
     *
     * @param value A {@code BigDecimal} value
     */
    private Amount(BigDecimal value) {
        assert value.scale() <= 2;
        this.value = value;
    }

    /**
     * Constructs an {@code Amount} from a BigDecimal value.
     *
     * @param value A {@code BigDecimal} value
     * @return {@code Amount} with the specified value
     */
    public static Amount of(BigDecimal value) {
        return new Amount(value);
    }

    /**
     * Returns true if a given string is a valid amount.
     * A valid amount must only contain numbers, with an optional 2 decimal places or '$' prefix.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the value of the amount to be used in budget calculations.
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Returns true if the {@code Amount} has a non-negative value.
     */
    public boolean isNonNegative() {
        return value.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Returns a String representation of this Amount that can be used to construct an identical Amount.
     * @return A String representation of this Amount.
     */
    @Override
    public String toString() {
        return String.format("$%.2f", value.abs());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && (value.compareTo(((Amount) other).value)) == 0); // state check
    }

    @Override
    public int compareTo(Amount other) {
        return value.compareTo(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
