package fido;
import fido.datastructures.Deadline;
import fido.datastructures.Event;
import fido.datastructures.Task;
import fido.datastructures.Todo;
import fido.enumerators.Commands;
import fido.enumerators.ErrorMessages;
import fido.enumerators.ParserRegex;
import fido.exceptions.FidoException;
import fido.utilities.FileManager;
import fido.utilities.Formatter;
import fido.utilities.Parser;
import fido.utilities.TaskManager;
import fido.utilities.UserInterface;
import java.util.List;
/*
 * The main class that runs the Fido program
 * driver logic for the program
 * integrating functionality from parser, task manager, user interface and file manager
 */
public class Fido {
    private static final String RECREATING_FILE_STRING = ", attempting to deleting and recreating file to salvage";
    TaskManager fidoTaskManager;
    Parser inputParser;
    UserInterface userInterface;
    FileManager fileManager;
    /**
     * Constructor for Fido
     * Initializes the task manager, parser, user interface and file manager
     * and loads tasks from the file/creates the file if it does not exist
     * recreates the file if there are parsing issues
     * ends the program if there are issues with the file
     */
    public Fido() {
        this.fidoTaskManager = new TaskManager();
        this.inputParser = new Parser();
        this.userInterface = new UserInterface();
        this.fileManager = new FileManager();
        try {
            String resultOfFileCheck = fileManager.ensureFileExists();
            loadTasksFromFile();
            userInterface.printMessage(resultOfFileCheck);
        } catch (Exception e) {
            try {
                userInterface.printMessage(e.getMessage() + RECREATING_FILE_STRING);
                String recreationMessage = fileManager.recreateFile();
                userInterface.printMessage(recreationMessage);
            } catch (Exception e2) {
                userInterface.printMessage(e.getMessage());
                Exit();
            }
        }
        userInterface.printGreetingMessage();
    }
    /**
     * Main method that runs the Fido program
     * continuously collects user input, processes the input and prints the output
     */
    public void run() {
        while (true) {
            try {
                inputParser.collectUserInput();
                String command = inputParser.getUserInputCommand();
                String outputMessage = processInputCommand(command);
                userInterface.printMessage(outputMessage);
            } catch (FidoException e) {
                userInterface.printMessage(Commands.INVALID_COMMAND.string + " " + e.getMessage());
            }
        }
    }
    /**
     * Processes the user input command and returns the output message
     * @param String command the user input command
     * @return String the output message of the command executed
     * @throws FidoException relaying erros messages from the command methods
     */
    private String processInputCommand(String command) throws FidoException {
        Commands CommandEnum = Commands.getCommandEnumeration(command);
        switch (CommandEnum) {
        case EXIT:
            Exit();
        case LIST:
            return fidoTaskManager.getTaskList();
        case MARK:
            return handleTaskMarking();
        case UNMARK:
            return handleUnmarkingTask();
        case TODO:
            return addTodo();
        case DEADLINE:
            return addDeadline();
        case EVENT:
            return addEvent();
        case DELETE:
            return deleteTask();
        case FIND:
            return findTasks();
        default:
            return Commands.INVALID_COMMAND.string;
        }
    }
    private void Exit() {
        userInterface.printExitMessage();
        System.exit(0);
    }
    /**
     * Adds a todo to the task list
     * @return String the string representation of the todo that was added
     * @throws FidoException if the todo is invalid
     */
    private String addTodo() throws FidoException {
        if (!inputParser.isValidTodo()) {
            throw new FidoException(ErrorMessages.INVALID_TODO.string);
        }
        String taskDescription = inputParser.getTaskDescription();
        Todo todo = new Todo(taskDescription);
        fileManager.save(todo);
        return fidoTaskManager.addTask(todo);
    }
    /**
     * Adds a deadline to the task list
     * @return String the string representation of the deadline that was added
     * @throws FidoException if the deadline is invalid
     */
    private String addDeadline() throws FidoException {
        if (!inputParser.isValidDeadline()) {
            throw new FidoException(ErrorMessages.INVALID_DEADLINE.string);
        }
        String byString = inputParser.getStringAfterKeywordUntilNextKeyword(ParserRegex.BY);
        String taskDescription = inputParser.getTaskDescription();
        Deadline deadline = new Deadline(taskDescription, byString);
        fileManager.save(deadline);
        return fidoTaskManager.addTask(deadline);
    }
    /**
     * Adds an event to the task list
     * @return String the string representation of the event that was added
     * @throws FidoException if the event is invalid
     */
    private String addEvent() throws FidoException {
        if (!inputParser.isValidEvent()) {
            throw new FidoException(ErrorMessages.INVALID_EVENT.string);
        }
        String fromString = inputParser.getStringAfterKeywordUntilNextKeyword(ParserRegex.FROM);
        String toString = inputParser.getStringAfterKeywordUntilNextKeyword(ParserRegex.TO);
        String taskDescription = inputParser.getTaskDescription();
        Event event = new Event(taskDescription, fromString, toString);
        fileManager.save(event);
        return fidoTaskManager.addTask(event);
    }
    /**
     * Marks a task as done in the task list
     * @return String the string representation of the task that was marked as done
     * @throws FidoException if the index is out of range
     */
    private String handleTaskMarking() throws FidoException {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndex();
            String unmarkedTask = Formatter.convertToFileFormat(fidoTaskManager.getTask(stdoutTaskIndex));
            String message = fidoTaskManager.markTaskAsDone(stdoutTaskIndex);
            String markedTask = Formatter.convertToFileFormat(fidoTaskManager.getTask(stdoutTaskIndex));
            fileManager.overwriteLine(unmarkedTask, markedTask);
            return message;
        } catch (IndexOutOfBoundsException e){
            throw new FidoException(ErrorMessages.INDEX_OUT_OF_BOUNDS.string);
        }
    }
    /**
     * Marks a task as not done in the task list
     * @return String the string representation of the task that was marked as not done
     * @throws FidoException if the index is out of range
     */
    private String handleUnmarkingTask() throws FidoException {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndex();
            String markedTask = Formatter.convertToFileFormat(fidoTaskManager.getTask(stdoutTaskIndex));
            String message = fidoTaskManager.unmarkTask(stdoutTaskIndex);
            String unmarkedTask = Formatter.convertToFileFormat(fidoTaskManager.getTask(stdoutTaskIndex));
            fileManager.overwriteLine(markedTask, unmarkedTask);
            return message;
        } catch (IndexOutOfBoundsException e) {
            throw new FidoException(ErrorMessages.INDEX_OUT_OF_BOUNDS.string);
        }
    }
    /**
     * Deletes a task from the task list
     * @return String the string representation of the task that was deleted
     * @throws FidoException if the index is out of range
     */
    private String deleteTask() throws FidoException {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndex();
            String taskToDelete = Formatter.convertToFileFormat(fidoTaskManager.getTask(stdoutTaskIndex));
            String message = fidoTaskManager.deleteTask(stdoutTaskIndex);
            fileManager.overwriteLine(System.lineSeparator() + taskToDelete, "");
            return message;
        } catch (IndexOutOfBoundsException e) {
            throw new FidoException(ErrorMessages.INDEX_OUT_OF_BOUNDS.string);
        }
    }
    /**
     * Finds tasks using a keyword
     * @return String the string representation of the tasks that were found
     * @throws FidoException if the find command does not have a keyword supplied
     */
    private String findTasks() throws FidoException{
        String keyword = inputParser.getFindKeyword();
        return fidoTaskManager.findTasksUsingKeyword(keyword);
    }
    /**
     * Loads tasks from the file
     * @throws FidoException if there are issues with the file
     */
    private void loadTasksFromFile() throws FidoException {
        try {
            String taskFileContents = fileManager.readFile();
            List<Task> taskList = Formatter.convertToTaskListFromFileFormat(taskFileContents);
            for (Task task : taskList) {
                fidoTaskManager.addTask(task);
            }
        } catch (Exception e) {
            throw new FidoException(ErrorMessages.FILE_LOAD_ERROR.string);
        }
    }
}
