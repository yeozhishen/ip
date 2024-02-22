package fido.datastructures;

public class Deadline extends Task {
    protected String doBy;
    public Deadline(String task, String doBy) {
        super(task, 'D');
        this.doBy = doBy;
    }
    public String getDoBy() {
        return doBy;
    }
    @Override
    public String toString() {
        return super.toString()
                + " (" + "by:" + doBy + ")";
    }
}
