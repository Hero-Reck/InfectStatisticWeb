package dao;

import java.io.File;

public class LogDao {
    private String logPath;

    public LogDao(String logPath) {
        this.logPath = logPath;
    }

    public String getLogPath() {
        return logPath;
    }

}
