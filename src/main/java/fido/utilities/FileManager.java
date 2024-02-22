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
    File dataFile = new File(FILE_PATH);
    File directory = new File(DIRECTORY_PATH);
    public FileManager() {

    }
    public boolean fileExists() {
        return dataFile.exists();
    }
    public String ensureFileExists() throws FidoException {
        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            boolean fileDoesNotExist = dataFile.createNewFile();
            if (fileDoesNotExist) {
                FileWriter writer = new FileWriter(dataFile,true);
                writer.append("Tasklist");
                writer.flush();
                return "file successfully created";
            }
            return "existing file found";
        } catch (IOException e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
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
    public String readFile() throws FidoException {
        try {
            Scanner sc = new Scanner(dataFile);
            StringBuffer buffer = new StringBuffer();
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String rawString = buffer.toString();
            return handleIOErrors(rawString);
        } catch (Exception e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
    private String handleIOErrors(String rawString) throws FidoException {
        //double newline bug in file writing
        try {
            rawString = rawString.replaceAll(System.lineSeparator() + "+", System.lineSeparator());
            FileWriter writer = new FileWriter(dataFile);
            writer.append(rawString);
            writer.flush();
            return rawString;
        } catch (IOException e) {
            throw new FidoException(ErrorMessages.FILE_ERROR.string);
        }
    }
}
