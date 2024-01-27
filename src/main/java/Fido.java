import java.util.Scanner;
public class Fido {
    TaskManager FidoTaskManager;
    Parser inputParser;

    public Fido() {
        this.FidoTaskManager = new TaskManager();
        this.inputParser = new Parser();
    }

    public void run() {
        Greet();
        mainLoop: while(true) {
            inputParser.collectUserInput();
            String command = inputParser.getUserInputCommand();
            switch (command) {
            case "bye":
                break mainLoop;
            case "list":
                FidoTaskManager.printTaskList();
                break;
            default:
                FidoTaskManager.addTask(inputParser.getUserInputString());
                break;
            }
        }
        Exit();

    }
    private void Greet() {
        String greeting = "Hello! I'm Fido\n"
                + "What can I do for you?";
        printlnHorizontalLine();
        System.out.println(greeting);
        printlnHorizontalLine();
    }
    private void Exit() {
        String exitString = "Bye. Hope to see you again soon!";
        System.out.println(exitString);
        printlnHorizontalLine();
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
    public static void main(String [] args){
        Fido chatbot = new Fido();
        chatbot.run();
    }
}
