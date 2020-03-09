package util;

import java.io.Reader;

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
}
