package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.Comparator;

import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private BorderPane cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label date;
    @FXML
    private HBox categories;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a {@code TransactionCard} with the given {@code Transaction} and index to display.
     * Binds the width of the {@code TransactionCard} to that of its containing list.
     */
    public TransactionCard(Transaction transaction, int displayedIndex, ReadOnlyDoubleProperty width) {
        super(FXML);
        this.transaction = transaction;
        cardPane.maxWidthProperty().bind(width.subtract(32));
        id.setText(displayedIndex + ". ");
        title.setText(transaction.getTitle().toString());
        amount.setText(transaction.getAmount().toString());
        transaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.getCategoryName()))
                .forEach(category -> {
                    Label newCategory = new Label(category.getCategoryName());
                    categories.getChildren().add(newCategory);
                });
        date.setText(transaction.getDate().toString());

        // Hijack scroll wheel for horizontal scrolling instead of vertical scrolling.
        scrollPane.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() != 0) {
                scrollPane.setHvalue(scrollPane.getHvalue() - scrollEvent.getDeltaY() / categories.getWidth());
                // Do not pass scroll event up to parent.
                scrollEvent.consume();
            }
        });

        // Hijack scroll events on category labels and pass on to scroll pane.
        categories.setOnScroll(scrollEvent -> {
            scrollPane.fireEvent(scrollEvent);
            scrollEvent.consume();
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionCard)) {
            return false;
        }

        // state check
        TransactionCard card = (TransactionCard) other;
        return id.getText().equals(card.id.getText())
                && transaction.equals(card.transaction);
    }
}