package com.sxk.leetcode.sort;

import java.util.Arrays;

public class BubbleSort {


    public static void main(String[] args) {
        int count = 0;
        int[] ints = new int[100];
        for (int i = 100; i > 0; i--) {
            ints[count++] = i + 1;
        }
        System.out.println(Arrays.toString(ints));

        bubbleSort(ints);

        System.out.println(Arrays.toString(ints));
    }

    private static void bubbleSort(int[] ints) {
        boolean f = false;
        int length = ints.length;
        for (int i = 0; i < length; i++) {
            if (f) break;
            for (int j = 0; j < length - 1 - i; j++) {
                f = true;
                if (ints[j] > ints[j + 1]) {
                    int temp = ints[j];
                    ints[j] = ints[j + 1];
                    ints[j + 1] = temp;
                    f = false;
                }
            }
        }
    }

}
