package ay2021s1_cs2103_w16_3.finesse.ui;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.GuiSettings;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.logic.Logic;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.CommandResult;
import ay2021s1_cs2103_w16_3.finesse.logic.commands.exceptions.CommandException;
import ay2021s1_cs2103_w16_3.finesse.logic.parser.exceptions.ParseException;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.AnalyticsTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.ExpenseTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.IncomeTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.OverviewTabPane;
import ay2021s1_cs2103_w16_3.finesse.ui.tabs.UserGuideTabPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private static final String WELCOME_MESSAGE = "Welcome to Fine$$e - your personal finance tracker."
            + "\nPlease enter the command \"help\" to view the user guide on the various commands you can use.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Model model;
    private final Logic logic;
    private final UiState uiState;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private Button commandBoxButton;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private Tab overviewTab;
    @FXML
    private Tab incomeTab;
    @FXML
    private Tab expenseTab;
    @FXML
    private Tab analyticsTab;
    @FXML
    private Tab userGuideTab;
    @FXML
    private ToggleButton userGuideButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private Text expenseLimit;

    // Window size and position information when application is not fullscreen.
    private double windowWidth;
    private double windowHeight;
    private int xPosition;
    private int yPosition;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage}, {@code Logic} and {@code UiState}.
     */
    public MainWindow(Stage primaryStage, Model model, Logic logic, UiState uiState) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.model = model;
        this.logic = logic;
        this.uiState = uiState;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        // Keep track of window size and position when application is not fullscreen.
        initializeWindowListeners();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser(WELCOME_MESSAGE);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, model.getCommandHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Initialize the contents of each tab.
     */
    public void initializeTabs() {
        // Set up tab contents.
        OverviewTabPane overviewTabPane =
                new OverviewTabPane(logic.getFilteredTransactionList(), logic.getMonthlyBudget());
        overviewTab.setContent(overviewTabPane.getRoot());

        IncomeTabPane incomeTabPane =
                new IncomeTabPane(logic.getFilteredIncomeList(), logic.getFilteredBookmarkIncomeList());
        incomeTab.setContent(incomeTabPane.getRoot());

        ExpenseTabPane expenseTabPane =
                new ExpenseTabPane(logic.getFilteredExpenseList(), logic.getFilteredBookmarkExpenseList());
        expenseTab.setContent(expenseTabPane.getRoot());

        AnalyticsTabPane analyticsTabPane = new AnalyticsTabPane(logic.getMonthlyBudget());
        analyticsTab.setContent(analyticsTabPane.getRoot());

        UserGuideTabPane userGuideTabPane = new UserGuideTabPane();
        userGuideTab.setContent(userGuideTabPane.getRoot());

        // Set default selection.
        SelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(overviewTab);

        // Set the bottom anchor after the tabs have been initialized.
        AnchorPane.setBottomAnchor(tabPane, 0.0);

        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldTabIndex, newTabIndex) -> {
            // Update UI state on tab change.
            uiState.setCurrentTab(UiState.Tab.values()[newTabIndex.intValue()]);

            // Disable the user guide tab when not selected to prevent the user from clicking the invisible tab.
            userGuideTab.setDisable(newTabIndex.intValue() != UiState.Tab.USER_GUIDE.getTabIndex().getZeroBased());

            // Set the user guide button to be unselected.
            userGuideButton.setSelected(false);
        });

        // Disable tab scrolling using arrow keys.
        tabPane.addEventFilter(KeyEvent.ANY, Event::consume);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        // Set minimum window size.
        primaryStage.setMinHeight(650.0);
        primaryStage.setMinWidth(900.0);

        // Retrieve window size and position from previous session.
        windowHeight = guiSettings.getWindowHeight();
        windowWidth = guiSettings.getWindowWidth();
        if (guiSettings.getWindowCoordinates() != null) {
            xPosition = (int) guiSettings.getWindowCoordinates().getX();
            yPosition = (int) guiSettings.getWindowCoordinates().getY();
        }

        // Restore window size and position from previous session.
        primaryStage.setHeight(windowHeight);
        primaryStage.setWidth(windowWidth);
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(xPosition);
            primaryStage.setY(yPosition);
        }
        primaryStage.setMaximized(guiSettings.isFullscreen());
    }

    /**
     * Initializes listeners for the window size and position.
     */
    private void initializeWindowListeners() {
        primaryStage.widthProperty().addListener(((observableValue, oldWidth, newWidth) -> {
            if (!primaryStage.isMaximized()) {
                windowWidth = newWidth.doubleValue();
            }
        }));
        primaryStage.heightProperty().addListener((observableValue, oldHeight, newHeight) -> {
            if (!primaryStage.isMaximized()) {
                windowHeight = newHeight.doubleValue();
            }
        });
        // The x and y properties are set before the application is considered maximised.
        // Hence, we check whether they are non-negative values instead when setting.
        primaryStage.xProperty().addListener((observableValue, oldX, newX) -> {
            if (newX.intValue() >= 0) {
                xPosition = newX.intValue();
            }
        });
        primaryStage.yProperty().addListener((observableValue, oldY, newY) -> {
            if (newY.intValue() >= 0) {
                yPosition = newY.intValue();
            }
        });
    }

    /**
     * Switches to the hidden user guide tab and sets the user guide button to be selected.
     */
    @FXML
    public void switchToUserGuideTab() {
        tabPane.getSelectionModel().select(userGuideTab);
        userGuideButton.setSelected(true);
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings =
                new GuiSettings(windowWidth, windowHeight, xPosition, yPosition, primaryStage.isMaximized());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see ay2021s1_cs2103_w16_3.finesse.logic.Logic#execute(String, UiState)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText, uiState);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                switchToUserGuideTab();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isCalculateBudgetInfo()) {
                logic.calculateBudgetInfo();
            }

            Optional<UiState.Tab> tabToSwitchTo = commandResult.getTabToSwitchTo();
            tabToSwitchTo.ifPresent(this::switchTabs);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Programmatically switches UI tab based on the specified tab.
     *
     * @param tab The tab to switch to.
     * @throws NullPointerException If the tab is {@code null}.
     */
    private void switchTabs(UiState.Tab tab) {
        requireNonNull(tab);
        tabPane.getSelectionModel().select(tab.getTabIndex().getZeroBased());
        if (tab.equals(UiState.Tab.OVERVIEW)) {
            logic.calculateBudgetInfo();
        }
    }
}
