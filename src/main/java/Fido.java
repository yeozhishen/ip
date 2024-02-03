public class Fido {
    private static final String COMMAND_ADD_WORD = "add";
    private static final String COMMAND_LIST_WORD = "list";
    private static final String COMMAND_EXIT_WORD = "bye";
    private static final String COMMAND_MARK_WORD = "mark";
    private static final String COMMAND_UNMARK_WORD = "unmark";
    private static final String INVALID_MARK_COMMAND_STRING = "enter only 1 integer argument within range after the mark/unmark command";
    private static final String INVALID_COMMAND_STRING = "invalid command!";
    TaskManager FidoTaskManager;
    Parser inputParser;
    UserInterface userInterface;
    public Fido() {
        this.FidoTaskManager = new TaskManager();
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
                userInterface.printMessage(INVALID_COMMAND_STRING);
            }
        }
    }
    private String processInputCommand(String command){
        switch (command) {
        case COMMAND_EXIT_WORD:
            Exit();
        case COMMAND_LIST_WORD:
            return FidoTaskManager.getTaskList();
        case COMMAND_MARK_WORD:
            return handleTaskMarking();
        case COMMAND_UNMARK_WORD:
            return handleUnmarkingTask();
        default:
            return FidoTaskManager.addTask(inputParser.getUserInputString());
        }
    }
    private void Exit() {
        userInterface.printExitMessage();
        System.exit(0);
    }
    private void printlnHorizontalLine() {
        String line = "--------------------------------------";
        System.out.println(line);
    }
    private void echoInput() {
        String inputString;
        while (true) {
            inputParser.collectUserInput();
            inputString = inputParser.getUserInputString();
            printlnHorizontalLine();
            if (inputString.equals("bye")) {
                return;
            }
            System.out.println(inputString);
            printlnHorizontalLine();
        }
    }
    private String handleTaskMarking() {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndexForMarking();
            return FidoTaskManager.markTaskAsDone(stdoutTaskIndex);
        } catch (Exception e){
            return INVALID_MARK_COMMAND_STRING;
        }
    }
    private String handleUnmarkingTask() {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndexForMarking();
            return FidoTaskManager.unmarkTask(stdoutTaskIndex);
        } catch (Exception e) {
            return INVALID_MARK_COMMAND_STRING;
        }
    }
}
