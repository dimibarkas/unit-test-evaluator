package com.test.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testIfFunctionThrowsException() {
        StringUtil su = new StringUtil();
        String testString = null;
        assertThrows(IllegalArgumentException.class, () -> {
            su.duplicateCharactersInString(testString);
        });
    }

    @Test
    public void testIfFunctionWorksCorrectly() {
        StringUtil su = new StringUtil();
        String testString = "abc";
        String resultString = su.duplicateCharactersInString(testString);
        assertEquals("aabbcc", resultString);
    }
}