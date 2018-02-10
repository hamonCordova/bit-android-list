package com.blackscreen.listbitcoinvalue.model;

/**
 * Created by Hamon on 08/02/2018.
 */

public class BitcoinValueModel {

    private String value;
    private String description;

    public BitcoinValueModel(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
