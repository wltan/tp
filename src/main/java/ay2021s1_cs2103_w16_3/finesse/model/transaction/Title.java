package ay2021s1_cs2103_w16_3.finesse.model.transaction;

import static ay2021s1_cs2103_w16_3.finesse.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Transaction's title in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title implements Comparable<Title> {

    public static final String MESSAGE_CONSTRAINTS =
            "Titles should contain at least one non-whitespace printable ASCII character.";
    public static final String VALIDATION_REGEX = "\\p{Graph}\\p{Print}*";

    private final String fullTitle;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_CONSTRAINTS);
        fullTitle = title;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a String representation of this Title that can be used to construct an identical Title.
     * @return A String representation of this Title.
     */
    @Override
    public String toString() {
        return fullTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && fullTitle.equals(((Title) other).fullTitle)); // state check
    }

    @Override
    public int hashCode() {
        return fullTitle.hashCode();
    }

    @Override
    public int compareTo(Title title) {
        return fullTitle.compareTo(title.fullTitle);
    }
}
