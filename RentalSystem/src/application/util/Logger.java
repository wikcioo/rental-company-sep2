package application.util;

public interface Logger {
    void info(String message);
    void warn(String message);
    void error(String message);
    void success(String message);
}
