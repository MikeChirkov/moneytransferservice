package ru.netology.moneytransferservice.utils;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void write(String message) {
        String msg = String.format("[%s] %s \n", SDF.format(new Date()), message);
        System.out.print(msg);
        writeToFile(msg);
    }

    private void writeToFile(String message) {
        try (FileWriter fw = new FileWriter("operations.log", true)) {
            fw.append(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
