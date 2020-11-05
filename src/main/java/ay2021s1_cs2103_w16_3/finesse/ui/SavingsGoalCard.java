package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.concurrent.atomic.AtomicBoolean;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * A UI component displaying all the information from monthly budget.
 */
public class SavingsGoalCard extends UiPart<Region> {
    private static final String FXML = "SavingsGoalCard.fxml";
    private final Image savingsPanelPicture = new Image(this.getClass()
            .getResourceAsStream("/images/SavingsImage.png"));

    @FXML
    private Label monthlyExpenseLimit;
    @FXML
    private Label monthlySavingsGoal;
    @FXML
    private Label budgetStatus;
    @FXML
    private Label savingsStatus;
    @FXML
    private Pane imageContainer;
    @FXML
    private ImageView savingsPicture;

    /**
     * Creates a {@code }SavingsGoalCard} using the information from the given {@code MonthlyBudget}.
     */
    public SavingsGoalCard(MonthlyBudget monthlyBudget) {
        super(FXML);

        AtomicBoolean isBudgetDeficit = new AtomicBoolean();
        AtomicBoolean isSavingsDeficit = new AtomicBoolean();

        StringBinding expenseLimitBinding = Bindings.createStringBinding(() ->
                        String.format(
                                "Monthly Expense Limit: %s",
                                monthlyBudget.getMonthlyExpenseLimit().get().toString()),
                monthlyBudget.getMonthlyExpenseLimit());

        StringBinding savingsGoalBinding = Bindings.createStringBinding(() ->
                        String.format(
                                "Monthly Savings Goal: %s",
                                monthlyBudget.getMonthlySavingsGoal().get().toString()),
                monthlyBudget.getMonthlySavingsGoal());

        StringBinding budgetStatusBinding = Bindings.createStringBinding(() -> {
            if (monthlyBudget.getRemainingBudget().get().isNonNegative()) {
                budgetStatus.setStyle("-fx-text-fill: white");
                return String.format("Remaining Budget: %s",
                        monthlyBudget.getRemainingBudget().get().toString());
            } else {
                budgetStatus.setStyle("-fx-text-fill: red");
                return String.format("Budget Deficit: %s",
                        monthlyBudget.getRemainingBudget().get().toString());
            }
        }, monthlyBudget.getRemainingBudget());

        StringBinding savingsStatusBinding = Bindings.createStringBinding(() -> {
            if (monthlyBudget.getCurrentSavings().get().isNonNegative()) {
                savingsStatus.setStyle("-fx-text-fill: white");
                return String.format("Current Savings: %s",
                        monthlyBudget.getCurrentSavings().get().toString());
            } else {
                savingsStatus.setStyle("-fx-text-fill: red");
                return String.format("Savings Deficit: %s",
                        monthlyBudget.getCurrentSavings().get().toString());
            }
        }, monthlyBudget.getCurrentSavings());

        monthlyExpenseLimit.textProperty().bind(expenseLimitBinding);
        monthlySavingsGoal.textProperty().bind(savingsGoalBinding);
        budgetStatus.textProperty().bind(budgetStatusBinding);
        savingsStatus.textProperty().bind(savingsStatusBinding);
        savingsPicture.setImage(savingsPanelPicture);
        savingsPicture.layoutXProperty().bind(imageContainer.widthProperty()
                .subtract(Bindings.min(savingsPicture.fitHeightProperty(), savingsPicture.fitWidthProperty()))
                .divide(2));
    }
}
