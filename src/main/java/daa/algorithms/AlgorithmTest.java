package daa.algorithms;

import daa.metrics.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    private Metrics metrics;
    private final Random random = new Random(42);

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    // 1. MergeSort: 100 кездейсоқ массивте тексеру
    @Test
    void testMergeSortCorrectness() {
        for (int t = 0; t < 100; t++) {
            int n = random.nextInt(500) + 1;
            int[] actual = random.ints(n, -1000, 1000).toArray();
            int[] expected = actual.clone();

            Arrays.sort(expected);
            MergeSort.sort(actual, metrics);

            assertArrayEquals(expected, actual);
        }
    }

    // MergeSort: Шеткі жағдайлар
    @Test
    void testMergeSortEdgeCases() {
        // Бос массив
        int[] empty = new int[0];
        MergeSort.sort(empty, metrics);
        assertArrayEquals(new int[0], empty);

        // 1 элемент
        int[] single = {42};
        MergeSort.sort(single, metrics);
        assertArrayEquals(new int[]{42}, single);

        // Барлық элементтері тең массив
        int[] duplicates = {5, 5, 5, 5, 5};
        MergeSort.sort(duplicates, metrics);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, duplicates);

        // Алдын ала сұрыпталған массив
        int[] sorted = {1, 2, 3, 4, 5, 6};
        MergeSort.sort(sorted, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, sorted);
    }

    // 2. QuickSort: 100 кездейсоқ массивте тексеру
    @Test
    void testQuickSortCorrectness() {
        for (int t = 0; t < 100; t++) {
            int n = random.nextInt(500) + 1;
            int[] actual = random.ints(n, -1000, 1000).toArray();
            int[] expected = actual.clone();

            Arrays.sort(expected);
            QuickSort.sort(actual, metrics);

            assertArrayEquals(expected, actual);
        }
    }

    // QuickSort: Шеткі жағдайлар
    @Test
    void testQuickSortEdgeCases() {
        int[] empty = new int[0];
        QuickSort.sort(empty, metrics);
        assertArrayEquals(new int[0], empty);

        int[] duplicates = {7, 7, 7, 7, 7, 7};
        QuickSort.sort(duplicates, metrics);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7, 7}, duplicates);
    }

    // 3. QuickSort: Рекурсия тереңдігін тексеру (n = 100 000, maxDepth <= 2 * log2(n))
    @Test
    void testQuickSortRecursionDepthOnSortedArray() {
        int n = 100_000;
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            sorted[i] = i;
        }

        QuickSort.sort(sorted, metrics);

        double log2n = Math.log(n) / Math.log(2);
        int maxAllowedDepth = (int) Math.ceil(2 * log2n);

        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Max depth was " + metrics.getMaxDepth() + ", but expected <= " + maxAllowedDepth);
    }

    // 4. QuickSelect: 100 кездейсоқ массивте тексеру
    @Test
    void testQuickSelectCorrectness() {
        for (int t = 0; t < 100; t++) {
            int n = random.nextInt(200) + 1;
            int[] arr = random.ints(n, -1000, 1000).toArray();
            int k = random.nextInt(n);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            int actual = QuickSelect.select(arr, k, metrics);
            assertEquals(expected, actual);
        }
    }

    // QuickSelect: Қате енгізулерді тексеру
    @Test
    void testQuickSelectInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[0], 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, 3, metrics));
    }
}