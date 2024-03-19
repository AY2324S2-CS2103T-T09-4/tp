package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javafx.embed.swing.JFXPanel;
import seedu.address.model.module.ModuleCode;

public class ModuleCardTest {

    @BeforeAll
    public static void initializeJavaFX() {
        // Initialize JavaFX only if not on Linux
        if (!OS.LINUX.isCurrentOs()) {
            new JFXPanel();
        }
    }

    @DisabledOnOs(OS.LINUX)
    @Test
    public void constructor_validModuleCode_setsModuleCodeAndTutorialClasses() {
        if (!Boolean.getBoolean("gui.tests.enabled") || OS.LINUX.isCurrentOs()) {
            System.out.println("Skipping GUI test.");
            return;
        }
        ModuleCode moduleCode = new ModuleCode("CS2103");
        ModuleCard moduleCard = new ModuleCard(moduleCode);
        assertEquals(moduleCode, moduleCard.moduleCode);
        assertEquals(moduleCode.getModule().toString(), moduleCard.moduleCodeLabel.getText());
        assertEquals(moduleCode.getTutorialClasses().toString(), moduleCard.tutorialClassLabel.getText());
    }
}
