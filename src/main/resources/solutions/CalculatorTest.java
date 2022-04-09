package com.test.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    Calculator cal = new Calculator();

    @Test
    public void addTest() {
        int result = cal.add(5,5);
        assertEquals(10, result);
    }

    @Test
    public void subTest() {
        int result = cal.subtract(16,5);
        assertEquals(11, result);
    }

    @Test
    public void multiplyTest() {
        int result = cal.multiply(5,5);
        assertEquals(25, result);
    }

    @Test
    public void divideTest() {
        int result = cal.divide(35,7);
        assertEquals(5, result);
    }

    @Test
    public void checkForException() {
        assertThrows(IllegalArgumentException.class, () -> {
            cal.divide(5,0);
        });
    }

}