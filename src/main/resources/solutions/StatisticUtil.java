package com.test.app;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class StatisticUtilTest {

    @Test
    public void getAverageAgeStandard() throws LineHasWrongSyntaxException, IOException {
        String sampleString = "Bob;Builder;15\nAlice;Builder;21\nCharles;Drawing;24";
        InputStream stream = new ByteArrayInputStream(sampleString.getBytes());
        StatisticUtil su = new StatisticUtil();
        assertTrue(20.0 == su.getAverageAge(stream));
    }

    @Test
    public void getAverageAgeException() {
        String sampleString = "Bob;Builder\nAlice;Builder;21\nCharles;Drawing;24";
        InputStream stream = new ByteArrayInputStream(sampleString.getBytes());
        LineHasWrongSyntaxException thrown = assertThrows(LineHasWrongSyntaxException.class, () -> {
            StatisticUtil su = new StatisticUtil();
            su.getAverageAge(stream);
        });
        assertEquals(0, thrown.getLineNumber());
    }

    @Test
    public void getAverageAgeException2() {
        String sampleString = "Bob;Builder;a\nAlice;Builder;21\nCharles;Drawing;24";
        InputStream stream = new ByteArrayInputStream(sampleString.getBytes());
        LineHasWrongSyntaxException thrown = assertThrows(LineHasWrongSyntaxException.class, () -> {
            StatisticUtil su = new StatisticUtil();
            su.getAverageAge(stream);
        });
        assertEquals(0, thrown.getLineNumber());
    }
}