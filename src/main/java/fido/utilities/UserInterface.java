package fido.utilities;

public class UserInterface {
    private static final String STRING_DECORATION = "------------------------------------";
    private static final String GREETING = "Hello! I'm Fido"
            + System.lineSeparator()
            + "What can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    public UserInterface() {
        System.out.println(Formatter.prettify(GREETING, STRING_DECORATION, STRING_DECORATION));
    }
    public void printMessage(String message){
        System.out.println(Formatter.prettify(message, STRING_DECORATION, STRING_DECORATION));
    }
    public void printExitMessage() {
        System.out.println(Formatter.prettify(GOODBYE, STRING_DECORATION, STRING_DECORATION));
    }
}
