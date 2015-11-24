package prosoft.weatherv1;

/**
 * Created by Sylwester Zalewski on 17/11/2015.
 * This class stores weather data about cities.
 */

public class WeatherData {


    private double coordLon;
    private double coordLat;
    private String weatherID;
    private String weatherMain;
    private String weatherDesc;
    private double mainTemp;
    private String windSpeed;
    private String clouds;
    private String rain3h;
    private String snow3h;
    private String sysSunrise;
    private String sysSunset;
    private String ID;
    private String City;

    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////



    public double getCoordLon() {
        return coordLon;
    }

    public void setCoordLon(double coordLon) {
        this.coordLon = coordLon;
    }

    public double getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(double coordLat) {
        this.coordLat = coordLat;
    }

    public String getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(String weatherID) {
        this.weatherID = weatherID;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public double getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(double mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getRain3h() {
        return rain3h;
    }

    public void setRain3h(String rain3h) {
        this.rain3h = rain3h;
    }

    public String getSnow3h() {
        return snow3h;
    }

    public void setSnow3h(String snow3h) {
        this.snow3h = snow3h;
    }

    public String getSysSunset() {
        return sysSunset;
    }

    public void setSysSunset(String sysSunset) {
        this.sysSunset = sysSunset;
    }

    public String getSysSunrise() {
        return sysSunrise;
    }

    public void setSysSunrise(String sysSunrise) {
        this.sysSunrise = sysSunrise;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

}
