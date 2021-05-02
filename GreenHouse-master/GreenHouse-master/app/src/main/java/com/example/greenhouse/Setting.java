package com.example.greenhouse;

public class Setting {
    private Integer id;
    private Integer maxHumidiy;
    private Integer minHumidiy;
    private Float maxTemperature;
    private Float minTemperature;

    public Setting() {

    }

    public Setting(Integer id, Integer maxHumidiy, Integer minHumidiy, Float maxTemperature, Float minTemperature) {
        this.id = id;
        this.maxHumidiy = maxHumidiy;
        this.minHumidiy = minHumidiy;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxHumidiy() {
        return maxHumidiy;
    }

    public void setMaxHumidiy(Integer maxHumidiy) {
        this.maxHumidiy = maxHumidiy;
    }

    public Integer getMinHumidiy() {
        return minHumidiy;
    }

    public void setMinHumidiy(Integer minHumidiy) {
        this.minHumidiy = minHumidiy;
    }

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }
}
