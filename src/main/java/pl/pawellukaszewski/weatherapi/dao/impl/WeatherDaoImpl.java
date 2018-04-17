package pl.pawellukaszewski.weatherapi.dao.impl;

import pl.pawellukaszewski.weatherapi.dao.WeatherDao;
import pl.pawellukaszewski.weatherapi.models.MySQLConnector;
import pl.pawellukaszewski.weatherapi.models.WeatherInfo;
import pl.pawellukaszewski.weatherapi.models.WeatherModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDaoImpl implements WeatherDao {


     MySQLConnector connector = MySQLConnector.getInstance();

    @Override
    public void addWeather(WeatherModel model) {
        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "INSERT INTO weather VALUES(?,?,?,?)");

        try {
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, model.getCityname());
            preparedStatement.setFloat(3, model.getTemp());
            preparedStatement.setDate(4,null);

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WeatherModel> getAllWeatherData(String cityname) {
        List<WeatherModel> cityList = new ArrayList<>();

        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT * FROM weather WHERE cityname=?");

        try {
            preparedStatement.setString(1, cityname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cityList.add(new WeatherModel(resultSet.getString("cityname"), resultSet.getFloat("temp"), resultSet.getDate("date")));
            }
            return cityList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<String> getCities() {
        List<String> cityNames = new ArrayList<>();


        PreparedStatement preparedStatement = connector.getPreparedStatement(
                "SELECT DISTINCT cityname FROM `weather`");

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cityNames.add(resultSet.getString("cityname"));
            }
            return cityNames;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
