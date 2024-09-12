package nen.utils;

import nen.commands.Command;
import nen.commands.DoNothingCommand;
import nen.exceptions.ArgumentMissingException;
import nen.exceptions.InvalidInputException;

/**
 * This class represents a parser deals with making sense of the user command
 * @author Gan Ren Yick (A0276246X)
 */
public class Parser {
    private TaskList taskList;

    /**
     * Creates a parser which determined action of user input and make changes
     * to taskList and give reaction to ui to print out
     * @param taskList of tasks
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Parses given input and make changes to taskList and give reaction to ui to print out
     * @param input by user
     * @return a boolean which determined if nen2 should continue read user input
     */
    public Command parse(String input) {
        String arg = "";
        String action = input.split(" ")[0];
        try {
            switch(action) {
            case "bye", "list", "find":
                break;
            case "mark", "delete", "unmark":
                arg = String.valueOf(getIndex(input));
                assert getIndex(input) >= 0 : "Index shouldn't be negative";
                break;
            default:
                action = input;
                break;
            }
            return Command.of(action, arg);
        } catch (InvalidInputException
                 | ArgumentMissingException e) {
            return new DoNothingCommand(e.getMessage() + "\n");
        }
    }

    /**
     * Gets the index of task for the command to execute on
     * @param text command input by user
     * @return index of task
     * @throws ArgumentMissingException when command doesn't contain an index
     * @throws InvalidInputException when the index in command is invalid
     */
    private int getIndex(String text) throws ArgumentMissingException, InvalidInputException {
        String[] arr = text.split(" ");
        if (arr.length < 2) {
            throw new ArgumentMissingException(arr[0] + " which one??");
        }
        try {
            int out = Integer.parseInt(arr[1]);
            if (out > taskList.size()) {
                throw new ArgumentMissingException("Helloo there's only " + taskList.size() + " tasks\n"
                        + "How to " + arr[0] + " " + out + "th task");
            }
            return out;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Huh?? \"" + arr[1] + "\" is not a number lah!");
        }
    }
}
