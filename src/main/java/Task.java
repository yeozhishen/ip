public class Task {
    protected String task;
    protected boolean isDone;

    public Task(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void printTask(){
        System.out.print("[" + getStatusIcon() + "] ");
        System.out.print(task);
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



