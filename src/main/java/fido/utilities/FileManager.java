package fido.utilities;
import fido.exceptions.FidoException;
import fido.datastructures.Task;
import fido.enumerators.ErrorMessages;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
public class FileManager {
    private static final String FILE_PATH = "./data/tasklist.txt";
    private static final String DIRECTORY_PATH = "./data";
    private static final String FILE_CREATED_STRING = "new data file successfully created";
    private static final String EXISTING_FILE_FOUND_STRING = "existing data file found";
    private static final String FILE_RECREATED_SUCCESSFULLY_STRING = "file recreation success";
    private static final String FILE_TITLE = "Tasklist";
    File dataFile = new File(FILE_PATH);
    File directory = new File(DIRECTORY_PATH);
    /**
     * Ensures that the file exists, if it does not, it creates a new file
     * @return String message indicating the status of the file
     * @throws FidoException if there is an error creating the file
     */
    public String ensureFileExists() throws FidoException {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            boolean fileDoesNotExist = dataFile.createNewFile();
            if (fileDoesNotExist) {
                FileWriter writer = new FileWriter(dataFile,true);
                writer.append(FILE_TITLE);
                writer.flush();
                return FILE_CREATED_STRING;
            }
            return EXISTING_FILE_FOUND_STRING;
        } catch (IOException e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
    /**
     * Overwrites a line in the file with a new line
     * @param String oldText the old line of text to be replaced
     * @param String newText the new line of text to replace the old line
     * @throws FidoException if there is an error overwriting the line in the file
     */
    public void overwriteLine(String oldText, String newText) throws FidoException {
        try {
            Scanner sc = new Scanner(dataFile);
            StringBuffer buffer = new StringBuffer();
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String fileContent = buffer.toString();
            sc.close();
            fileContent = fileContent.replaceAll(oldText, newText);
            FileWriter writer = new FileWriter(dataFile);
            writer.write(fileContent);
            writer.flush();
        } catch (Exception e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
    /**
     * Saves a new task to the file
     * @param Task task the task to be saved
     * @throws FidoException if there is an error saving the task to the file
     */
    public void save(Task task) throws FidoException {
        try {
            String taskInFileFormat = Formatter.convertToFileFormat(task);
            FileWriter writer = new FileWriter(dataFile,true);
            writer.append(System.lineSeparator() + taskInFileFormat);
            writer.flush();
        } catch (Exception e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
    /**
     * Reads the file and returns the contents of the file with the corresponding file newlines
     * @return String the contents of the file
     * @throws FidoException if there is an error reading the file
     */
    public String readFile() throws FidoException {
        try {
            Scanner sc = new Scanner(dataFile);
            StringBuffer buffer = new StringBuffer();
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String rawString = buffer.toString();
            return handleFileFormatErrors(rawString);
        } catch (Exception e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
    private String handleFileFormatErrors(String rawString) throws FidoException {
        //This fixes the presence of more than 1 newline bug in between tasks in the file
        try {
            rawString = rawString.replaceAll(System.lineSeparator() + System.lineSeparator(), System.lineSeparator());
            FileWriter writer = new FileWriter(dataFile);
            writer.append(rawString);
            writer.flush();
            return rawString;
        } catch (IOException e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
    /**
     * recreates the data file
     * @return String message indicating the status of the file
     * @throws FidoException if there is an error recreating the file
     */
    public String recreateFile() throws FidoException {
        File fileToDelete = new File(FILE_PATH);
        fileToDelete.delete();
        ensureFileExists();
        return FILE_RECREATED_SUCCESSFULLY_STRING;
    }
}
