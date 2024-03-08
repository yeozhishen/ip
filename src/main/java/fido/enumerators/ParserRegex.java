package fido.enumerators;
/**
 * Represents the keywords separating the different task fields that Fido can understand
 * with the string representation of the keyword task fields separator
 */
public enum ParserRegex {
    BY("/by"),
    FROM("/from"),
    TO("/to"),
    REGEX_NOT_FOUND("invalid regex");
    public final String string;
    private ParserRegex(String string) {
        this.string = string;
    }
    /**
     * Returns the ParserRegex enumeration of the command
     * @param String command the command to get the enumeration of
     * @return ParserRegex the enumeration of the command, REGEX_NOT_FOUND if the command is not a valid enumeration
     */
    public static ParserRegex getCommandEnumeration (String command) {
        for (ParserRegex enumCommand : values()) {
            if (enumCommand.string.equals(command)) {
                return enumCommand;
            }
        }
        return REGEX_NOT_FOUND;
    }
    /**
     * Checks if the command is a valid enumeration
     * @param String command the command to check
     * @return boolean true if the command is a valid enumeration, false otherwise
     */
    public static boolean isStringEnumeration (String command) {
        return getCommandEnumeration(command) != REGEX_NOT_FOUND;
    }
}
