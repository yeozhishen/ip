public enum COMMANDS {
    LIST("list"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    EXIT("bye"),
    MARK("mark"),
    UNMARK("unmark"),
    INVALID_COMMAND("invalid command!"),
    INVALID_MARK_COMMAND("enter only 1 integer argument within range after the mark/unmark command");

    public final String string;
    private COMMANDS(String string) {
        this.string = string;
    }
    public static COMMANDS getCommandEnumeration (String command) {
        for (COMMANDS enumCommand : values()) {
            if (enumCommand.string.equals(command)) {
                return enumCommand;
            }
        }
        return INVALID_COMMAND;
    }
}
