public class Parser {
    private Ui ui;
    private TaskList taskList;

    public Parser(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    public boolean parseInput(String input) {
        int arg;
        String action = input.split(" ")[0];
        String react = "";
        try{
            switch(action) {
                case "bye":
                    return false;
                case "mark":
                    arg = getIndex(input);
                    taskList.get(arg - 1).markAsDone();
                    react += "Nice! I've marked this task as done:" + "\n" +
                            taskList.get(arg - 1).toString() + "\n";
                    break;
                case "unmark":
                    arg = getIndex(input);
                    taskList.get(arg - 1).markAsNotDone();
                    react += "OK, I've marked this task as not done yet:" + "\n" +
                            taskList.get(arg - 1).toString() + "\n";
                    break;
                case "delete":
                    arg = getIndex(input);
                    react += "Noted. I've removed this task:" + "\n" +
                            taskList.get(arg - 1).toString() + "\n";
                    taskList.remove(arg - 1);
                    react += "Now you have " + taskList.size() + " tasks in the list." + "\n";
                    break;
                case "list":
                    react += "Here are the tasks in your list:" + "\n" +
                            taskList.toString();
                    break;
                default:
                    Task t = Task.of(input);
                    taskList.add(t);
                    react += "Got it. I've added this task: \n" + t + "\n" +
                            "Now you have " + taskList.size() +
                            " tasks in the list." + "\n";
            }
            ui.print(react);
        } catch (InvalidInputException |
                 ArgumentMissingException |
                 EmptyDescriptionException |
                 DateTimeFormatIncorrectException e){
            ui.print(e.getMessage() + "\n");
        }

        return true;
    }

    private int getIndex(String text) throws ArgumentMissingException, InvalidInputException{
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
