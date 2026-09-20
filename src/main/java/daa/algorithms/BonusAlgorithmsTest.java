package daa.algorithms;

import daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BonusAlgorithmsTest {

    @Test
    void testDeterministicSelectCorrectness() {
        Random rand = new Random(42);
        for (int trial = 0; trial < 100; trial++) {
            int n = 100 + rand.nextInt(400);
            int[] arr = rand.ints(n, -5000, 5000).toArray();
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = rand.nextInt(n);
            Metrics metrics = new Metrics();
            int actual = DeterministicSelect.select(arr, k, metrics);

            assertEquals(sorted[k], actual, "Median-of-Medians returned wrong element!");
        }
    }

    @Test
    void testClosestPairAgainstBruteForce() {
        Random rand = new Random(42);
        // Тапсырма талабы: n <= 2000 нүкте үшін Brute-Force пен салыстыру
        int[] sizes = {10, 50, 200, 1000, 2000};

        for (int n : sizes) {
            Point2D[] points = new Point2D[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point2D(rand.nextDouble() * 10000.0, rand.nextDouble() * 10000.0);
            }

            double expected = ClosestPair.bruteForce(points);
            double actual = ClosestPair.findClosestPair(points);

            assertEquals(expected, actual, 1e-9, "Closest pair distance does not match brute-force!");
        }
    }

    @Test
    void testComparisonQuickSelectVsDeterministicSelect() {
        int n = 50000;
        Random rand = new Random(123);
        int[] randomArr = rand.ints(n, 0, 100000).toArray();
        int[] sortedArr = randomArr.clone();
        Arrays.sort(sortedArr);

        int k = n / 2;

        Metrics mQuickRandom = new Metrics();
        QuickSelect.select(randomArr.clone(), k, mQuickRandom);

        Metrics mDetRandom = new Metrics();
        DeterministicSelect.select(randomArr.clone(), k, mDetRandom);

        Metrics mQuickSorted = new Metrics();
        QuickSelect.select(sortedArr.clone(), k, mQuickSorted);

        Metrics mDetSorted = new Metrics();
        DeterministicSelect.select(sortedArr.clone(), k, mDetSorted);

        System.out.println("--- QuickSelect vs DeterministicSelect (n = " + n + ") ---");
        System.out.println("Random array comparisons: QuickSelect=" + mQuickRandom.getComparisons() + ", DetSelect=" + mDetRandom.getComparisons());
        System.out.println("Sorted array comparisons: QuickSelect=" + mQuickSorted.getComparisons() + ", DetSelect=" + mDetSorted.getComparisons());

        assertTrue(mDetRandom.getComparisons() > 0);
        assertTrue(mDetSorted.getComparisons() > 0);
    }
}