package dao;

import util.MyUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LogDao {
    private File logDirectory;
    public static String newestDate;

    public LogDao(String logPath) {
        logDirectory = new File(logPath);
    }

    public List<File> getLogList() {
        List<File> logList = new ArrayList<>();
        String regex = "\\d{4}-\\d{2}-\\d{2}.log.txt";   //日志文件名所遵循的格式
        for (File file : logDirectory.listFiles()){
            if(file.getName().matches(regex)){
                logList.add(file);
            }
            newestDate = file.getName();
        }
        return logList;
    }

    public List<String> getRecords(File log) {
        BufferedReader reader = null;
        List<String> records = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(log), "gbk"));
            String dataRow;
            while ((dataRow = reader.readLine()) != null) {
                if (dataRow.startsWith("//")) { //忽略注释行
                    continue;
                }
                records.add(dataRow);
            }
            return records;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            MyUtil.close(reader);
        }
    }
}
