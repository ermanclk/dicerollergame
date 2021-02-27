package org.familygame.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    Properties appProps;

    public ConfigurationReader() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";
        appProps = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(appConfigPath)) {
            appProps.load(fileInputStream);
        }
    }

    public String readConfig(String propName) {
        return appProps.getProperty(propName);
    }

}
