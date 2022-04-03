package com.test.app;

public class BubbleSort {

    private final int array[];

    public BubbleSort(int[] array) {
        this.array = array;
        bubbleSort(this.array);
    }

    private void bubbleSort(int[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] > arr[j+1])
                {
                    // swap arr[j+1] and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }

    public int[] getSortedArray() {
        return array;
    }

}
