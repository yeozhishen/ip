package fido.enumerators;
/**
 * Represents the commands that Fido can understand
 * with the string representation of the command
 */
public enum Commands {
    LIST("list"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    EXIT("bye"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    FIND("find"),
    INVALID_COMMAND("invalid command!");
    public final String string;
    private Commands(String string) {
        this.string = string;
    }
    public static Commands getCommandEnumeration(String command) {
        for (Commands enumCommand : values()) {
            if (enumCommand.string.equals(command)) {
                return enumCommand;
            }
        }
        return INVALID_COMMAND;
    }
}
