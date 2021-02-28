package org.familygame.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    Properties appProps;
    private static final String PROPERTIES_FILE="application.properties";
    private static final String EMPTY_STRING="";

    public ConfigurationReader() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource(EMPTY_STRING).getPath();
        String appConfigPath = rootPath + PROPERTIES_FILE;
        appProps = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(appConfigPath)) {
            appProps.load(fileInputStream);
        }
    }

    public String readConfig(String propName) {
        return appProps.getProperty(propName);
    }

}
