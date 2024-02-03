public class Fido {
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
                userInterface.printMessage(COMMANDS.INVALID_COMMAND.string);
            }
        }
    }
    private String processInputCommand(String command){
        COMMANDS CommandEnum = COMMANDS.getCommandEnumeration(command);
        switch (CommandEnum) {
        case EXIT:
            Exit();
        case LIST:
            return FidoTaskManager.getTaskList();
        case MARK:
            return handleTaskMarking();
        case UNMARK:
            return handleUnmarkingTask();
        default:
            return FidoTaskManager.addTask(inputParser.getUserInputString());
        }
    }
    private void Exit() {
        userInterface.printExitMessage();
        System.exit(0);
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
            return FidoTaskManager.markTaskAsDone(stdoutTaskIndex);
        } catch (Exception e){
            return COMMANDS.INVALID_MARK_COMMAND.string;
        }
    }
    private String handleUnmarkingTask() {
        try {
            int stdoutTaskIndex = inputParser.getTaskIndexForMarking();
            return FidoTaskManager.unmarkTask(stdoutTaskIndex);
        } catch (Exception e) {
            return COMMANDS.INVALID_MARK_COMMAND.string;
        }
    }
}
