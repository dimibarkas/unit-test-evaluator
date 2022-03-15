package com.test.app;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an array of sensor data.
 */
public class SensorData {
    /**
     * Sensor values.
     */
    private List<Double> values;

    /**
     * Default constructor.
     */
    public SensorData() {
        this.values = new ArrayList<>();
    }

    /**
     * Constructor setting initial values.
     * @param values values
     */
    public SensorData(double... values) {
        this.values = new ArrayList<>();
        for(double v : values) {
            this.values.add(v);
        }
    }

    /**
     * Adds sensor value.
     * @param value value
     */
    public void add(Double value) {
        this.values.add(value);
    }

    /**
     * Returns number of sensor values.
     * @return number of values
     */
    public int getSize() {
        return this.values.size();
    }

    /**
     * Deletes all sensor values.
     */
    public void clear() {
        this.values.clear();
    }

    /**
     * Returns value at index.
     * @param index index
     * @return value at index
     * @throws IndexOutOfBoundsException if index is out of bounds of array
     */
    public Double get(int index) {
        return this.values.get(index);
    }

    /**
     * Returns the minimum value of all values.
     * @return minimum value
     * @throws EmptySensorArrayException if no sensor values exist
     */
    public double getMinimum() throws EmptySensorArrayException {
        if(this.values.size() == 0) {
            throw new EmptySensorArrayException("Cannot determine minimum of sensor values because no values exist.");
        }
        return this.values.stream().mapToDouble(x -> x).min().orElseThrow();
    }

    /**
     * Returns the maximum value of all values.
     * @return maximum value
     * @throws EmptySensorArrayException if no sensor values exist
     */
    public double getMaximum() throws EmptySensorArrayException {
        if(this.values.size() == 0) {
            throw new EmptySensorArrayException("Cannot determine maximum of sensor values because no values exist.");
        }
        return this.values.stream().mapToDouble(x -> x).max().orElseThrow();
    }

    /**
     * Returns the average value of all values.
     * @return average value
     * @throws EmptySensorArrayException if no sensor values exist
     */
    public double getAverage() throws EmptySensorArrayException {
        if(this.values.size() == 0) {
            throw new EmptySensorArrayException("Cannot determine average of sensor values because no values exist.");
        }
        return this.values.stream().mapToDouble(x -> x).average().orElseThrow();
    }
}
