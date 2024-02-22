package fido.enumerators;

public enum ErrorMessages {
    INVALID_DEADLINE("Invalid deadline. Input deadline in the form of: deadline [task] /by [time]"),
    INVALID_EVENT("Invalid event. Input event in the form of: event [task] /from [start_time] /to [end_time]"),
    INVALID_MARK_COMMAND("enter only 1 integer argument within range after the mark/unmark command"),
    INVALID_TODO("Invalid todo. Input todo in the form of: todo [task]"),

    MISSING_INPUT("No input detected, enter an input command"),
    FILE_ERROR("error regarding file IO"),
    INDEX_OUT_OF_BOUNDS("Index given is out of bounds");
    public final String string;
    private ErrorMessages(String string) {
        this.string = string;
    }
}
