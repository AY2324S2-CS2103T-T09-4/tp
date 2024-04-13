package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.module.TutorialTeamName;
import seedu.address.model.person.Person;

/**
 * Views all teams in a specified tutorial class of a module to the user.
 */
public class ViewTeamCommand extends Command {

    public static final String COMMAND_WORD = "/view_teams";
    public static final String MESSAGE_EMPTY = "No teams available in %1$s %2$s";
    public static final String MESSAGE_NO_TUTORIAL_CLASSES = "No tutorial classes available in %1$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with %1$s %2$s not found!";
    public static final String MESSAGE_NO_MODULE = "Module not available";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View teams of the tutorial class "
        + "based on their name or index. \n"
        + "Parameters: [name/NAME | index/TEAM_INDEX]"
        + PREFIX_MODULECODE + "MODULE CODE "
        + PREFIX_TUTORIALCLASS + "TUTORIAL CLASS "
        + "Example: " + COMMAND_WORD + " "
        + "name/Team 1 "
        + PREFIX_MODULECODE + "CS2103T "
        + PREFIX_TUTORIALCLASS + "T09 ";

    private final Prefix predicateType;
    private final String predicateValue;
    private final ModuleCode moduleCode;
    private final TutorialClass tutorialClass;
    /**
     * Constructs a ViewTeamCommand object with the specified predicate type, predicate value, module code,
     * and tutorial class.
     *
     * @param predicateType The type of predicate used for filtering teams (either PREFIX_NAME or PREFIX_INDEX).
     * @param predicateValue The value associated with the predicate (either team name or team index).
     * @param moduleCode The code of the module to which the tutorial class belongs.
     * @param tutorialClass The tutorial class to filter teams from.
     * @throws NullPointerException if any of the arguments are null.
     */
    public ViewTeamCommand(Prefix predicateType, String predicateValue, ModuleCode moduleCode,
                           TutorialClass tutorialClass) {
        requireAllNonNull(predicateType, predicateValue, moduleCode, tutorialClass);
        this.predicateType = predicateType;
        this.predicateValue = predicateValue;
        this.moduleCode = moduleCode;
        this.tutorialClass = tutorialClass;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        TutorialTeam teamToView;

        ModuleCode moduleInList = model.findModuleFromList(moduleCode);
        if (moduleInList == null) {
            return new CommandResult(MESSAGE_NO_MODULE);
        }
        if (moduleInList.getTutorialClasses().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_TUTORIAL_CLASSES, moduleInList));
        }
        TutorialClass tutorialInList = model.findTutorialClassFromList(tutorialClass, moduleInList);
        if (tutorialInList.getTeams().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_EMPTY, moduleInList, tutorialInList));
        }
        if (predicateType.equals(PREFIX_NAME)) {
            teamToView = findTeamByName(model, new TutorialTeamName(predicateValue), tutorialInList, moduleInList);
            if (teamToView == null || !tutorialInList.hasTeam(teamToView)) {
                throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, predicateType, predicateValue));
            }
        } else if (predicateType.equals(PREFIX_INDEX)) {
            if (!isInteger(predicateValue)) {
                return new CommandResult("Integer needed for predicate value");
            }
            teamToView = findTeamByIndex(model, Index.fromOneBased(Integer.parseInt(predicateValue)), tutorialClass,
                moduleInList);
            if (teamToView == null || !tutorialInList.hasTeam(teamToView)) {
                throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, predicateType, predicateValue));
            }
        } else {
            throw new CommandException("Invalid predicate type specified!");
        }
        StringBuilder studentsStringBuilder = new StringBuilder();
        for (Person student : teamToView.getStudents()) {
            studentsStringBuilder.append(student.toStringTwo()).append(", ");
        }
        String studentsString = studentsStringBuilder.toString();
        // Remove the trailing comma and space
        if (studentsString.length() > 2) {
            studentsString = studentsString.substring(0, studentsString.length() - 2);
        }
        model.getAddressBook().setStudentsInTeam(teamToView);
        return new CommandResult("Team Name: " + teamToView.getTeamName() + ", Team Capacity: "
            + teamToView.getTeamSize() + ", Students: " + studentsString);
    }

    private TutorialTeam findTeamByName(Model model, TutorialTeamName teamName, TutorialClass tutorialClass,
                                        ModuleCode moduleCode) {
        Predicate<TutorialTeam> namePredicate = tutorialTeam -> tutorialTeam.getTeamName()
            .trim().equalsIgnoreCase(teamName.toString());
        return model.searchTeamByPredicate(namePredicate, tutorialClass, moduleCode);
    }

    TutorialTeam findTeamByIndex(Model model, Index index, TutorialClass tutorialClass,
                                 ModuleCode moduleCode) throws CommandException {
        TutorialClass tutorialInList = model.findTutorialClassFromList(tutorialClass, moduleCode);
        Predicate<TutorialTeam> indexPredicate = tutorialTeam -> {
            ObservableList<TutorialTeam> filteredList = FXCollections.observableList(tutorialInList.getTeams());
            int teamIndex = filteredList.indexOf(tutorialTeam) + 1;
            return teamIndex == index.getOneBased();
        };
        TutorialTeam team = model.searchTeamByPredicate(indexPredicate, tutorialClass, moduleCode);
        if (team == null) {
            throw new CommandException("No team found.");
        }
        return team;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewTeamCommand)) {
            return false;
        }
        ViewTeamCommand command = (ViewTeamCommand) other;
        return moduleCode.equals(command.moduleCode)
            && tutorialClass.equals(command.tutorialClass);
    }
    /**
     * Checks if the given string can be parsed as an integer.
     *
     * @param s the string to be checked
     * @return true if the string can be parsed as an integer, false otherwise
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
