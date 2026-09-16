package daa.algorithms;

import daa.metrics.Metrics;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        quickSort(a, 0, a.length - 1, metrics);
    }

    private static void quickSort(int[] a, int low, int high, Metrics metrics) {
        metrics.enterRecursion();
        try {
            while (low < high) {
                // 3-Way partition арқылы тең элементтер аралығын [lt, gt] аламыз
                int[] pivotRange = partition(a, low, high, metrics);
                int lt = pivotRange[0];
                int gt = pivotRange[1];

                int leftSize = lt - low;
                int rightSize = high - gt;

                // Smaller side first: кіші жағын рекурсияға жібереміз, үлкенін циклде қалдырамыз
                if (leftSize < rightSize) {
                    if (low < lt - 1) {
                        quickSort(a, low, lt - 1, metrics);
                    }
                    low = gt + 1;
                } else {
                    if (gt + 1 < high) {
                        quickSort(a, gt + 1, high, metrics);
                    }
                    high = lt - 1;
                }
            }
        } finally {
            metrics.exitRecursion();
        }
    }

    // QuickSelect-те қайта пайдалану үшін public әдіс ретінде жазылды
    public static int[] partition(int[] a, int low, int high, Metrics metrics) {
        // Random Pivot таңдау
        int randomIndex = ThreadLocalRandom.current().nextInt(low, high + 1);
        swap(a, low, randomIndex);

        int pivot = a[low];
        int lt = low;
        int gt = high;
        int i = low + 1;

        // Dijkstra 3-way partitioning
        while (i <= gt) {
            metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}