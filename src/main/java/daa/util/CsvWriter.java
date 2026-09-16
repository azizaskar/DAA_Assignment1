package daa.util;

import daa.bench.Result;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CsvWriter {
    public static void write(Path path, List<Result> results) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");
            for (Result r : results) {
                writer.write(r.toCsvLine());
                writer.newLine();
            }
        }
    }
}