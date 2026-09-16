package daa.bench;

public class Result {
    private final String algorithm;
    private final String input;
    private final int n;
    private final double timeMs;
    private final long comparisons;
    private final int maxDepth;

    public Result(String algorithm, String input, int n, double timeMs, long comparisons, int maxDepth) {
        this.algorithm = algorithm;
        this.input = input;
        this.n = n;
        this.timeMs = timeMs;
        this.comparisons = comparisons;
        this.maxDepth = maxDepth;
    }

    public String toCsvLine() {
        return String.format("%s,%s,%d,%.4f,%d,%d", algorithm, input, n, timeMs, comparisons, maxDepth);
    }
}