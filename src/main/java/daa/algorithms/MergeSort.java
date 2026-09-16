package daa.algorithms;

import daa.metrics.Metrics;

public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        // Reusable buffer: тек 1 рет бөлінеді
        int[] buffer = new int[a.length];
        mergeSort(a, buffer, 0, a.length - 1, metrics);
    }

    private static void mergeSort(int[] a, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        try {
            // Cutoff: 15 элемент немесе одан аз болса, Insertion Sort
            if (right - left + 1 <= CUTOFF) {
                insertionSort(a, left, right, metrics);
                return;
            }

            int mid = left + (right - left) / 2;
            mergeSort(a, buffer, left, mid, metrics);
            mergeSort(a, buffer, mid + 1, right, metrics);

            merge(a, buffer, left, mid, right, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static void merge(int[] a, int[] buffer, int left, int mid, int right, Metrics metrics) {
        System.arraycopy(a, left, buffer, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.incrementComparisons();
            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            a[k++] = buffer[i++];
        }
        while (j <= right) {
            a[k++] = buffer[j++];
        }
    }

    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incrementComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}