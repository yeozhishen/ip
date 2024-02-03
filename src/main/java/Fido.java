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
                userInterface.printMessage(COMMANDS.INVALID_COMMAND.string + e.getMessage());
            }
        }
    }
    private String processInputCommand(String command){
        COMMANDS CommandEnum = COMMANDS.getCommandEnumeration(command);
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
            return COMMANDS.INVALID_COMMAND.string;
        }
    }
    private void Exit() {
        userInterface.printExitMessage();
        System.exit(0);
    }
    private String addTodo() throws IllegalArgumentException {
        if(!inputParser.isValidTodo()) {
            throw new IllegalArgumentException("invalid Todo");
        }
        String taskDescription = inputParser.getTaskDescription();
        return fidoTaskManager.addTask(new Todo(taskDescription));
    }
    private String addDeadline() throws IllegalArgumentException {
        if(!inputParser.isValidDeadline()) {
            throw new IllegalArgumentException("invalid deadline");
        }
        String byString = inputParser.getStringAfterKeywordUntilNextKeyword(PARSER_REGEX.BY);
        String taskDescription = inputParser.getTaskDescription();
        return fidoTaskManager.addTask(new Deadline(taskDescription, byString));
    }
    private String addEvent() throws IllegalArgumentException {
        if(!inputParser.isValidEvent()) {
            throw new IllegalArgumentException("invalid event");
        }
        String fromString = inputParser.getStringAfterKeywordUntilNextKeyword(PARSER_REGEX.FROM);
        String toString = inputParser.getStringAfterKeywordUntilNextKeyword(PARSER_REGEX.TO);
        String taskDescription = inputParser.getTaskDescription();
        return fidoTaskManager.addTask(new Event(taskDescription, fromString, toString));
    }
    private void echoInput() {
        String inputString;
        while (true) {
            inputParser.collectUserInput();
            inputString = inputParser.getUserInputString();
            if (inputString.equals("bye")) {
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
            return COMMANDS.INVALID_MARK_COMMAND.string;
        }
    }
    private String handleUnmarkingTask() {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndexForMarking();
            return fidoTaskManager.unmarkTask(stdoutTaskIndex);
        } catch (Exception e) {
            return COMMANDS.INVALID_MARK_COMMAND.string;
        }
    }
}
