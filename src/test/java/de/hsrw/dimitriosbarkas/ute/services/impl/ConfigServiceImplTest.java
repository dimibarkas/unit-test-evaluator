package de.hsrw.dimitriosbarkas.ute.services.impl;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class ConfigServiceImplTest {

    @Test
    public void loadResourceByUrlTest() throws IOException {
        ConfigServiceImpl configService = new ConfigServiceImpl();
        String path = "BubbleSort.java";
        URL url = configService.getClass().getClassLoader().getResource(path);
        configService.loadResourceByUrl(url, path);
    }
}