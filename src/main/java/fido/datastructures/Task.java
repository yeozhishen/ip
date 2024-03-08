package fido.datastructures;
/**
 * Represents a task in the task list
 */
public abstract class Task {
    protected String task;
    protected boolean isDone;
    protected char type;
    public String getTaskDescription() {
        return task;
    }
    public boolean isDone() {
        return isDone;
    }
    /**
     * Returns the type of the task
     * @return char the type of the task
     */
    public char getType() {
        return type;
    }
    /**
     * Constructor for the task, sets the task description and type
     * and initializes the task as not done
     * @param String task the description of the task
     * @param char type the type of the task
     */
    public Task(String task, char type) {
        this.task = task;
        this.type = type;
        setNotDone();
    }
    public String toString() {
        return "[" + type + "]"
                + "[" + getStatusIcon() + "] "
                + task;
    }
    private String getStatusIcon() {
        return (isDone ? "X" : " ");
    }
    /**
     * Marks the task as done
     */
    public void setDone(){
        isDone = true;
    }
    /**
     * Marks the task as not done
     */
    public void setNotDone(){
        isDone = false;
    }
}



