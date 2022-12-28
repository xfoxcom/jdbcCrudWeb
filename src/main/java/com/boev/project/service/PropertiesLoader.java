package com.boev.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties() {

        Properties configuration = new Properties();

        try (InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            configuration.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return configuration;
    }

}
