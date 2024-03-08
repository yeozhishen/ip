package fido.datastructures;
/**
 * Represents a task with a deadline in the task list
 */
public class Deadline extends Task {
    private static final char TYPE = 'D';
    protected String doBy;
    /**
     * Constructor for the deadline task
     * @param String task the description of the task
     * @param String doBy the time the task is due
     */
    public Deadline(String task, String doBy) {
        super(task, TYPE);
        this.doBy = doBy;
    }
    /**
     * Returns the time task is due
     * @return String the time task is due
     */
    public String getDoBy() {
        return doBy;
    }
    @Override
    public String toString() {
        return super.toString()
                + " (" + "by: " + doBy + ")";
    }
}
