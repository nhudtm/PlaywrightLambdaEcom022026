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
        } catch (IOException ignored) {
        }
    }

    public static String getProp(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String envRawKey = System.getenv(key);
        if (envRawKey != null && !envRawKey.isBlank()) {
            return envRawKey;
        }

        String normalizedEnvKey = normalizeKey(key);
        String envNormalizedKey = System.getenv(normalizedEnvKey);
        if (envNormalizedKey != null && !envNormalizedKey.isBlank()) {
            return envNormalizedKey;
        }

        return PROPS.getProperty(key);
    }

    private static String normalizeKey(String key) {
        return key.toUpperCase().replaceAll("[^A-Z0-9]", "_");
    }

}
