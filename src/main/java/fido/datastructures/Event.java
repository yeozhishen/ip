package fido.datastructures;
/**
 * Represents a task with a start and end time in the task list
 */
public class Event extends Task {
    private static final char TYPE = 'E';
    protected String startTime;
    protected String endTime;
    /**
     * Constructor for the event task
     * @param String task the description of the task
     * @param String startTime the start time of the task
     * @param String endTime the end time of the task
     */
    public Event(String task, String startTime, String endTime) {
        super(task, TYPE);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    /**
     * Returns the end time of the task
     * @return String the end time of the task
     */
    public String getEndTime() {
        return endTime;
    }
    /**
     * Returns the start time of the task
     * @return String the start time of the task
     */
    public String getStartTime() {
        return startTime;
    }
    @Override
    public String toString() {
        return super.toString()
                + " (" + "from: " + startTime
                + ", to: " + endTime + ")";
    }
}
