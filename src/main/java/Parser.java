import java.util.Scanner;
import java.util.Arrays;
public class Parser {
    private Scanner stdin;
    private String inputString;
    private String inputCommand;
    private String[] inputCommandArguments;
    public Parser() {
        this.stdin = new Scanner(System.in);
        inputString = null;
        inputCommand = null;
        inputCommandArguments = null;
    }
    public void collectUserInput(){
        inputString = stdin.nextLine();
        String[] individualWords = inputString.split(" ");
        inputCommand = individualWords[0];
        if(individualWords.length > 1) {
            inputCommandArguments = Arrays.copyOfRange(individualWords, 1, individualWords.length);
        }
    }
    public String getUserInputString() {
        return inputString;
    }
    public String getUserInputCommand() {
        return inputCommand;
    }
    public int getTaskIndexForMarking() throws IllegalArgumentException {
        int taskIndexForMarking = -1;
        if(inputCommandArguments == null | inputCommandArguments.length > 1){
            throw new IllegalArgumentException("too many or too little arguments");
        }
        try {
            taskIndexForMarking = Integer.parseInt(inputCommandArguments[0]);
        } catch (Exception e){
            throw new IllegalArgumentException("not a valid integer");
        }
        return taskIndexForMarking;
    }
}
