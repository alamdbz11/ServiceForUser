package com.alam.serviceforuser.data;

import android.content.Context;
import android.content.SharedPreferences;

public class DataPreference {
    private static final String PREF_NAME = "ACDU";
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    public DataPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }


    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void setToken(String token) {
        editor.putString("token", token);
        editor.apply();

    }
    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.apply();

    }


    public String getEmail() {
        return sharedPreferences.getString("email", "");
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.apply();

    }
    public String getSimOneNumber() {
        return sharedPreferences.getString("sim1", "");
    }

    public void setSimOneNumber(String sim1) {
        editor.putString("sim1", sim1);
        editor.apply();

    }
    public String getSimTwoNumber() {
        return sharedPreferences.getString("sim2", "");
    }

    public void setSimTwoNumber(String sim2) {
        editor.putString("sim2", sim2);
        editor.apply();

    }

    public String getCurrentAddress() {
        return sharedPreferences.getString("address", "");
    }

    public void setCurrentAddress(String address) {
        editor.putString("address", address);
        editor.apply();

    }

    public String getLatitude() {
        return sharedPreferences.getString("Latitude", "0.0");
    }

    public void setLatitude(String Latitude) {
        editor.putString("Latitude", Latitude);
        editor.apply();

    }

    public String getLongitude() {
        return sharedPreferences.getString("Longitude", "0.0");
    }

    public void setLongitude(String Longitude) {
        editor.putString("Longitude", Longitude);
        editor.apply();

    }
}
