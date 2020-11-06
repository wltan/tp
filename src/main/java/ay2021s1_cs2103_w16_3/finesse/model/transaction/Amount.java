package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

/**
 * Represents a Transaction's amount in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount implements Comparable<Amount> {

    public static final Amount ZERO_AMOUNT = new Amount("0");
    public static final String MESSAGE_CONSTRAINTS = "Amounts should only contain non-negative numbers up to 8 digits,"
            + " with an optional 2 decimal places or '$' prefix";
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
     * Returns true if a given string is a valid amount.
     * A valid amount must only contain numbers, with an optional 2 decimal places or '$' prefix.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a String representation of this Amount that can be used to construct an identical Amount.
     * @return A String representation of this Amount.
     */
    @Override
    public String toString() {
        return String.format("$%.2f", value);
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

    /**
     * Represents a calculated amount, used for budget calculations.
     * It supports simple arithmetic such as addition and subtraction.
     * Note that unlike {@code Amount} which can only contain non-negative values,
     * {@code CalculatedAmount} can contain negative values due to subtraction.
     * Guarantees: immutable; has at most 2 decimal places
     */
    public static class CalculatedAmount {
        private final BigDecimal value;

        /**
         * Creates a new calculated amount with a default value of zero.
         */
        public CalculatedAmount() {
            this(Amount.ZERO_AMOUNT);
        }

        /**
         * Wraps an amount into a calculated amount.
         *
         * @param amount The amount to be wrapped.
         */
        public CalculatedAmount(Amount amount) {
            this(amount.value);
        }

        private CalculatedAmount(BigDecimal value) {
            this.value = value;
        }

        /**
         * Adds two calculated amounts.
         *
         * @param other The addend.
         * @return A calculated amount representing the added value.
         */
        public CalculatedAmount add(CalculatedAmount other) {
            return new CalculatedAmount(value.add(other.value));
        }

        /**
         * Subtracts two calculated amounts, with this calculated amount as the minuend
         * and the given argument as the subtrahend.
         *
         * @param other The subtrahend.
         * @return A calculated amount representing the subtracted value.
         */
        public CalculatedAmount subtract(CalculatedAmount other) {
            return new CalculatedAmount(value.subtract(other.value));
        }

        /**
         * Returns a number representing the value of this calculated amount.
         *
         * @return A number representing the value of this calculated amount.
         */
        public Number getValue() {
            return value;
        }

        /**
         * Returns true if the calculated amount has a non-negative value.
         */
        public boolean isNonNegative() {
            return value.compareTo(BigDecimal.ZERO) >= 0;
        }

        @Override
        public String toString() {
            return String.format("$%.2f", value.abs());
        }
    }

}
