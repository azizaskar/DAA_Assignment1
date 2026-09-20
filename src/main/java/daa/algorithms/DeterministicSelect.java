package daa.algorithms;

import daa.metrics.Metrics;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid index k or null array");
        }
        int[] copy = a.clone();
        return select(copy, 0, copy.length - 1, k, metrics);
    }

    private static int select(int[] a, int left, int right, int k, Metrics metrics) {
        if (left == right) {
            return a[left];
        }

        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(a, left, right, metrics);
            return a[left + k];
        }

        // 1. Рекурсивно находим медиану медиан в качестве опорного элемента (pivot)
        int pivot = getMedianOfMedians(a, left, right, metrics);

        // 2. Выполняем 3-way partition
        int[] bounds = partition3Way(a, left, right, pivot, metrics);
        int lt = bounds[0];
        int gt = bounds[1];

        int leftCount = lt - left;
        int equalCount = gt - lt + 1;

        if (k < leftCount) {
            return select(a, left, lt - 1, k, metrics);
        } else if (k < leftCount + equalCount) {
            return pivot;
        } else {
            return select(a, gt + 1, right, k - leftCount - equalCount, metrics);
        }
    }

    private static int getMedianOfMedians(int[] a, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int gLeft = left + i * 5;
            int gRight = Math.min(gLeft + 4, right);
            insertionSort(a, gLeft, gRight, metrics);
            medians[i] = a[gLeft + (gRight - gLeft) / 2];
        }

        return select(medians, 0, medians.length - 1, medians.length / 2, metrics);
    }

    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                if (metrics != null) metrics.incrementComparisons();
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

    private static int[] partition3Way(int[] a, int left, int right, int pivot, Metrics metrics) {
        int lt = left;
        int gt = right;
        int i = left;
        while (i <= gt) {
            if (metrics != null) metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else {
                if (metrics != null) metrics.incrementComparisons();
                if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}