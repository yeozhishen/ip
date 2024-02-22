package fido.utilities;

import fido.exceptions.FidoException;
import fido.enumerators.ErrorMessages;
import fido.enumerators.ParserRegex;

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
    public void collectUserInput() throws FidoException {
        clearAllInputs();
        inputString = stdin.nextLine();
        String[] individualWords = inputString.trim().split(" ");
        int last_word_index = individualWords.length;
        if (individualWords.length == 0) {
            throw new FidoException(ErrorMessages.MISSING_INPUT.string);
        }
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
    public String getUserInputString() {
        return inputString;
    }
    public String getUserInputCommand() {
        return inputCommand;
    }
    public boolean isValidTodo() {
        ParserRegex[] keywordsToLookOutFor = {};
        if (inputCommandArguments == null || !onlyContainsKeywords(keywordsToLookOutFor)){
            return false;
        }
        return inputCommandArguments.length >= 1;
    }
    public boolean isValidDeadline() {
        ParserRegex[] keywordsToLookOutFor = {ParserRegex.BY};
        if (inputCommandArguments == null || !onlyContainsKeywords(keywordsToLookOutFor)){
            return false;
        }
        int byKeywordIndex = indexOfKeywordInCommandArguments(ParserRegex.BY.string);
        return byKeywordIndex != DOES_NOT_EXIST
                && !atEndsOfCommandArgumentList(byKeywordIndex);
    }
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
        return string.toString();
    }
    public String getTaskDescription() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < inputCommandArguments.length; i++){
            if (ParserRegex.isStringEnumeration(inputCommandArguments[i])) {
                break;
            }
            string.append(" ");
            string.append(inputCommandArguments[i]);
        }
        return string.toString();
    }
    public int getTaskIndex() throws FidoException {
        int taskIndexForMarking;
        if (inputCommandArguments == null || inputCommandArguments.length > 1){
            throw new FidoException("too many or too little arguments");
        }
        try {
            taskIndexForMarking = Integer.parseInt(inputCommandArguments[0]);
        } catch (Exception e){
            throw new FidoException("not a valid integer");
        }
        return taskIndexForMarking;
    }
}
