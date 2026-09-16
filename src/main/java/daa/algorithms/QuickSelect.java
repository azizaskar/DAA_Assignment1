package daa.algorithms;

import daa.metrics.Metrics;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Index k is out of bounds: " + k);
        }

        return quickSelect(a, 0, a.length - 1, k, metrics);
    }

    private static int quickSelect(int[] a, int low, int high, int k, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (low == high) {
                return a[low];
            }

            // QuickSort класындағы 3-way partition әдісін қайта пайдаланамыз
            int[] pivotRange = QuickSort.partition(a, low, high, metrics);
            int lt = pivotRange[0];
            int gt = pivotRange[1];

            // Егер k тең элементтер аралығында [lt, gt] болса, элемент табылды
            if (k >= lt && k <= gt) {
                return a[k];
            } else if (k < lt) {
                // Тек сол жақ бөліктен іздейміз
                return quickSelect(a, low, lt - 1, k, metrics);
            } else {
                // Тек оң жақ бөліктен іздейміз
                return quickSelect(a, gt + 1, high, k, metrics);
            }
        } finally {
            metrics.exitRecursion();
        }
    }
}