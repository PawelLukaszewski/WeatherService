package pl.pawellukaszewski.weatherapi.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import pl.pawellukaszewski.weatherapi.dao.WeatherDao;
import pl.pawellukaszewski.weatherapi.dao.impl.WeatherDaoImpl;
import pl.pawellukaszewski.weatherapi.models.IWeatherObserver;
import pl.pawellukaszewski.weatherapi.models.WeatherInfo;
import pl.pawellukaszewski.weatherapi.models.WeatherModel;
import pl.pawellukaszewski.weatherapi.models.services.WeatherService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, IWeatherObserver {


    private WeatherService weatherService = WeatherService.getService();


    @FXML
    TextField textCity;

    @FXML
    Button buttonSend;

    @FXML
    Label labelTemp, labelPress, labelClouds, labelWindSpeed, labelCityName;

    @FXML
    ProgressIndicator progressIndi;

    @FXML
    Button buttonCharts;

    private WeatherDao weatherDao = new WeatherDaoImpl();


    public void initialize(URL location, ResourceBundle resources) {

        progressIndi.setVisible(false);
        labelCityName.setVisible(false);
        labelTemp.setVisible(false);
        labelClouds.setVisible(false);
        labelPress.setVisible(false);
        labelWindSpeed.setVisible(false);


        weatherService.registerObserver(this);


        buttonSend.setOnMouseClicked(e -> {
            if (!textCity.getText().isEmpty()) {
                progressIndi.setVisible(true);
                labelCityName.setVisible(false);
                labelTemp.setVisible(false);
                labelClouds.setVisible(false);
                labelPress.setVisible(false);
                labelWindSpeed.setVisible(false);
                weatherService.makeRequest(textCity.getText());
                textCity.clear();
            }

        });
        textCity.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER && !textCity.getText().isEmpty()) {
                progressIndi.setVisible(true);
                labelCityName.setVisible(false);
                labelTemp.setVisible(false);
                labelClouds.setVisible(false);
                labelPress.setVisible(false);
                labelWindSpeed.setVisible(false);

                weatherService.makeRequest(textCity.getText());
                textCity.clear();
            }
        });


        buttonCharts.setOnMouseClicked(e -> goToCharts());

    }

    private void goToCharts() {
        Stage stage = (Stage) buttonCharts.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("chartView.fxml"));
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onWeatherUpdate(WeatherInfo info) {
        labelTemp.setText("Temp -> " + info.getTemp() + " °C");
        labelPress.setText("Cisnienie -> " + info.getPreassure() + "hPa");
        labelClouds.setText("Zachmurzenie -> " + info.getClouds() + "%");
        labelWindSpeed.setText("Predkosc wiatru -> " + info.getVisibility() + "km/h");
        labelCityName.setText(info.getCityname() + ":");
        progressIndi.setVisible(false);
        labelTemp.setVisible(true);
        labelClouds.setVisible(true);
        labelPress.setVisible(true);
        labelWindSpeed.setVisible(true);
        labelCityName.setVisible(true);
        weatherDao.addWeather(new WeatherModel(info));
    }
}
