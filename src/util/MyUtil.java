package util;

import dao.LogDao;

import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    public static void close(Reader reader) {
        try {
            if (reader !=null) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int parseData(String dataNumber)  {
        try{
            return Integer.parseInt(dataNumber.substring(0,dataNumber.length() - 1));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static String getNewestDate() {
        String newestDate = LogDao.newestDate.substring(0,LogDao.newestDate.indexOf("."));
        return  newestDate;
    }
}
