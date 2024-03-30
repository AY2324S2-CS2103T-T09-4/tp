package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim();
        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(trimmedStudentId);
    }

    /**
     * Parses a {@code String moduleCode} into an {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String tutorial} into an {@code TutorialClass}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code tutorial} is invalid.
     */
    public static TutorialClass parseTutorialClass(String tutorial) throws ParseException {
        requireNonNull(tutorial);
        String trimmedTutorial = tutorial.trim();
        if (!TutorialClass.isValidTutorialClass(trimmedTutorial)) {
            throw new ParseException(TutorialClass.MESSAGE_CONSTRAINTS);
        }
        return new TutorialClass(trimmedTutorial);
    }

    /**
     * Parses a {@code String teams} into an {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code int} is invalid.
     */
    public static int parseNumberOfTeams(String teams) throws ParseException {
        requireNonNull(teams);
        String trimmedTeams = teams.trim();
        try {
            int numOfTeams = Integer.parseInt(trimmedTeams);
            return numOfTeams;
        } catch (NumberFormatException err) {
            throw new ParseException(TutorialTeam.MESSAGE_NUMBER_CONSTRAINTS);
        }
    }


    /**
     * Parses a {@code String team} into an {@code TutorialTeam}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code team} is invalid.
     */
    public static TutorialTeam parseTutorialTeam(String team) throws ParseException {
        requireNonNull(team);
        String trimmedTeam = team.trim();
        if (!TutorialTeam.isValidTeamName(trimmedTeam)) {
            throw new ParseException(TutorialTeam.MESSAGE_CONSTRAINTS);
        }
        return new TutorialTeam(trimmedTeam);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
