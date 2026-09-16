package daa;

import daa.bench.Benchmark;
import daa.bench.Result;
import daa.util.CsvWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public final class Main {
    public static void main(String[] args) throws IOException {
        Path output = Path.of(args.length > 0 ? args[0] : "results.csv");

        Benchmark benchmark = new Benchmark();
        List<Result> results = benchmark.run();

        CsvWriter.write(output, results);

        System.out.println();
        System.out.println("Saved " + results.size() + " rows to " + output.toAbsolutePath());
    }
}