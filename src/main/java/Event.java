public class Event extends Task {
    protected String startTime;
    protected String endTime;
    public Event(String task, String startTime, String endTime) {
        super(task, 'E');
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return super.toString()
                + " (" + "from: " + startTime
                + ", to: " + endTime + ")";
    }
}
