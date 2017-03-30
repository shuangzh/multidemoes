package com.shuangzh.dao.mybatis.domain;

/**
 * Created by admin on 2017/3/9.
 */
public class PhoneNumber {
    private String countryConde;
    private String stateCode;
    private String number;

    public String getCountryConde() {
        return countryConde;
    }

    public void setCountryConde(String countryConde) {
        this.countryConde = countryConde;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneNumber(String countryCode, String stateCode, String number) {
        this.countryConde = countryCode;
        this.stateCode = stateCode;
        this.number = number;
    }

    public PhoneNumber(String string) {
        if (string != null) {
            String[] parts = string.split("-");
            if (parts.length > 0) this.countryConde = parts[0];
            if (parts.length > 1) this.stateCode = parts[1];
            if (parts.length > 2) this.number = parts[2];
        }
    }

    public  String getAsString() {
        return countryConde +"-" + stateCode+"-"+number;
    }

}
