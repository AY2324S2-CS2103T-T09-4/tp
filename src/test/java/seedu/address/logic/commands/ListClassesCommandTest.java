package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.testutil.TypicalModules;

public class ListClassesCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        List<ModuleCode> typicalModules = TypicalModules.getTypicalModules();
        for (ModuleCode module : typicalModules) {
            model.addModule(module);
        }
    }

    @Test
    public void execute_listClasses_success() {
        ListClassesCommand listClassesCommand = new ListClassesCommand();
        CommandResult commandResult = listClassesCommand.execute(model);

        List<String> expectedOutput = TypicalModules.getTypicalModules().stream()
            .map(module -> module.toString() + ": "
                + module.getTutorialClasses().stream()
                    .map(TutorialClass::toString)
                    .collect(Collectors.joining(", ")))
            .collect(Collectors.toList());

        // Check if command result message contains the expected output
        assertTrue(expectedOutput.stream().allMatch(commandResult.getFeedbackToUser()::contains));
    }

    @Test
    public void execute_listClasses_noModulesAdded() {
        Model emptyModel = new ModelManager();
        ListClassesCommand listClassesCommand = new ListClassesCommand();
        CommandResult commandResult = listClassesCommand.execute(emptyModel);

        // Check if command result message states that no modules are added
        assertEquals(ListClassesCommand.MESSAGE_EMPTY, commandResult.getFeedbackToUser());
        // Check if the result is truly empty
        assertTrue(emptyModel.getAddressBook().getModuleList().isEmpty());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ListClassesCommand listClassesCommand = new ListClassesCommand();
        assertThrows(NullPointerException.class, () -> listClassesCommand.execute(null));
    }
}
