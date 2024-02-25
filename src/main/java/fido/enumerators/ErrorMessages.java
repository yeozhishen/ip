package fido.enumerators;

public enum ErrorMessages {
    INVALID_DEADLINE("Invalid deadline. Input deadline in the form of: deadline [task] /by [time]"),
    INVALID_EVENT("Invalid event. Input event in the form of: event [task] /from [start_time] /to [end_time]"),
    INVALID_TODO("Invalid todo. Input todo in the form of: todo [task]"),
    INVALID_ARGUMENTS("too many or too little arguments"),
    INVALID_INTEGER("not a valid integer"),
    INVALID_FIND("Invalid find command. Too little arguments, enter a keyword/s to find"),
    MISSING_INPUT("No input detected, enter an input command"),
    FILE_ERROR("error regarding file IO"),
    INDEX_OUT_OF_BOUNDS("Index given is out of bounds");
    public final String string;
    private ErrorMessages(String string) {
        this.string = string;
    }
}
