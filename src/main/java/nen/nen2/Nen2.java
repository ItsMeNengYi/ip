package nen.nen2;

import java.util.Scanner;

import nen.commands.Command;
import nen.utils.Parser;
import nen.utils.Storage;
import nen.utils.TaskList;
import nen.utils.Ui;


/**
 * This class represents a chatbot, name Nen2
 * @author Gan Ren Yick (A0276246X)
 */
public class Nen2 {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private String commandType;

    /**
     * Constructs Nen2 chatbot
     * @param filePath of the saved data
     */
    public Nen2(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
        parser = new Parser(tasks);
    }

    /**
     * Runs the Nen2
     */
    public void run() {
        Scanner messageReader = new Scanner(System.in);

        ui.greet();
        while (true) {
            Command c = parser.parse(messageReader.nextLine());
            c.execute(tasks);
            ui.print(c.getDescription());
            if (c.isEnd()) {
                break;
            }
        }
        storage.save(tasks.toDataStringArr());
        ui.exit();
    }

    /**
     * @param text input from user
     * @return response to the text
     */
    public String getResponse(String text) {
        Command c = parser.parse(text);
        assert c != null : "Parsed command shouldn't be null";
        c.execute(tasks);
        commandType = c.getName();
        return c.getDescription();
    }

    /**
     * @return command type of the latest command
     */
    public String getCommandType() {
        return commandType;
    }
}
