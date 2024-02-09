public enum ParserRegex {
    BY("/by"),
    FROM("/from"),
    TO("/to"),
    REGEX_NOT_FOUND("invalid regex");
    public final String string;
    private ParserRegex(String string) {
        this.string = string;
    }
    public static ParserRegex getCommandEnumeration (String command) {
        for (ParserRegex enumCommand : values()) {
            if (enumCommand.string.equals(command)) {
                return enumCommand;
            }
        }
        return REGEX_NOT_FOUND;
    }
    public static boolean isStringEnumeration (String command) {
        if(getCommandEnumeration(command) == REGEX_NOT_FOUND) {
            return false;
        }
        return true;
    }
}
