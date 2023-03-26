package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.jobs.sorters.SortbyTime;

/**
 * Shows timetable of the requested week based on user's date input
 */
public class TimetableDateCommand extends Command {
    public static final String COMMAND_WORD = "timetable_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows timetable of scheduled/uncompleted jobs in the week, "
            + "based on user's input date\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2023-03-15";

    public static final String SHOWING_TIMETABLE_MESSAGE = "Show timetable of the week containing day: %s.";

    public static final SortbyTime SORTER = new SortbyTime();
    private final LocalDate jobDate;

    /**
     * Finds and shows timetable of jobs in week based on input date
     * @param jobDate input date
     */
    public TimetableDateCommand(LocalDate jobDate) {
        requireNonNull(jobDate);
        this.jobDate = jobDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        System.out.println("helu1");

        model.updateFocusDate(jobDate);
        model.updateSortedDeliveryJobList(SORTER);
        /*model.getSortedDeliveryJobListByDate();*/
        model.updateSortedDeliveryJobListByDate();
        model.updateWeekDeliveryJobList(jobDate);

        System.out.println(model.getSortedDeliveryJobList());
        System.out.println(model.getSortedDeliveryJobListByDate());
        System.out.print(model.getWeekDeliveryJobList());
        String showTimetableMessage = String.format(SHOWING_TIMETABLE_MESSAGE, jobDate.toString());
        return new CommandResult(showTimetableMessage, false, true, false, false, false);
    }

}
