package ay2021s1_cs2103_w16_3.finesse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import ay2021s1_cs2103_w16_3.finesse.commons.core.Config;
import ay2021s1_cs2103_w16_3.finesse.commons.core.LogsCenter;
import ay2021s1_cs2103_w16_3.finesse.commons.core.Version;
import ay2021s1_cs2103_w16_3.finesse.commons.exceptions.DataConversionException;
import ay2021s1_cs2103_w16_3.finesse.commons.util.ConfigUtil;
import ay2021s1_cs2103_w16_3.finesse.commons.util.StringUtil;
import ay2021s1_cs2103_w16_3.finesse.logic.Logic;
import ay2021s1_cs2103_w16_3.finesse.logic.LogicManager;
import ay2021s1_cs2103_w16_3.finesse.model.FinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.Model;
import ay2021s1_cs2103_w16_3.finesse.model.ModelManager;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyFinanceTracker;
import ay2021s1_cs2103_w16_3.finesse.model.ReadOnlyUserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.UserPrefs;
import ay2021s1_cs2103_w16_3.finesse.model.util.SampleDataUtil;
import ay2021s1_cs2103_w16_3.finesse.storage.FinanceTrackerStorage;
import ay2021s1_cs2103_w16_3.finesse.storage.JsonFinanceTrackerStorage;
import ay2021s1_cs2103_w16_3.finesse.storage.JsonUserPrefsStorage;
import ay2021s1_cs2103_w16_3.finesse.storage.Storage;
import ay2021s1_cs2103_w16_3.finesse.storage.StorageManager;
import ay2021s1_cs2103_w16_3.finesse.storage.UserPrefsStorage;
import ay2021s1_cs2103_w16_3.finesse.ui.Ui;
import ay2021s1_cs2103_w16_3.finesse.ui.UiManager;
import ay2021s1_cs2103_w16_3.finesse.ui.UiState;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected UiState uiState;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Fine$$e ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        FinanceTrackerStorage financeTrackerStorage =
                new JsonFinanceTrackerStorage(userPrefs.getFinanceTrackerFilePath());
        storage = new StorageManager(financeTrackerStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        uiState = new UiState();

        ui = new UiManager(model, logic, uiState);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s finance tracker and {@code userPrefs}. <br>
     * The data from the sample finance tracker will be used instead if {@code storage}'s finance tracker is not found,
     * or an empty finance tracker will be used instead if errors occur when reading {@code storage}'s finance tracker.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyFinanceTracker> financeTrackerOptional;
        ReadOnlyFinanceTracker initialData;
        try {
            financeTrackerOptional = storage.readFinanceTracker();
            if (!financeTrackerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample FinanceTracker");
            }
            initialData = financeTrackerOptional.orElseGet(SampleDataUtil::getSampleFinanceTracker);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FinanceTracker");
            initialData = new FinanceTracker();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FinanceTracker");
            initialData = new FinanceTracker();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FinanceTracker");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Fine$$e " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Fine$$e ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
