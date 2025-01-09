package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CorpusReader {
    public static List<String> loadCorpus(String directoryPath) throws IOException {
        List<String> corpus = new ArrayList<>();
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    corpus.add(file.getAbsolutePath());
                }
            }
        }
        return corpus;
    }
}
