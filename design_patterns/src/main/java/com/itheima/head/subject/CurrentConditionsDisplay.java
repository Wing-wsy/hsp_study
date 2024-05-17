package com.itheima.head.subject;

/**
 * @author wing
 * @create 2024/5/17
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement{
    private float temperature;
    private float humidity;
    private WeatherData weatherData;
    @Override
    public void display() {
        System.out.println("CurrentConditionsDisplay: " + temperature + "," + humidity);
    }
    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    @Override
    public void update() {
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        display();
    }
}
