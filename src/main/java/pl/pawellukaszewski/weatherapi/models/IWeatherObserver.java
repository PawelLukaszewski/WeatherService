package pl.pawellukaszewski.weatherapi.models;

public interface IWeatherObserver {

    void onWeatherUpdate(WeatherInfo info);
}
