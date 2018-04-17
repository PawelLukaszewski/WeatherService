package pl.pawellukaszewski.weatherapi.models;

public class WeatherInfo {
    private double temp;
    private int preassure;
    private int clouds;
    private int windSpeed;
    private String cityname;

    public WeatherInfo(double temp, int preassure, int clouds, int Visibility, String cityname) {
        this.temp = temp;
        this.preassure = preassure;
        this.clouds = clouds;
        this.windSpeed = Visibility;
        this.cityname = cityname;

    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public int getVisibility() {
        return windSpeed;
    }

    public void setVisibility(int visibility) {
        this.windSpeed = visibility;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }


    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getPreassure() {
        return preassure;
    }

    public void setPreassure(int preassure) {
        this.preassure = preassure;
    }
}
