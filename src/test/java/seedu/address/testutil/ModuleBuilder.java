package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * A utility function to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE = "CS1010S";
    public static final String DEFAULT_TUTORIAL = "T01";

    private ModuleCode moduleCode;
    private ArrayList<TutorialClass> tutorialClasses;

    /**
     * Creates a {@code ModuleBuilder} with the default details.
     */
    public ModuleBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE);
        tutorialClasses = new ArrayList<>();
        tutorialClasses.add(new TutorialClass(DEFAULT_TUTORIAL));
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(ModuleCode moduleToCopy) {
        moduleCode = moduleToCopy.getModule();
        tutorialClasses = moduleToCopy.getTutorialClasses();
    }

    /**
     * Sets the {@code moduleCode} of the {@code ModuleCode} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code tutorialClass} of the {@code TutorialClass} that we are building.
     */
    public ModuleBuilder withTutorialClasses(String tutorialClass) {
        TutorialClass tutorialClassToAdd = new TutorialClass(tutorialClass);
        this.tutorialClasses = new ArrayList<>();
        this.tutorialClasses.add(tutorialClassToAdd);
        return this;
    }

    public ModuleCode build() {
        return new ModuleCode(moduleCode.value, tutorialClasses);
    }
}
