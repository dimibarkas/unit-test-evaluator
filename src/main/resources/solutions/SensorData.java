package com.test.app;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;

public class SensorDataTest {

    @Test
    public void testSensorData() {
        SensorData testObject = new SensorData();
        assertEquals(0, testObject.getSize());
    }

    @Test
    public void testSensorDataDoubleArray() {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertEquals(5, testObject.getSize());
    }

    @Test
    public void testAdd() {
        SensorData testObject = new SensorData();
        assertEquals(0, testObject.getSize());
        testObject.add(4.0);
        assertEquals(1, testObject.getSize());
        testObject.add(8.0);
        assertEquals(2, testObject.getSize());
    }

    @Test
    public void testGetSize() {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertEquals(5, testObject.getSize());
    }

    @Test
    public void testClear() {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertEquals(5, testObject.getSize());
        testObject.clear();
        assertEquals(0, testObject.getSize());
    }

    @Test
    public void testGet() {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertTrue(5.0 ==  testObject.get(4));
    }

    @Test
    public void testGetMinimum() throws EmptySensorArrayException {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertTrue(1.0 ==  testObject.getMinimum());
        testObject.clear();
        assertThrows(EmptySensorArrayException.class, () -> testObject.getMinimum());
    }


    @Test
    public void testGetMaximum() throws EmptySensorArrayException {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertTrue(5.0 ==  testObject.getMaximum());
        testObject.clear();
        assertThrows(EmptySensorArrayException.class, () -> testObject.getMaximum());
    }

    @Test
    public void testGetAverage() throws EmptySensorArrayException {
        double array[] = {1.0,2.0,3.0,4.0,5.0};
        SensorData testObject = new SensorData(array);
        assertTrue(3.0 == testObject.getAverage());
        testObject.clear();
        assertThrows(EmptySensorArrayException.class, () -> testObject.getAverage());
    }
}