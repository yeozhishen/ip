package fido.datastructures;
/**
 * Represents a todo task in the task list
 */
public class Todo extends Task {
    private static final char TYPE = 'T';
    public Todo(String task) {
        super(task, TYPE);
    }
}
