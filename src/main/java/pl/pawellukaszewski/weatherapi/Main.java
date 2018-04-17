package pl.pawellukaszewski.weatherapi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.pawellukaszewski.weatherapi.models.Utils;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainView.fxml"));
        primaryStage.setTitle("WeatherApp");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(true);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.getIcons().add(new Image("https://cdn3.iconfinder.com/data/icons/weather-and-forecast/51/Weather_icons_grey-03-512.png"));
        primaryStage.show();


    }


    public static void main(String[] args) {

        launch(args);

    }

}
