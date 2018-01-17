package com.koudai.operate.model;

/**
 * Created by admin on 2016/9/14.
 */
public class CityBean {
    private String city_id;
    private String name;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
