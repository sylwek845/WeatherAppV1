package prosoft.weatherv1;

/**
 * Created by Sylwester Zalewski on 10/12/2015.
 * Class is used to pass data between different classes (e.g new intent)
 * Class does not need constructor
 * All methods and variables are static
 */
public class DataExchanger {

    private static WeatherData[] weatherDatas = null;

    /**
     * Get Element position
     * @return int with position
     */
    public static int getElement() {
        return element;
    }

    /**
     * Used to set position (element 0 is first element of array)
     * @param element - positive number
     */
    public static void setElement(int element) {
        DataExchanger.element = element;
    }

    private static int element = -999;

    public static boolean isCelsius() {
        return Celsius;
    }

    public static void setCelsius(boolean celsius) {
        Celsius = celsius;
    }

    public static boolean isMph() {
        return mph;
    }

    public static void setMph(boolean mph) {
        DataExchanger.mph = mph;
    }

    private static boolean Celsius = true;
    private static boolean mph = true;


    /**
     * Public method used to get WeatherData array
     * @return WeatherData[] array
     */
    public static WeatherData[] getWeatherDatas() {
        return weatherDatas;
    }

    /**
     * Public method to set Array of WeatherData class
     * @param weatherDatas array of weaherData[].
     */
    public static void setWeatherDatas(WeatherData[] weatherDatas) {
        DataExchanger.weatherDatas = weatherDatas;
    }

    /**
     * Reset variables to clean memory (Element is set to -999)
     */
    public static void Reset()
    {
        weatherDatas = null;
        element = -999;
    }




}
