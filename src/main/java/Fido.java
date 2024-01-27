import java.util.Scanner;
public class Fido {
    public void Greet() {
        String greeting = "Hello! I'm Fido\n"
                + "What can I do for you?";
        printlnHorizontalLine();
        System.out.println(greeting);
        printlnHorizontalLine();
    }
    public void Exit() {
        String exitString = "Bye. Hope to see you again soon!";
        System.out.println(exitString);
        printlnHorizontalLine();
    }
    private void printlnHorizontalLine() {
        String line = "--------------------------------------";
        System.out.println(line);
    }
    public void echoInput() {
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
        chatbot.Greet();
        chatbot.echoInput();
        chatbot.Exit();
    }
}
