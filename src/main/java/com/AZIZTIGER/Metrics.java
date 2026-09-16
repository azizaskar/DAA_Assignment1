package com.AZIZTIGER;

public class Metrics {
    private long comparisons = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private long timeNano = 0;

    public void incrementComparisons() {
        comparisons++;
    }

    public void addComparisons(long count) {
        comparisons += count;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public void reset() {
        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;
        timeNano = 0;
    }

    // Getters and Setters
    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getTimeNano() {
        return timeNano;
    }

    public void setTimeNano(long timeNano) {
        this.timeNano = timeNano;
    }
}