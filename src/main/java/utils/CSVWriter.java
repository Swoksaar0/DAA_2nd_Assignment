package utils;

import java.io.*;

public class CSVWriter implements Closeable {
    private final PrintWriter pw;
    private boolean headerWritten = false;

    public CSVWriter(String path) throws IOException {
        this.pw = new PrintWriter(new FileWriter(path), true);
    }

    public void writeHeader(String... cols) {
        if (!headerWritten) {
            pw.println(String.join(",", cols));
            headerWritten = true;
        }
    }

    public void writeRow(Object... vals) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i]);
            if (i < vals.length-1) sb.append(",");
        }
        pw.println(sb);
    }

    @Override
    public void close() { pw.close(); }
}