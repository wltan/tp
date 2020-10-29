package ay2021s1_cs2103_w16_3.finesse.ui.bookmark;

import java.util.Comparator;

import ay2021s1_cs2103_w16_3.finesse.model.bookmark.BookmarkTransaction;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Transaction;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class BookmarkTransactionCard<T extends Transaction> extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";
    private static final double PREFERRED_CARD_HEIGHT = 60.00;
    private static final double PREFERRED_CARD_WIDTH = 100.00;

    public final BookmarkTransaction<T> bookmarkTransaction;

    @FXML
    private VBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private FlowPane categories;
    @FXML
    private GridPane transactionDetails;

    /**
     * Creates a {@code BookmarkTransactionCard} with the given {@code BookmarkTransaction} and index to display.
     * The font size of the content is set based on the given {@code fontSize}.
     */
    public BookmarkTransactionCard(BookmarkTransaction<T> bookmarkTransaction, int displayedIndex, int fontSize) {
        super(FXML);
        this.bookmarkTransaction = bookmarkTransaction;
        String fontSizeParsedToString = String.valueOf(fontSize);
        String categoriesFontSizeParsedToString = String.valueOf(fontSize - 2);
        cardPane.setPrefHeight(PREFERRED_CARD_HEIGHT);
        cardPane.setPrefWidth(PREFERRED_CARD_WIDTH);

        id.setText(displayedIndex + ". ");
        id.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        title.setText(bookmarkTransaction.getTitle().toString());
        title.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        amount.setText(bookmarkTransaction.getAmount().toString());
        amount.setStyle(String.format("-fx-font-size: %spx", fontSizeParsedToString));

        bookmarkTransaction.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.getCategoryName()))
                .forEach(category -> {
                    Label newCategory = new Label(category.getCategoryName());
                    newCategory.setStyle("-fx-font-family: Eczar");
                    newCategory.setStyle(String.format("-fx-font-size: %spx", categoriesFontSizeParsedToString));
                    categories.getChildren().add(newCategory);
                });
        categories.setColumnHalignment(HPos.CENTER);
        categories.setRowValignment(VPos.CENTER);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookmarkTransactionCard)) {
            return false;
        }

        // state check
        BookmarkTransactionCard<?> card = (BookmarkTransactionCard<?>) other;
        return id.getText().equals(card.id.getText())
                && bookmarkTransaction.equals(card.bookmarkTransaction);
    }

}
