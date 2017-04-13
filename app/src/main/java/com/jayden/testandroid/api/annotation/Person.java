package com.jayden.testandroid.api.annotation;

/**
 * Created by Jayden on 2017/3/25.
 */

public class Person {
    @Seven(value = "jayden")
    private String name;

    private String Property;

    public Person(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Seven(Property = "凤凰创壹研发部")
    public void setProperty(String property) {
        Property = property;
    }

    public String getProperty() {
        return Property;
    }
}
