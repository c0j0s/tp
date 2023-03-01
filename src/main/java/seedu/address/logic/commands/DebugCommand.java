package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * DebugCommand
 */
public class DebugCommand extends Command {

    public static final String COMMAND_WORD = "debug";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(COMMAND_WORD, false, false, true);
    }

}
