package daa.bench;

import daa.algorithms.MergeSort;
import daa.algorithms.QuickSelect;
import daa.algorithms.QuickSort;
import daa.metrics.Metrics;

import java.util.*;

public class Benchmark {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final int RUNS = 5;

    public List<Result> run() {
        List<Result> results = new ArrayList<>();
        Random rng = new Random(42);

        for (int n : SIZES) {
            for (InputType type : InputType.values()) {
                System.out.printf("Running: n = %d, input = %s...%n", n, type.getLabel());

                // 1. MergeSort
                results.add(benchmarkSort("MergeSort", type, n, rng, MergeSort::sort));

                // 2. QuickSort
                results.add(benchmarkSort("QuickSort", type, n, rng, QuickSort::sort));

                // 3. QuickSelect (k = n / 2)
                results.add(benchmarkSelect("QuickSelect", type, n, rng));
            }
        }
        return results;
    }

    private Result benchmarkSort(String name, InputType type, int n, Random rng, SortFunction func) {
        List<Double> times = new ArrayList<>();
        long totalComparisons = 0;
        int maxDepth = 0;

        for (int run = 0; run < RUNS; run++) {
            int[] arr = generateArray(type, n, rng);
            Metrics m = new Metrics();

            long start = System.nanoTime();
            func.sort(arr, m);
            long elapsed = System.nanoTime() - start;

            times.add(elapsed / 1_000_000.0);
            totalComparisons = m.getComparisons();
            maxDepth = m.getMaxDepth();
        }

        Collections.sort(times);
        double medianTime = times.get(RUNS / 2);

        return new Result(name, type.getLabel(), n, medianTime, totalComparisons, maxDepth);
    }

    private Result benchmarkSelect(String name, InputType type, int n, Random rng) {
        List<Double> times = new ArrayList<>();
        long totalComparisons = 0;
        int maxDepth = 0;
        int k = n / 2;

        for (int run = 0; run < RUNS; run++) {
            int[] arr = generateArray(type, n, rng);
            Metrics m = new Metrics();

            long start = System.nanoTime();
            QuickSelect.select(arr, k, m);
            long elapsed = System.nanoTime() - start;

            times.add(elapsed / 1_000_000.0);
            totalComparisons = m.getComparisons();
            maxDepth = m.getMaxDepth();
        }

        Collections.sort(times);
        double medianTime = times.get(RUNS / 2);

        return new Result(name, type.getLabel(), n, medianTime, totalComparisons, maxDepth);
    }

    private int[] generateArray(InputType type, int n, Random rng) {
        int[] arr = new int[n];
        switch (type) {
            case RANDOM:
                for (int i = 0; i < n; i++) arr[i] = rng.nextInt();
                break;
            case SORTED:
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case DUPLICATES:
                for (int i = 0; i < n; i++) arr[i] = rng.nextInt(10);
                break;
        }
        return arr;
    }

    @FunctionalInterface
    interface SortFunction {
        void sort(int[] a, Metrics m);
    }
}