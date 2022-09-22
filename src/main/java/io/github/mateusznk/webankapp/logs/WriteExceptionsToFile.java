package io.github.mateusznk.webankapp.logs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteExceptionsToFile {

    public void typicalErrorLog(String className, Exception exception) {
        String error = "Caused by: " + exception.getClass().getCanonicalName() +
                " at " + className + " in line: " + exception.getStackTrace()[0].getLineNumber();

        appendErrorLogToFile(error);
    }

    public void unusualErrorLog(int lineNumber, String className) {
        String error = "Exception at " + className + " in line: " + (--lineNumber);
        appendErrorLogToFile(error);
    }

    private void appendErrorLogToFile(String error) {
        final String pathToFile =
                "/home/mateusz/IdeaProjects/WeBankApp/src/main/java/io/github/mateusznk/webankapp/logs/log.txt";
        LocalDateTime actualDateAndTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = actualDateAndTime.format(formatter);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathToFile, true))) {
            bufferedWriter.append("[").append(formattedDateTime).append("] ");
            bufferedWriter.append(error);
            bufferedWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
