package pl.pawellukaszewski.weatherapi.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pl.pawellukaszewski.weatherapi.dao.WeatherDao;
import pl.pawellukaszewski.weatherapi.dao.impl.WeatherDaoImpl;
import pl.pawellukaszewski.weatherapi.models.WeatherModel;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    @FXML
    BarChart chartTemp;

    @FXML
    ListView<String> listCities;

    @FXML
    Button buttonBack;

    private ObservableList<String> cityObservableList;
    private WeatherDao weatherDao = new WeatherDaoImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cityObservableList = FXCollections.observableList(weatherDao.getCities());
        listCities.setItems(cityObservableList);

        buttonBack.setOnMouseClicked(event -> backToWeatherCheck());

        listCities.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> generateChart(newValue)
        );

    }

    private void backToWeatherCheck() {

        Stage stage = (Stage) buttonBack.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainView.fxml"));
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateChart(String cityname) {
        List<WeatherModel> weatherList = weatherDao.getAllWeatherData(cityname);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(cityname);
        for (WeatherModel weatherModel : weatherList) {
            series.getData().add(new XYChart.Data<>(weatherModel.getDate().toString(), weatherModel.getTemp()));
        }

        chartTemp.getData().clear();
        chartTemp.getData().add(series);
    }
}
