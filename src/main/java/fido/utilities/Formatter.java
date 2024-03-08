package fido.utilities;
import fido.datastructures.Deadline;
import fido.datastructures.Event;
import fido.datastructures.Task;
import fido.datastructures.Todo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Formats the output of Fido and 
 * converts tasks to and from file format
 */
public class Formatter {
    private static final char DEADLINE = 'D';
    private static final char TODO = 'T';
    private static final char EVENT = 'E';
    private static final int TYPE = 0;
    private static final int IS_DONE = 1;
    private static final int DESCRIPTION = 2;
    private static final int BY_OR_START = 3;
    private static final int END = 4;
    private static final String FILE_DELIMITER = "`";
    private static final String TASK_DELIMITER = System.lineSeparator();
    /**
     * Prettifies a message with a top and bottom line string decoration
     * @param message the message to be prettified
     * @param String topLineDecoration the string decoration to be added to the top of the message
     * @param String bottomLineDecoration the string decoration to be added to the bottom of the message
     * @return String the prettified message
     */
    public static String prettify(String message, String topLineDecoration, String bottomLineDecoration) {
        return topLineDecoration + System.lineSeparator()
                + message + System.lineSeparator() + bottomLineDecoration;
    }
    /**
     * Converts a task to a string in file format to be handled by the file manager
     * @param Task task the task to be converted
     * @return String the task in file format
     */
    public static String convertToFileFormat(Task task) {
        String output = task.getType() + FILE_DELIMITER + task.isDone() + FILE_DELIMITER + task.getTaskDescription();
        switch (task.getType()) {
        case TODO:
            break;
        case DEADLINE:
            Deadline deadline = (Deadline)task;
            output += FILE_DELIMITER + deadline.getDoBy();
            break;
        case EVENT:
            Event event = (Event)task;
            output += FILE_DELIMITER + event.getStartTime() + FILE_DELIMITER + event.getEndTime();
        }
        return output;
    }
    /**
     * converts a single line of task in file format to a task object
     * @param String task the task in file format
     * @return Task the task object
     */
    private static Task convertFromFileFormat(String task) {
        String[] taskFields = task.split(FILE_DELIMITER);
        Task actualTask = createTaskFromFileFormat(taskFields);
        if (Boolean.parseBoolean(taskFields[IS_DONE])) {
            actualTask.setDone();
        }
        return actualTask;
    }
    private static Task createTaskFromFileFormat(String[] taskFields) {
        char taskType = taskFields[TYPE].charAt(0);
        switch (taskType) {
        case TODO:
            return new Todo(taskFields[DESCRIPTION]);
        case DEADLINE:
            return new Deadline(taskFields[DESCRIPTION], taskFields[BY_OR_START]);
            //last case is an event
        default:
            return new Event(taskFields[DESCRIPTION], taskFields[BY_OR_START], taskFields[END]);
        }
    }
    /**
     * Converts a list of tasks in file format to a List of task objects
     * @param String taskListString the list of tasks in file format
     * @return List<Task> the list of task objects
     */
    public static List<Task> convertToTaskListFromFileFormat(String taskListString) {
        List<String> stringTaskList = new ArrayList<String>(Arrays.asList(taskListString.split(TASK_DELIMITER)));
        //first item in the list is the file header
        stringTaskList.remove(0);
        List<Task> taskList = new ArrayList<Task>();
        for (String taskString: stringTaskList) {
            Task convertedTask = convertFromFileFormat(taskString);
            taskList.add(convertedTask);
        }
        return taskList;
    }
}
