package seedu.address.storage;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Jackson-friendly version of {@link ModuleCode}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module name is missing!";

    private final String name;
    private final ArrayList<TutorialClass> tutorialClasses;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("name") String name, @JsonProperty("tutorialClasses")
        ArrayList<TutorialClass> tutorialClasses) {
        this.name = name;
        this.tutorialClasses = tutorialClasses;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(ModuleCode source) {
        name = source.toString();
        this.tutorialClasses = source.getTutorialClasses();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public ModuleCode toModelType() throws IllegalValueException {
        if (!ModuleCode.isValidModuleCode(name)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, name));
        }
        return new ModuleCode(name, tutorialClasses);
    }
}
