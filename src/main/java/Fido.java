public class Fido {
    TaskManager fidoTaskManager;
    Parser inputParser;
    UserInterface userInterface;
    public Fido() {
        this.fidoTaskManager = new TaskManager();
        this.inputParser = new Parser();
        this.userInterface = new UserInterface();
    }

    public void run() {
        while(true) {
            try {
                inputParser.collectUserInput();
                String command = inputParser.getUserInputCommand();
                String outputMessage = processInputCommand(command);
                userInterface.printMessage(outputMessage);
            } catch (IllegalArgumentException e) {
                userInterface.printMessage(Commands.INVALID_COMMAND.string + " " + e.getMessage());
            }
        }
    }
    private String processInputCommand(String command) {
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
        default:
            return Commands.INVALID_COMMAND.string;
        }
    }
    private void Exit() {
        userInterface.printExitMessage();
        System.exit(0);
    }
    private String addTodo() throws IllegalArgumentException {
        if(!inputParser.isValidTodo()) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_TODO.string);
        }
        String taskDescription = inputParser.getTaskDescription();
        return fidoTaskManager.addTask(new Todo(taskDescription));
    }
    private String addDeadline() throws IllegalArgumentException {
        if(!inputParser.isValidDeadline()) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_DEADLINE.string);
        }
        String byString = inputParser.getStringAfterKeywordUntilNextKeyword(ParserRegex.BY);
        String taskDescription = inputParser.getTaskDescription();
        return fidoTaskManager.addTask(new Deadline(taskDescription, byString));
    }
    private String addEvent() throws IllegalArgumentException {
        if(!inputParser.isValidEvent()) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_EVENT.string);
        }
        String fromString = inputParser.getStringAfterKeywordUntilNextKeyword(ParserRegex.FROM);
        String toString = inputParser.getStringAfterKeywordUntilNextKeyword(ParserRegex.TO);
        String taskDescription = inputParser.getTaskDescription();
        return fidoTaskManager.addTask(new Event(taskDescription, fromString, toString));
    }
    private void echoInput() {
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
    private String handleTaskMarking() {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndexForMarking();
            return fidoTaskManager.markTaskAsDone(stdoutTaskIndex);
        } catch (Exception e){
            return ErrorMessages.INVALID_MARK_COMMAND.string;
        }
    }
    private String handleUnmarkingTask() {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndexForMarking();
            return fidoTaskManager.unmarkTask(stdoutTaskIndex);
        } catch (Exception e) {
            return ErrorMessages.INVALID_MARK_COMMAND.string;
        }
    }
}
