package weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class YandexWeatherClient {
    public static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public YandexWeatherClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public JsonNode weatherData(double lat, double lon) throws Exception {
        String url = String.format("%s?lat=%f&lon=%f", API_URL, lat, lon);
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Yandex-API-Key", apiKey)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // https://www.baeldung.com/jackson-object-mapper-tutorial#3-json-to-jackson-jsonnode
            return objectMapper.readTree(response.body());
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
            throw e;
        }
    }

    public double temperature(JsonNode weatherData) {
        return weatherData.get("fact").get("temp").asDouble();
    }

    public double averageTemperature(JsonNode weatherData, int days) {
        JsonNode forecasts = weatherData.get("forecasts");
        if (days > forecasts.size()) {
            days = forecasts.size();
        }

        double sum = 0;
        for (int i = 0; i < days; i++) {
            sum += forecasts.get(i).get("parts").get("day").get("temp_avg").asDouble();
        }

        return sum / days;
    }
}