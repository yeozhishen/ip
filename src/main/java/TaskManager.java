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
    public void markTaskAsDone(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if( stdoutTaskIndex <= 0 || stdoutTaskIndex > numTasks) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToMark = taskList.get(stdoutTaskIndex - 1);
        taskToMark.setDone();
        printlnHorizontalLine();
        System.out.println("uwu  marked the task as done: ");
        taskToMark.printTask();
        System.out.println();
        printlnHorizontalLine();
    }
    public void unmarkTask(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if( stdoutTaskIndex <= 0 || stdoutTaskIndex > numTasks) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToMark = taskList.get(stdoutTaskIndex - 1);
        taskToMark.setNotDone();
        printlnHorizontalLine();
        System.out.println(":( marked the task as not done yet: ");
        taskToMark.printTask();
        System.out.println();
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
