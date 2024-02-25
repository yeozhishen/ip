package fido.utilities;

import fido.datastructures.Task;

import java.util.LinkedList;
public class TaskManager {
    private LinkedList<Task> taskList;
    private LinkedList<Task> lastCheckedList;
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
    public String markTaskAsDone(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if (isIndexOutOfRange(stdoutTaskIndex)) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToMark = taskList.get(stdoutTaskIndex - 1);
        taskToMark.setDone();
        return TASK_DONE_STRING + taskToMark.toString();
    }
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
    public String deleteTask(int stdoutTaskIndex) throws IndexOutOfBoundsException {
        if (isIndexOutOfRange(stdoutTaskIndex)) {
            throw new IndexOutOfBoundsException();
        }
        Task taskToDelete = taskList.get(stdoutTaskIndex - 1);
        taskList.remove(stdoutTaskIndex - 1);
        numTasks--;
        return TASK_DELETED_STRING + taskToDelete.toString() + "\n" + getTasksInListString();
    }
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
