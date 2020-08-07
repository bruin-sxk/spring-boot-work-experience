package com.sxk.leetcode.sort;

import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		int count = 0;
		int[] ints = new int[100];
		for (int i = 100; i > 0; i--) {
			ints[count++] = i + 1;
		}

		quickSort(ints, 0, ints.length - 1);

		System.out.println(Arrays.toString(ints));
	}

	private static void quickSort(int[] ints, int low, int hight) {
		if (low < hight) {
			// 查找基准数的正确索引
			int index = getIndex(ints, low, hight);

			quickSort(ints, low, index - 1);
			quickSort(ints, index + 1, hight);
		}
	}


	private static int getIndex(int[] ints, int low, int hight) {
		// 基准数据
		int temp = ints[low];
		while (low < hight) {
			// 从队尾开始遍历，直到找到第一个比基准值小的数，让 low 等于该位置的值
			// 也可以理解为交换两个位置的数据
			while (low < hight && temp < ints[hight])
				hight--;
			ints[low] = ints[hight];
			// 在从队头开始遍历 继续和基准值进行比较 直到找到第一个比基准值大的
			// hight 等于 该位置的值(也可理解为交换两个位置的数据)
			while (low < hight && temp > ints[low])
				low++;
			ints[hight] = ints[low];
		}
		// 跳出循环也就意味着 第一次的排列结束 结束之后 基准值左边全部都是比基准值小的,右边的全部都是比基准值大的
		ints[low] = temp;
		return low;
	}

}
