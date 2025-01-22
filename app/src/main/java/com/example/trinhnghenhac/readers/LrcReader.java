package com.example.trinhnghenhac.readers;

import android.util.Log;

import com.example.trinhnghenhac.models.Lyrics;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcReader {
    private final URL mUrl;

    public LrcReader(String url) throws MalformedURLException {
        mUrl = new URL(url);
    }

    public LrcReader(URL url) {
        this.mUrl = url;
    }

    public Lyrics read() {
        Pattern pat = Pattern.compile("^\\[(\\d{2}):(\\d{2}).(\\d{2})](.*)$", Pattern.MULTILINE);
        List<Lyrics.Sentence> sentences = new ArrayList<>();
        try {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) mUrl.openConnection();
            httpUrlConnection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
            try (InputStream is = httpUrlConnection.getInputStream();
                 Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name())) {
                if (sc.hasNext("\\ufeff"))
                    sc.skip("\\ufeff"); // Skip BOM
                List<String> lines = new ArrayList<>();
                while (sc.hasNextLine()) lines.add(sc.nextLine());

                // Must be read from end to obtain endTime from startTime of next line
                for (int i = lines.size() - 1; i >= 0; i--) {
                    String rawLine = lines.get(i).trim();
                    Matcher matcher = pat.matcher(rawLine);
                    if (!matcher.matches()) continue;
                    int m = Integer.parseInt(matcher.group(1));
                    int s = Integer.parseInt(matcher.group(2));
                    int cs = Integer.parseInt(matcher.group(3));
                    String line = matcher.group(4);
                    Lyrics.Sentence sentence = new Lyrics.Sentence(line,
                            m * 60000 + s * 1000 + cs * 10,
                             i == lines.size() - 1 ? Lyrics.END_TIME_ENDLESS : sentences.get(0).getStartTime());
                    sentences.add(0, sentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Lyrics(sentences);
    }
}
