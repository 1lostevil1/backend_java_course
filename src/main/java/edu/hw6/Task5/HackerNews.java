package edu.hw6.Task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.net.http.HttpClient.newHttpClient;

public class HackerNews {
    private final static int MAX_DURATION = 30;

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                .GET()
                .timeout(Duration.of(MAX_DURATION, ChronoUnit.SECONDS))
                .build();

            var response = newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString()).body();

            String[] stringIds = response.substring(1, response.length() - 1).split(",");
            long[] ids = new long[stringIds.length];

            for (int i = 0; i < stringIds.length; i++) {
                ids[i] = Long.parseLong(stringIds[i]);
            }

            return ids;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return new long[]{};
        }
    }

    public static String news(long id) {
        try {
            String encodedId = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/item/" + encodedId + ".json"))
                .GET()
                .timeout(Duration.of(MAX_DURATION, ChronoUnit.SECONDS))
                .build();

            var response = newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString()).body();

            Matcher matcher = Pattern.compile("\"title\":\"(.+?)\"").matcher(response);

            if (matcher.find()) {
                return matcher.group(1);
            }

            return null;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return null;
        }
    }
}
