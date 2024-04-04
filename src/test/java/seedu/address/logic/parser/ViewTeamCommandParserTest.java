package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

public class ViewTeamCommandParserTest {

    private final ViewTeamCommandParser parser = new ViewTeamCommandParser();

    @Test
    public void parse_validInput_success() throws ParseException {
        // Valid input with name predicate
        assertEquals(parser.parse(" name/TeamA module/CS2103T tutorial/T09"),
            new ViewTeamCommand(PREFIX_NAME, "TeamA", new ModuleCode("CS2103T"), new TutorialClass("T09")));

        // Valid input with index predicate
        assertEquals(parser.parse(" index/1 module/CS2103T tutorial/T09"),
            new ViewTeamCommand(PREFIX_INDEX, "1", new ModuleCode("CS2103T"), new TutorialClass("T09")));
    }

    @Test
    public void parse_missingModuleCode_throwsParseException() {
        // Missing module code
        assertThrows(ParseException.class, () -> parser.parse(" name/TeamA tutorial/T09"));
    }

    @Test
    public void parse_missingTutorialClass_throwsParseException() {
        // Missing tutorial class
        assertThrows(ParseException.class, () -> parser.parse(" name/TeamA module/CS2103T"));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // Invalid prefix
        assertThrows(ParseException.class, () -> parser.parse(" x/TeamA module/CS2103T tutorial/T09"));
    }
}
