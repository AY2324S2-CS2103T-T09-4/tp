package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
/**
 * A utility class containing a set of typical modules and their associated tutorial classes.
 */
public class TypicalModules {
    public static final String CS2103T_CODE = "CS2103T";
    public static final TutorialClass CS2103T_TUT1 = new TutorialClass("T01");
    public static final TutorialClass CS2103T_TUT2 = new TutorialClass("T02");

    /**
     * Returns a list of typical modules with their associated tutorial classes.
     *
     * @return The list of typical modules.
     */
    public static List<ModuleCode> getTypicalModules() {
        List<ModuleCode> modules = new ArrayList<>();
        ModuleCode cs2103t = new ModuleCode(CS2103T_CODE, "T09");
        cs2103t.addTutorialClass(CS2103T_TUT1);
        cs2103t.addTutorialClass(CS2103T_TUT2);
        modules.add(cs2103t);
        return modules;
    }
}

