package ay2021s1_cs2103_w16_3.finesse.ui;

import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.MainApp;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.commons.util.StringUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.Logic;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String APPLICATION_ICON = "/images/SavingsImage.png";

    private Model model;
    private Logic logic;
    private MainWindow mainWindow;
    private UiState uiState;

    /**
     * Creates a {@code UiManager} with the given {@code Logic}.
     */
    public UiManager(Model model, Logic logic, UiState uiState) {
        super();
        this.model = model;
        this.logic = logic;
        this.uiState = uiState;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        // Set the application icon.
        primaryStage.getIcons().add(getImage(APPLICATION_ICON));

        try {
            loadCustomFonts();
            mainWindow = new MainWindow(primaryStage, model, logic, uiState);
            mainWindow.show(); // This should be called before creating other UI parts.
            mainWindow.fillInnerParts();
            mainWindow.initializeTabs();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private void loadCustomFonts() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/RobotoCondensed-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Eczar-Regular.ttf"), 16);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

}
