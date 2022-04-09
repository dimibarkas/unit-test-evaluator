package com.test.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleSortTest {

    @Test
    public void test() {
        int arr[] = {4,21,38,1,2};
        int exp[] = {1,2,4,21,38};
        BubbleSort bs = new BubbleSort(arr);
        assertArrayEquals(exp, bs.getSortedArray());
    }
}