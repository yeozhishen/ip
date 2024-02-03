public enum PARSER_REGEX {
    BY("/by"),
    FROM("/from"),
    TO("/to"),
    REGEX_NOT_FOUND("invalid regex");
    public final String string;
    private PARSER_REGEX(String string) {
        this.string = string;
    }
    public static PARSER_REGEX getCommandEnumeration (String command) {
        for (PARSER_REGEX enumCommand : values()) {
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
