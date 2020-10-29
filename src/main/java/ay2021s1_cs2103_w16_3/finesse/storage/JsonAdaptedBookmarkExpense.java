package ay2021s1_cs2103_w16_3.finesse.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.IllegalValueException;
import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkExpense;
import ay2021s1_cs2103_w16_3.finesse.model.category.Category;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Title;

/**
 * JSON-friendly version of {@link BookmarkExpense}.
 */
public class JsonAdaptedBookmarkExpense {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "BookmarkExpense's %s is missing!";

    private final String title;
    private final String amount;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBookmarkExpense} with the given bookmark expense details.
     */
    @JsonCreator
    public JsonAdaptedBookmarkExpense(@JsonProperty("title") String title, @JsonProperty("amount") String amount,
                                      @JsonProperty("categories") List<JsonAdaptedCategory> categories) {
        this.title = title;
        this.amount = amount;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Converts a given {@code BookmarkExpense} into this class for Jackson use.
     */
    public JsonAdaptedBookmarkExpense(BookmarkExpense source) {
        title = source.getTitle().toString();
        amount = source.getAmount().toString();
        categories.addAll(source.getCategories().stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted bookmark expense object into the model's {@code BookmarkExpense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted bookmark expense.
     */
    public BookmarkExpense toModelType() throws IllegalValueException {
        final List<Category> transactionCategories = new ArrayList<>();
        for (JsonAdaptedCategory category : categories) {
            transactionCategories.add(category.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        final Set<Category> modelCategories = new HashSet<>(transactionCategories);
        return new BookmarkExpense(modelTitle, modelAmount, modelCategories);
    }

}
