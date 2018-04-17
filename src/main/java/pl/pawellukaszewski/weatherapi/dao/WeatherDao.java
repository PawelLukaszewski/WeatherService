package pl.pawellukaszewski.weatherapi.dao;

import pl.pawellukaszewski.weatherapi.models.WeatherModel;

import java.util.List;

public interface WeatherDao {

    void addWeather(WeatherModel model);
    List<WeatherModel> getAllWeatherData(String cityname);
    List<String> getCities();
}
