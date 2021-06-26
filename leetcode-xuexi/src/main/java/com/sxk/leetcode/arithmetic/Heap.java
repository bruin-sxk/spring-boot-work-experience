package com.sxk.leetcode.arithmetic;

import java.util.Arrays;
import java.util.Random;

public class Heap {

    private static class LeftMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public LeftMaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            this.heapSize = 0;
        }

        public boolean isEmpty() {
            return this.heapSize == 0;
        }

        public boolean isFull() {
            return this.heapSize == this.limit;
        }

        public void push(int val) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full,plz wait...");
            }
            heap[heapSize] = val;
            heapInsert(heap, heapSize++);
        }

        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        private void heapify(int[] heap, int index, int heapSize) {
            int childLfet = 2 * index + 1;
            while (childLfet < heapSize) {
                int childRight = childLfet + 1;
                int largest = childRight < heapSize && heap[childLfet] > heap[childRight] ? childRight : childLfet;
                largest = heap[largest] > heap[index] ? largest : index;
                if (largest == index) {
                    break;
                }
                swap(heap, largest, index);
                index = largest;
                childLfet = index * 2 + 1;
            }
        }

        private void heapInsert(int[] heap, int i) {
            while (heap[i] > heap[(i - 1) / 2]) {
                swap(heap, i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }

    public static void main(String[] args) {
        LeftMaxHeap leftMaxHeap = new LeftMaxHeap(32);

        final Random random = new Random();
        for (int i = 0; i < 32; i++) {
            int val = random.nextInt(500);
            leftMaxHeap.push(val);
        }
        int[] heap = leftMaxHeap.heap;
        System.out.println(Arrays.toString(heap));


    }


}
