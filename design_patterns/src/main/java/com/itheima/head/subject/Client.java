package com.itheima.head.subject;

/**
 * @author wing
 * @create 2024/5/17
 */
public class Client {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay conditionsDisplay =
                new CurrentConditionsDisplay(weatherData);
        CurrentConditionsDisplay2 conditionsDisplay2 =
                new CurrentConditionsDisplay2(weatherData);
        weatherData.setMeasurements(80,65,30.4F);
        weatherData.setMeasurements(70,65,30.4F);
    }
}
