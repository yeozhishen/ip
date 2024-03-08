package fido.utilities;
/**
 * Manages the user interface
 * by printing messages to the command line
 */
public class UserInterface {
    private static final String STRING_DECORATION = "------------------------------------";
    private static final String GREETING = "Hello! I'm Fido"
            + System.lineSeparator()
            + "What can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";
    public void printGreetingMessage() {
        printMessage(GREETING);
    }
    /**
     * Prints the message to the command line with decorative strings
     * @param String message the message to be printed
     */
    public void printMessage(String message){
        System.out.println(Formatter.prettify(message, STRING_DECORATION, STRING_DECORATION));
    }
    public void printExitMessage() {
        printMessage(GOODBYE);
    }
}
