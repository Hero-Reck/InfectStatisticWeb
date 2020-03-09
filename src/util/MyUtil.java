package util;

import java.io.IOException;
import java.io.InputStream;
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
}
