import java.util.Scanner;
import java.util.Arrays;
public class Parser {
    private static final int FIRST_WORD_INDEX = 0;
    private static final int DOES_NOT_EXIST = -1;
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
    public void collectUserInput() throws IllegalArgumentException {
        inputString = stdin.nextLine();
        String[] individualWords = inputString.trim().split(" ");
        int last_word_index = individualWords.length;
        if(individualWords.length == 0) {
            throw new IllegalArgumentException("array is empty!");
        }
        inputCommand = individualWords[FIRST_WORD_INDEX];
        if(last_word_index > 1) {
            inputCommandArguments = Arrays.copyOfRange(individualWords, FIRST_WORD_INDEX + 1, last_word_index);
        }
    }
    public String getUserInputString() {
        return inputString;
    }
    public String getUserInputCommand() {
        return inputCommand;
    }
    public boolean isValidTodo() {
        return inputCommandArguments.length >= 1;
    }
    public boolean isValidDeadline() {
        int byKeywordIndex = indexOfKeywordInCommandArguments(PARSER_REGEX.BY.string);
        return byKeywordIndex != -1 && !atEndsOfCommandArgumentList(byKeywordIndex);
    }
    public boolean isValidEvent() {
        int fromKeywordIndex = indexOfKeywordInCommandArguments(PARSER_REGEX.FROM.string);
        int toKeywordIndex = indexOfKeywordInCommandArguments(PARSER_REGEX.TO.string);
        return fromKeywordIndex != DOES_NOT_EXIST && toKeywordIndex != DOES_NOT_EXIST
                && !atEndsOfCommandArgumentList(fromKeywordIndex)
                && !atEndsOfCommandArgumentList(toKeywordIndex)
                && !consecutiveInArgumentList(fromKeywordIndex, toKeywordIndex);
    }
    private boolean atEndsOfCommandArgumentList(int index) {
        return index == 0 || index == (inputCommandArguments.length - 1);
    }
    private boolean consecutiveInArgumentList(int index1, int index2) {
        return index1 == (index2 - 1) || index2 == (index1 - 1);
    }
    private int indexOfKeywordInCommandArguments(String keyword) {
        for(int i = 0; i < inputCommandArguments.length; i++) {
            if(inputCommandArguments[i].equals(keyword)) {
                return i;
            }
        }
        return DOES_NOT_EXIST;
    }
    public String getStringAfterKeywordUntilNextKeyword(PARSER_REGEX keyword) {
        int keywordIndex = indexOfKeywordInCommandArguments(keyword.string);
        StringBuilder string = new StringBuilder();
        for (int i = keywordIndex + 1; i < inputCommandArguments.length; i++){
            if(PARSER_REGEX.isStringEnumeration(inputCommandArguments[i])) {
                break;
            }
            string.append(" ");
            string.append(inputCommandArguments[i]);
        }
        return string.toString();
    }
    public String getTaskDescription() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < inputCommandArguments.length; i++){
            if(PARSER_REGEX.isStringEnumeration(inputCommandArguments[i])) {
                break;
            }
            string.append(" ");
            string.append(inputCommandArguments[i]);
        }
        return string.toString();
    }
    public int getTaskIndexForMarking() throws IllegalArgumentException {
        int taskIndexForMarking;
        if(inputCommandArguments == null || inputCommandArguments.length > 1){
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
