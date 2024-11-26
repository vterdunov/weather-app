import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.YandexWeatherClient;

public class Main {
    public static void main(String[] args) {
        System.out.println("This is a Simple Weather App.");
        String apiKey = System.getenv("WEATHER_APP_YANDEX_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Error: YANDEX_WEATHER_API_KEY environment variable is not set");
            System.exit(1);
        }

        String latStr = System.getenv("WEATHER_APP_LATITUDE");
        String lonStr = System.getenv("WEATHER_APP_LONGITUDE");

        if (latStr == null || lonStr == null) {
            System.err.println("Error: WEATHER_APP_LATITUDE or WEATHER_APP_LONGITUDE environment variables are not set");
            System.exit(1);
        }

        YandexWeatherClient yandexWeatherClient = new YandexWeatherClient(apiKey);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lonStr);
            JsonNode weather = yandexWeatherClient.weatherData(latitude, longitude);

            System.out.println("Full weather data:");
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(weather));

            System.out.println("Current temperature: " + yandexWeatherClient.temperature(weather));

            int days = 3;
            System.out.println("Average temperature for " + days + " days: " +
                    yandexWeatherClient.averageTemperature(weather, days));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
