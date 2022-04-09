package com.test.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class InsertionSortTest {

    @Test
    public void test() {
        int arr[] = {2,4,1,6,7,5};
        int exp[] = {1,2,4,5,6,7};
        InsertionSort is = new InsertionSort();
        is.sort(arr);
        assertArrayEquals(exp, arr);
    }
}