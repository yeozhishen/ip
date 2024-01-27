import java.util.LinkedList;
public class TaskManager {
    private LinkedList<Task> taskList;
    private int numTasks;

    public TaskManager() {
        this.numTasks = 0;
        this.taskList = new LinkedList<Task>();
    }

    public void printTaskList() {
        printlnHorizontalLine();
        for(int i = 0; i < numTasks; i++) {
            Task currentTask = taskList.get(i);
            System.out.print(i+1 + ". ");
            currentTask.printTask();
            System.out.println();
        }
        printlnHorizontalLine();
    }
    private void printlnHorizontalLine() {
        String line = "--------------------------------------";
        System.out.println(line);
    }
    public void addTask(String task) {
        Task newTask = new Task(task);
        taskList.add(newTask);
        printlnHorizontalLine();
        System.out.println("added: " + task);
        printlnHorizontalLine();
        numTasks++;
    }
}
