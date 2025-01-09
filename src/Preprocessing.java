package src;

import org.tartarus.snowball.ext.PorterStemmer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Preprocessing {
    private Set<String> stopwords;

    public Preprocessing(String stopwordsPath) throws IOException {
        stopwords = new HashSet<>(Files.readAllLines(new File(stopwordsPath).toPath()));
    }

    public String preprocessFile(String filePath) throws IOException {
        String content = Files.readString(new File(filePath).toPath()).toLowerCase();
        String[] words = content.replaceAll("[^a-zA-Z ]", "").split("\\s+");

        PorterStemmer stemmer = new PorterStemmer();
        return Arrays.stream(words)
                .filter(word -> !stopwords.contains(word))
                .map(word -> {
                    stemmer.setCurrent(word);
                    stemmer.stem();
                    return stemmer.getCurrent();
                })
                .collect(Collectors.joining(" "));
    }
}
