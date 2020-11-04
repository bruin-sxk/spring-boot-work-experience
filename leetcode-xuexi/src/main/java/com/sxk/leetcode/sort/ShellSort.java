package com.sxk.leetcode.sort;

import java.util.Arrays;

public class ShellSort {

	public static void main(String[] args) {
		int count = 0;
		int[] ints = new int[100];
		for (int i = 100; i > 0; i--) {
			ints[count++] = i + 1;
		}

		shellSortComplete(ints);
		System.out.println(Arrays.toString(ints));

		shellSort(ints);
		System.out.println(Arrays.toString(ints));
	}

	private static void shellSortComplete(int[] ints) {
		int gap = ints.length / 2;
		while (gap >= 1) {
			for (int i = gap; i < ints.length; i++) {
				int temp = ints[i];
				for (int j = i - gap; j >= 0 && ints[j] > temp; j = j - gap) {
					int tmp = ints[j];
					ints[j] = ints[j + gap];
					ints[j + gap] = tmp;
				}
			}
			gap = gap / 2;
		}
	}


	/**
	 * 希尔排序算法
	 */
	public static void shellSort(int[] list) {
		int len = list.length ;
		// 取增量
		int gap = len / 2;

		while (gap >= 1) {
			// 无序序列
			for (int i = gap; i < len; i++) {
				int temp = list[i];
				int j;

				// 有序序列
				for (j = i - gap; j >= 0 && list[j] > temp; j = j - gap) {
					list[j + gap] = list[j];
				}
				list[j + gap] = temp;
			}

			// 缩小增量
			gap = gap / 2;
		}
	}


}
