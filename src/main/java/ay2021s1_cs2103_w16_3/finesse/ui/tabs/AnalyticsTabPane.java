package ay2021s1_cs2103_w16_3.finesse.ui.tabs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ay2021s1_cs2103_w16_3.finesse.model.budget.MonthlyBudget;
import ay2021s1_cs2103_w16_3.finesse.model.transaction.Amount.CalculatedAmount;
import ay2021s1_cs2103_w16_3.finesse.ui.UiPart;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Tab pane that displays analytics.
 */
public class AnalyticsTabPane extends UiPart<Canvas> {

    private static final String FXML = "AnalyticsTabPane.fxml";

    @FXML
    private Pane expenseAnalyticsPane;
    @FXML
    private Pane incomeAnalyticsPane;
    @FXML
    private Pane savingsAnalyticsPane;
    @FXML
    private Label expenseAnalyticsLabel;
    @FXML
    private Label incomeAnalyticsLabel;
    @FXML
    private Label savingsAnalyticsLabel;
    @FXML
    private BarChart<String, Number> expenseAnalyticsBarChart;
    @FXML
    private BarChart<String, Number> incomeAnalyticsBarChart;
    @FXML
    private BarChart<String, Number> savingsAnalyticsBarChart;

    /**
     * Creates an {@code AnalyticsTabPane}.
     */
    public AnalyticsTabPane(MonthlyBudget monthlyBudget) {
        super(FXML);

        Axis<String> expenseAnalyticsStringAxis = new CategoryAxis();
        Axis<Number> expenseAnalyticsNumberAxis = new NumberAxis();
        expenseAnalyticsBarChart = new BarChart<>(expenseAnalyticsStringAxis, expenseAnalyticsNumberAxis);
        expenseAnalyticsBarChart.setLegendVisible(false);
        expenseAnalyticsBarChart.setVerticalGridLinesVisible(false);
        expenseAnalyticsBarChart.setAnimated(false);
        expenseAnalyticsBarChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        expenseAnalyticsPane.getChildren().add(expenseAnalyticsBarChart);
        expenseAnalyticsLabel.setText("EXPENSE");

        Axis<String> incomeAnalyticsStringAxis = new CategoryAxis();
        Axis<Number> incomeAnalyticsNumberAxis = new NumberAxis();
        incomeAnalyticsBarChart = new BarChart<>(incomeAnalyticsStringAxis, incomeAnalyticsNumberAxis);
        incomeAnalyticsBarChart.setLegendVisible(false);
        incomeAnalyticsBarChart.setVerticalGridLinesVisible(false);
        incomeAnalyticsBarChart.setAnimated(false);
        incomeAnalyticsBarChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        incomeAnalyticsPane.getChildren().add(incomeAnalyticsBarChart);
        incomeAnalyticsLabel.setText("INCOME");

        Axis<String> savingsAnalyticsStringAxis = new CategoryAxis();
        Axis<Number> savingsAnalyticsNumberAxis = new NumberAxis();
        savingsAnalyticsBarChart = new BarChart<>(savingsAnalyticsStringAxis, savingsAnalyticsNumberAxis);
        savingsAnalyticsBarChart.setLegendVisible(false);
        savingsAnalyticsBarChart.setVerticalGridLinesVisible(false);
        savingsAnalyticsBarChart.setAnimated(false);
        savingsAnalyticsBarChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        savingsAnalyticsPane.getChildren().add(savingsAnalyticsBarChart);
        savingsAnalyticsLabel.setText("SAVINGS");

        populateData(monthlyBudget);
    }

    private void populateData(MonthlyBudget monthlyBudget) {
        Stream.of(
                populateDataIn(expenseAnalyticsBarChart, monthlyBudget.getMonths(), monthlyBudget.getMonthlyExpenses()),
                populateDataIn(incomeAnalyticsBarChart, monthlyBudget.getMonths(), monthlyBudget.getMonthlyIncomes()),
                populateDataIn(savingsAnalyticsBarChart, monthlyBudget.getMonths(), monthlyBudget.getMonthlySavings()))
                .forEach(monthlyBudget::addChangeListener);
    }

    private MonthlyBudget.ChangeListener populateDataIn(BarChart<String, Number> barChart,
                                                        ObservableList<String> strings,
                                                        ObservableList<CalculatedAmount> values) {
        assert strings.size() == values.size();

        class ZippedData extends ModifiableObservableListBase<XYChart.Data<String, Number>> {
            private final List<XYChart.Data<String, Number>> data = new ArrayList<>();

            {
                updateList();
            }

            @Override
            public XYChart.Data<String, Number> get(int index) {
                return data.get(index);
            }

            @Override
            public int size() {
                return data.size();
            }

            @Override
            protected void doAdd(int index, XYChart.Data<String, Number> element) {
                data.add(index, element);
            }

            @Override
            protected XYChart.Data<String, Number> doSet(int index, XYChart.Data<String, Number> element) {
                return data.set(index, element);
            }

            @Override
            protected XYChart.Data<String, Number> doRemove(int index) {
                return data.remove(index);
            }

            public void updateList() {
                this.clear();
                IntStream.range(0, strings.size())
                    .mapToObj(i -> new XYChart.Data<String, Number>(strings.get(i), values.get(i).getValue()))
                    .forEach(this::add);
            }
        }

        ZippedData newData = new ZippedData();
        barChart.getData().add(new XYChart.Series<>(newData));
        return newData::updateList;
    }
}
