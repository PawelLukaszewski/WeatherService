package pl.pawellukaszewski.weatherapi.models.services;

import javafx.application.Platform;
import org.json.JSONObject;
import pl.pawellukaszewski.weatherapi.models.Config;
import pl.pawellukaszewski.weatherapi.models.IWeatherObserver;
import pl.pawellukaszewski.weatherapi.models.Utils;
import pl.pawellukaszewski.weatherapi.models.WeatherInfo;

import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService {
    private static WeatherService ourInstance = new WeatherService();

    public static WeatherService getService() {
        return ourInstance;
    }

    private List<IWeatherObserver> observer = new ArrayList<>();

    private ExecutorService executorService;

    private WeatherService() {
        executorService = Executors.newSingleThreadExecutor();

    }

    public void makeRequest(String city) {

        Runnable runnable = () -> readJsonData(Utils.makeHttpRequest(Config.APP_BASE_URL + city + "&appid=" + Config.APP_ID),city);
        executorService.execute(runnable);


    }


    private void readJsonData(String json, String cityname) {
        JSONObject root = new JSONObject(json);
        JSONObject main = root.getJSONObject("main");
        JSONObject clouds = root.getJSONObject("clouds");
        JSONObject wind = root.getJSONObject("wind");


        double temp = main.getDouble("temp");
        double windSpeed = wind.getDouble("speed");
        int tempInC = (int) (temp - 273);
        double pressure = main.getDouble("pressure");
        int allClouds = (int) clouds.getDouble("all");

        System.out.println("temp to: " + temp);
        System.out.println("temp w stopniach C to: " + tempInC + "°C");
        System.out.println("Cisnienie to: " + pressure + "hPa");
        System.out.println("Zachmurzenie to: " + allClouds + "%");

//foreach z lambda
        observer.forEach(s ->
                Platform.runLater(() -> s.onWeatherUpdate(new WeatherInfo(tempInC, (int) pressure, allClouds, (int) windSpeed, cityname))));


//        wersja ze standardowym foreachem
//        for (IWeatherObserver iWeatherObserver : observer) {
//            iWeatherObserver.onWeatherUpdate(new WeatherInfo(tempInC, (int) pressure, allClouds, visibilityInKm));
//        }
    }

    public void registerObserver(IWeatherObserver observer) {
        this.observer.add(observer);
    }


}
