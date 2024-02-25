package fido;

import fido.datastructures.Deadline;
import fido.datastructures.Event;
import fido.datastructures.Task;
import fido.datastructures.Todo;
import fido.enumerators.Commands;
import fido.enumerators.ErrorMessages;
import fido.enumerators.ParserRegex;
import fido.exceptions.FidoException;
import fido.utilities.*;

import java.util.List;
public class Fido {
    TaskManager fidoTaskManager;
    Parser inputParser;
    UserInterface userInterface;
    FileManager fileManager;
    public Fido() {
        this.fidoTaskManager = new TaskManager();
        this.inputParser = new Parser();
        this.userInterface = new UserInterface();
        this.fileManager = new FileManager();
        try {
            String resultOfFileCheck = fileManager.ensureFileExists();
            loadTasksFromFile();
            userInterface.printMessage(resultOfFileCheck);
        } catch (FidoException e) {
            userInterface.printMessage(e.getMessage());
            Exit();
        }
        userInterface.printGreetingMessage();
    }
    public void run() {
        while(true) {
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
        default:
            return Commands.INVALID_COMMAND.string;
        }
    }
    private void Exit() {
        userInterface.printExitMessage();
        System.exit(0);
    }
    private String addTodo() throws FidoException {
        if (!inputParser.isValidTodo()) {
            throw new FidoException(ErrorMessages.INVALID_TODO.string);
        }
        String taskDescription = inputParser.getTaskDescription();
        Todo todo = new Todo(taskDescription);
        fileManager.save(todo);
        return fidoTaskManager.addTask(todo);
    }
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
    private void echoInput() throws FidoException {
        String inputString;
        while (true) {
            inputParser.collectUserInput();
            inputString = inputParser.getUserInputString();
            if (inputString.equals(Commands.EXIT.string)) {
                return;
            }
            System.out.println(inputString);
        }
    }
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
    private void loadTasksFromFile() throws FidoException {
        String taskFileContents = fileManager.readFile();
        List<Task> taskList = Formatter.convertToTaskListFromFileFormat(taskFileContents);
        for (Task task : taskList) {
            fidoTaskManager.addTask(task);
        }
    }
}
