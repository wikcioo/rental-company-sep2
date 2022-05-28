package application.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final ConsoleLogger instance = new ConsoleLogger();

    private ConsoleLogger() {
    }

    public static ConsoleLogger getInstance() {
        return instance;
    }

    private void logPrefix() {
        System.out.print(
                "[" + Thread.currentThread().getStackTrace()[3].getFileName() + ":" +
                        Thread.currentThread().getStackTrace()[3].getLineNumber() +
                        "] " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " "
        );
    }

    @Override
    public synchronized void info(String message) {
        logPrefix();
        System.out.println(ANSI_WHITE + "[INFO]: " + message + ANSI_RESET);
    }

    @Override
    public synchronized void warn(String message) {
        logPrefix();
        System.out.println(ANSI_YELLOW + "[WARNING]: " + message + ANSI_RESET);
    }

    @Override
    public synchronized void error(String message) {
        logPrefix();
        System.out.println(ANSI_RED + "[ERROR]: " + message + ANSI_RESET);
    }

    @Override
    public synchronized void success(String message) {
        logPrefix();
        System.out.println(ANSI_GREEN + "[SUCCESS]: " + message + ANSI_RESET);
    }
}
