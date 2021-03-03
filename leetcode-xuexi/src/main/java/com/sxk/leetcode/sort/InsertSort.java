package com.sxk.leetcode.sort;

import java.util.Arrays;

public class InsertSort {

    public static void main(String[] args) {
        int count = 0;
        int[] ints = new int[100];
        for (int i = 100; i > 0; i--) {
            ints[count++] = i + 1;
        }

        insertSort(ints);

        System.out.println(Arrays.toString(ints));
    }

    private static void insertSort(int[] ints) {
        int length = ints.length;
        for (int i = 0; i < length; i++) {
            int max = 0;
            int len = length - i - 1;
            for (int j = 0; j <= len; j++) {
                if (ints[j] > ints[max]) {
                    max = j;
                }
            }
            if (max < len) {
                int temp = ints[max];
                ints[max] = ints[len];
                ints[len] = temp;
            }
        }
    }
}
