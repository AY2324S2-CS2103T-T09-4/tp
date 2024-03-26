package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.OS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import seedu.address.model.module.ModuleCode;

public class ModuleListPanelTest {

    private ModuleListPanel moduleListPanel;
    private ObservableList<ModuleCode> moduleCodes;
    private Consumer<ModuleCode> moduleCodeConsumer;

    @BeforeAll
    public static void initializeJavaFX() {
        // Initialize JavaFX only if not on Linux
        if (!OS.LINUX.isCurrentOs()) {
            new JFXPanel();
        }
    }

    @BeforeEach
    public void setUp() {
        if (!Boolean.getBoolean("gui.tests.enabled") || OS.LINUX.isCurrentOs()) {
            System.out.println("Skipping GUI test.");
            return;
        }
        moduleCodes = FXCollections.observableArrayList();
        moduleListPanel = new ModuleListPanel(moduleCodes, moduleCodeConsumer);
    }

    @Test
    public void constructor_withListOfModuleCodes_initializesListView() {
        if (!Boolean.getBoolean("gui.tests.enabled") || OS.LINUX.isCurrentOs()) {
            System.out.println("Skipping GUI test.");
            return;
        }
        assertNotNull(moduleListPanel.moduleListView);
    }

    @Test
    public void constructor_withListOfModuleCodes_setsCellFactory() {
        if (!Boolean.getBoolean("gui.tests.enabled") || OS.LINUX.isCurrentOs()) {
            System.out.println("Skipping GUI test.");
            return;
        }
        assertNotNull(moduleListPanel.moduleListView.getCellFactory());
    }

    @Test
    public void constructor_withListOfModuleCodes_displaysCorrectModuleCards() {
        // Disable GUI testing on Linux
        if (!Boolean.getBoolean("gui.tests.enabled") || OS.LINUX.isCurrentOs()) {
            System.out.println("Skipping GUI test.");
            return;
        }

        moduleCodes.add(new ModuleCode("CS2103"));
        assertEquals(1, moduleListPanel.moduleListView.getItems().size());
    }
}


