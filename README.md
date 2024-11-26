# Weather App (simple)

This is a simple Java application that requests Yandex API to get weather data.

## Configuration
Application configure by environment variables. See `.env.example`.  
Get the Yandex Weather API key [here](https://yandex.ru/pogoda/b2b/console/home)

## Run
You could use pre-built docker image:
```
docker run \
    -it \
    --rm \
    -e WEATHER_APP_YANDEX_API_KEY=<your api key> \
    -e WEATHER_APP_LATITUDE=55.7558 \
    -e WEATHER_APP_LONGITUDE=37.6173 \
    ghcr.io/vterdunov/weather-app
```