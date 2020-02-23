package com.alam.serviceforuser.services;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.alam.serviceforuser.data.GlobalData;


import java.util.List;
import java.util.Locale;

public class AddressLocation {

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }

                strAdd = strReturnedAddress.toString();



            } else {
                Log.w(GlobalData.TAG, "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(GlobalData.TAG, "Canont get Address!");
            Log.e(GlobalData.TAG, e.toString());
        }
        return strAdd;
    }
}
