package fido.utilities;
import fido.exceptions.FidoException;
import fido.enumerators.ErrorMessages;
import fido.enumerators.ParserRegex;
import java.util.Scanner;
import java.util.Arrays;
/**
 * Obtains and parses user input from the command line and checks if the inputs are valid
 */
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
    /**
     * Collects user input from the command line and splits it into the command (inputCommand)
     * and its arguments (inputCommandArguments)
     * @throws FidoException if there is no input
     */
    public void collectUserInput() throws FidoException {
        clearAllInputs();
        inputString = stdin.nextLine();
        if (inputString.isEmpty()) {
            throw new FidoException(ErrorMessages.MISSING_INPUT.string);
        }
        String[] individualWords = inputString.trim().split(" ");
        int last_word_index = individualWords.length;
        inputCommand = individualWords[FIRST_WORD_INDEX];
        if (last_word_index > 1) {
            inputCommandArguments = Arrays.copyOfRange(individualWords, FIRST_WORD_INDEX + 1, last_word_index);
        }
    }
    private void clearAllInputs() {
        inputCommandArguments = null;
        inputCommand = null;
        inputString = null;
    }
    public String getUserInputCommand() {
        return inputCommand;
    }
    /**
     * Checks if the user input is a valid todo
     * @return boolean true if the user input is a valid todo, false otherwise
     */
    public boolean isValidTodo() {
        ParserRegex[] keywordsToLookOutFor = {};
        if (inputCommandArguments == null || !onlyContainsKeywords(keywordsToLookOutFor)){
            return false;
        }
        return inputCommandArguments.length >= 1;
    }
    /**
     * Checks if the user input is a valid deadline
     * @return boolean true if the user input is a valid deadline, false otherwise
     */
    public boolean isValidDeadline() {
        ParserRegex[] keywordsToLookOutFor = {ParserRegex.BY};
        if (inputCommandArguments == null || !onlyContainsKeywords(keywordsToLookOutFor)){
            return false;
        }
        int byKeywordIndex = indexOfKeywordInCommandArguments(ParserRegex.BY.string);
        return byKeywordIndex != DOES_NOT_EXIST
                && !atEndsOfCommandArgumentList(byKeywordIndex);
    }
    /**
     * Checks if the user input is a valid event
     * @return boolean true if the user input is a valid event, false otherwise
     */
    public boolean isValidEvent() {
        ParserRegex[] keywordsToLookOutFor = {ParserRegex.TO, ParserRegex.FROM};
        if (inputCommandArguments == null || !onlyContainsKeywords(keywordsToLookOutFor)){
            return false;
        }
        int fromKeywordIndex = indexOfKeywordInCommandArguments(ParserRegex.FROM.string);
        int toKeywordIndex = indexOfKeywordInCommandArguments(ParserRegex.TO.string);
        return fromKeywordIndex != DOES_NOT_EXIST && toKeywordIndex != DOES_NOT_EXIST
                && !atEndsOfCommandArgumentList(fromKeywordIndex)
                && !atEndsOfCommandArgumentList(toKeywordIndex)
                && !consecutiveInArgumentList(fromKeywordIndex, toKeywordIndex);
    }
    /**
     * checks if the keywords in the keywordList are the only keywords in the inputCommandArguments
     * @param ParserRegex[] keywordList the list of keywords to check for
     * @return boolean true if the keywords in the keywordList are the 
     * only keywords in the inputCommandArguments, false otherwise
     */
    private boolean onlyContainsKeywords(ParserRegex[] keywordList) {
        for (String word: inputCommandArguments) {
            if (isKeyword(word) && !isWordInKeywordList(word, keywordList) ){
                return false;
            }
        }
        return true;
    }
    private boolean isKeyword(String word) {
        ParserRegex regexMapping = ParserRegex.getCommandEnumeration(word);
        return regexMapping != ParserRegex.REGEX_NOT_FOUND;
    }
    private boolean isWordInKeywordList(String word, ParserRegex[] keywordList) {
        ParserRegex regexMapping = ParserRegex.getCommandEnumeration(word);
        for (ParserRegex keyword: keywordList) {
            if (regexMapping == keyword) {
                return true;
            }
        }
        return false;
    }
    private boolean atEndsOfCommandArgumentList(int index) {
        return index == 0 || index == (inputCommandArguments.length - 1);
    }
    private boolean consecutiveInArgumentList(int index1, int index2) {
        return index1 == (index2 - 1) || index2 == (index1 - 1);
    }
    private int indexOfKeywordInCommandArguments(String keyword) {
        for (int i = 0; i < inputCommandArguments.length; i++) {
            if (inputCommandArguments[i].equals(keyword)) {
                return i;
            }
        }
        return DOES_NOT_EXIST;
    }
    /**
     * Gets the string after a keyword in the inputCommandArguments unitl the next keyword is found
     * @param ParserRegex keyword the keyword whose index we want to start obtaining the string from
     * @return String the string after the keyword until a next keyword is found
     */
    public String getStringAfterKeywordUntilNextKeyword(ParserRegex keyword) {
        int keywordIndex = indexOfKeywordInCommandArguments(keyword.string);
        StringBuilder string = new StringBuilder();
        for (int i = keywordIndex + 1; i < inputCommandArguments.length; i++){
            if (ParserRegex.isStringEnumeration(inputCommandArguments[i])) {
                break;
            }
            string.append(" ");
            string.append(inputCommandArguments[i]);
        }
        return string.toString().trim();
    }
    /**
     * Gets the task description of a task from the inputCommandArguments
     * @return String the description of the task
     */
    public String getTaskDescription() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < inputCommandArguments.length; i++){
            if (ParserRegex.isStringEnumeration(inputCommandArguments[i])) {
                break;
            }
            string.append(" ");
            string.append(inputCommandArguments[i]);
        }
        return string.toString().trim();
    }
    /**
     * Gets the keyword that we want to find from the inputCommandArguments
     * when using the find command
     * @return String the keyword that we want to search for
     * @throws FidoException if there are no keywords to search for
     */
    public String getFindKeyword() throws FidoException{
        if (!isValidFind()) {
            throw new FidoException(ErrorMessages.INVALID_FIND.string);
        }
        StringBuilder string = new StringBuilder();
        for (String word : inputCommandArguments) {
            string.append(" ");
            string.append(word);
        }
        return string.toString().trim();
    }
    private boolean isValidFind() {
        return inputCommandArguments != null && inputCommandArguments.length != 0;
    }
    /**
     * Gets the index of the task that we want to mark as done from the user's mark/unmark command
     * @return int the index of the task that we want to mark as done
     * @throws FidoException if the input is invalid, when there are no arguments or more than 1 argument
     * after the mark command or when the argument is not an integer
     */
    public int getTaskIndex() throws FidoException {
        int taskIndexForMarking;
        if (inputCommandArguments == null || inputCommandArguments.length > 1){
            throw new FidoException(ErrorMessages.INVALID_ARGUMENTS.string);
        }
        try {
            taskIndexForMarking = Integer.parseInt(inputCommandArguments[0]);
        } catch (Exception e){
            throw new FidoException(ErrorMessages.INVALID_INTEGER.string);
        }
        return taskIndexForMarking;
    }
}
