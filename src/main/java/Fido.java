import java.util.Scanner;
public class Fido {
    TaskManager FidoTaskManager;

    public Fido() {
        this.FidoTaskManager = new TaskManager();
    }

    public void run() {
        String inputLine;
        Scanner in = new Scanner(System.in);
        Greet();
        mainLoop: while(true) {
            inputLine = in.nextLine();
            String command = inputLine.split(" ")[0];
            switch (command) {
            case "bye":
                break mainLoop;
            case "list":
                FidoTaskManager.printTaskList();
                break;
            default:
                FidoTaskManager.addTask(inputLine);
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
        String inputLine;
        Scanner in = new Scanner(System.in);
        while (true) {
            inputLine = in.nextLine();
            printlnHorizontalLine();
            if (inputLine.equals("bye")) {
                return;
            }
            System.out.println(inputLine);
            printlnHorizontalLine();
        }
    }
    public static void main(String [] args){
        Fido chatbot = new Fido();
        chatbot.run();
    }
}
