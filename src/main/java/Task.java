public class Task {
    protected String task;
    protected boolean isDone;

    public Task(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }
   public String toString() {
        return "[" + getStatusIcon() + "] " + task;
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



