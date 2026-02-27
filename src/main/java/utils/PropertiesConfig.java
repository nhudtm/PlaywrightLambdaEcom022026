package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig {
    private static final Properties PROPS = new Properties();

    //Load file 1 lần duy nhất khi class được nạp vào JVM - thread-safe
    static
    {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/config/config.properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            PROPS.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file cấu hình", e);
        }
    }

    public static String getProp(String key) {
        return PROPS.getProperty(key);
    }

}
