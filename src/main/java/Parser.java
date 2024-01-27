import java.util.Scanner;
public class Parser {
    private Scanner stdin;
    private String inputString;
    private String inputCommand;
    public Parser() {
        this.stdin = new Scanner(System.in);
        inputString = null;
        inputCommand = null;
    }
    public void collectUserInput(){
        inputString = stdin.nextLine();
        inputCommand = inputString.split(" ")[0];
    }
    public String getUserInputString() {
        return inputString;
    }
    public String getUserInputCommand() {
        return inputCommand;
    }
}
