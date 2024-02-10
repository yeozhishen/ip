public abstract class Task {
    protected String task;
    protected boolean isDone;
    protected char type;

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
   public void setDone(){
        isDone = true;
   }
   public void setNotDone(){
        isDone = false;
   }
}



