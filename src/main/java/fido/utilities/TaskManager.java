package fido.utilities;
import fido.datastructures.Task;
import java.util.LinkedList;
/*
 * Manages the tasks in the task list in memory
 */
public class TaskManager {
    private LinkedList<Task> taskList;
    private static final String TASK_DONE_STRING = "uwu  marked the task as done: ";
    private static final String TASK_NOT_DONE_STRING = ":( marked the task as not done yet: ";
    private static final String TASK_ADDED_STRING = "added: ";
    private static final String TASK_DELETED_STRING = "I have deleted: \n";
    private static final String LIST_MATCHING_TASKS_STRING = "Here are the matching tasks: \n";
    private static final String NO_TASKS_FOUND_STRING = "no tasks found";
    private int numTasks;

    public TaskManager() {
        this.numTasks = 0;
        this.taskList = new LinkedList<Task>();
    }
    public Task getTask(int stdoutTaskIndex) {
        return taskList.get(stdoutTaskIndex - 1);
    }
    /**
     * Returns a string representation of the current task list in memory
     * @return String representation of the task list
     */
    public String getTaskList(){
        if (numTasks == 0) {
            return NO_TASKS_FOUND_STRING;
        }
        StringBuilder listOfTasks = new StringBuilder();
        for (int i = 0; i < numTasks; i++) {
            Task currentTask = taskList.get(i);
            String lineOfTask = Integer.toString(i + 1) +
                    ". " + currentTask.toString() + System.lineSeparator();
            listOfTasks.append(lineOfTask);
        }
        return listOfTasks.toString();
    }
    /**
     * Returns a string representation of the tasks in memory that contains the keyword
     * @param String keyword the keyword to search for in the task list
     * @return String representation of the task list
     */
    public String findTasksUsingKeyword(String keyword) {
        StringBuilder listOfTasks = new StringBuilder();
        int counter = 1;
        for (Task task: taskList) {
            if (!task.getTaskDescription().contains(keyword)) {
                continue;
            }
            String lineOfTask = Integer.toString(counter) +
                    ". " + task.toString() + System.lineSeparator();
            listOfTasks.append(lineOfTask);
            counter++;
        }
        if (counter == 1) {
            return NO_TASKS_FOUND_STRING;
        }
        return LIST_MATCHING_TASKS_STRING + listOfTasks.toString();
    }
    /**
     * Marks a task as done in the task list in memory
     * @param int stdoutTaskIndex the index of the task to mark as done based on what the user sees
     * in the terminal, which is 1-indexed
     * @return String the string representation of the task that was marked as done
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String markTaskAsDone(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if (isIndexOutOfRange(stdoutTaskIndex)) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToMark = taskList.get(stdoutTaskIndex - 1);
        taskToMark.setDone();
        return TASK_DONE_STRING + taskToMark.toString();
    }
    /**
     * Marks a task as not done in the task list in memory
     * @param int stdoutTaskIndex the index of the task to mark as not done based on what the user sees
     * in the terminal, which is 1-indexed
     * @return String the string representation of the task that was marked as not done
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String unmarkTask(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if (isIndexOutOfRange(stdoutTaskIndex)) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToMark = taskList.get(stdoutTaskIndex - 1);
        taskToMark.setNotDone();
        return TASK_NOT_DONE_STRING + taskToMark.toString();
    }
    private boolean isIndexOutOfRange(int index){
        if (index <= 0 || index > numTasks){
            return true;
        }
        return false;
    }
    /**
     * Deletes a task from the task list in memory
     * @param int stdoutTaskIndex the index of the task to delete based on what the user sees
     * in the terminal, which is 1-indexed
     * @return String the string representation of the task that was deleted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String deleteTask(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if (isIndexOutOfRange(stdoutTaskIndex)) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToDelete = taskList.get(stdoutTaskIndex - 1);
        taskList.remove(stdoutTaskIndex - 1);
        numTasks--;
        return TASK_DELETED_STRING + taskToDelete.toString() + "\n" + getTasksInListString();
    }
    /**
     * Adds a task to the task list in memory
     * @param Task task the task to add to the task list
     * @return String the string representation of the task that was added 
     * to indicate that the task was added
     */
    public String addTask(Task task) {
        taskList.add(task);
        numTasks++;
        return TASK_ADDED_STRING + task.toString()
                + System.lineSeparator() + getTasksInListString();
    }
    private String getTasksInListString() {
        return "Now you have " + numTasks + " tasks in the list";
    }
}
