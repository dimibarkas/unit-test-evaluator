package com.test.app;

/**
 * Die Klasse stellt Funktionen für einfache Rechenoperationen zur Verfügung.
 */
public class Calculator {

    /**
     * Takes two integer values and adds them.
     * @param a the first integer value
     * @param b the second integer value
     * @return the addition of the two values.
     */
    public int add(int a, int b) {
        return a+b;
    };

    /**
     * Takes two integer values and subtract them.
     * @param a the first integer value
     * @param b the second integer value
     * @return the subtraction of the two values.
     */
    public int subtract(int a, int b) {
        return a-b;
    }

    /**
     * Takes two integer values and multiplies them.
     * @param a the first integer value
     * @param b the second integer value
     * @return the multiplication of the two values.
     */
    public int multiply(int a, int b) {
        return a*b;
    }

    /**
     * Takes two integer values and divides them.
     * @param a the first integer value
     * @param b the second integer value
     * @return the addition of the two values.
     */
    public int divide(int a, int b) {
        if(b == 0) {
            throw new IllegalArgumentException("division by zero is not supported");
        }
        return a / b;
    }

}
