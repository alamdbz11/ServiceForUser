package com.alam.serviceforuser;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alam.serviceforuser.data.DataPreference;
import com.alam.serviceforuser.data.GlobalData;
import com.alam.serviceforuser.services.AddressLocation;
import com.alam.serviceforuser.services.LocationTrack;
import com.alam.serviceforuser.utils.Loading;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplainActivity extends AppCompatActivity {
    private LocationTrack locationTrack;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int REQ_CODE_SPEECH_INPUT = 102;
    private String photo = "",ComplainType="";
    private ImageView imgCapture,imageViewLoading;
    private Bitmap theImage;
    private AppCompatActivity mContext;
    private Spinner spnProblemType;
    private EditText edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        uiInitialize();
    }

    private void uiInitialize() {
        mContext = this;
        locationTrack = new LocationTrack(mContext);
        spnProblemType = findViewById(R.id.spnProblemType);
        edtDescription = findViewById(R.id.edtDescription);
        imgCapture = findViewById(R.id.imgCapture);
        imageViewLoading = findViewById(R.id.imageViewLoading);

        spnProblemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ComplainType= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void openVoice(View view) {
        askSpeechInput("hi");

    }

    private void askSpeechInput(String mgs) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "bn");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                mgs);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Log.i(GlobalData.TAG, "Voice ERROR: " + a.toString());
        }
    }

    public void btnChooseFile(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }

    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }

    @Override
    protected void onActivityResult(int req, int result, Intent data) {
        super.onActivityResult(req, result, data);
        if (req == CAMERA_REQUEST_CODE && result == Activity.RESULT_OK) {
            theImage = (Bitmap) data.getExtras().get("data");
            imgCapture.setImageBitmap(theImage);
            imgCapture.setVisibility(View.VISIBLE);
            photo = getEncodedString(theImage);
        }else if (req == REQ_CODE_SPEECH_INPUT) {
            if (null != data) {
                ArrayList<String> res = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String currentString = res.get(0).trim();

                edtDescription.setText(currentString);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationTrack.canGetLocation()) {


            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            Log.i(GlobalData.TAG, "Lat: " + latitude);
            Log.i(GlobalData.TAG, "Lon: " + longitude);

            new DataPreference(mContext).setLatitude(latitude + "");
            new DataPreference(mContext).setLongitude(longitude + "");
            //  Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void submitComplaint(View v) {

        Loading.showLoadingImage(mContext, imageViewLoading);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalData.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Loading.hideLoadingImage(mContext, imageViewLoading);

                        Log.e(GlobalData.TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                            JSONArray contacts = jsonObject.getJSONArray("data");
                            JSONObject c = contacts.getJSONObject(0);
                            String Success = c.getString("Success");
                            if (Success.equals("1")) {

                               // Toast.makeText(mContext, getResources().getString(R.string.mgs_reg_success), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } catch (JSONException e) {
                            Loading.hideLoadingImage(mContext, imageViewLoading);
                            Log.e(GlobalData.TAG, "" + e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Loading.hideLoadingImage(mContext, imageViewLoading);
                        Log.e(GlobalData.TAG, "" + error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("method", "entryComplaint");
                params.put("ComplainType", ComplainType);
                params.put("Name", new DataPreference(mContext).getName());
                params.put("Address", new DataPreference(mContext).getCurrentAddress());
                params.put("Email", new DataPreference(mContext).getEmail());
                params.put("Number1", new DataPreference(mContext).getSimOneNumber());
                params.put("Number2", new DataPreference(mContext).getSimTwoNumber());
                params.put("DeviceId", new DataPreference(mContext).getToken());
                params.put("Complaint", edtDescription.getText().toString());
                params.put("ImageData", photo);
                params.put("Latitude", new DataPreference(mContext).getLatitude());
                params.put("Longitude", new DataPreference(mContext).getLongitude());
                params.put("ComplaintAddress", AddressLocation.getCompleteAddressString(mContext,Double.parseDouble(new DataPreference(mContext).getLatitude()),Double.parseDouble(new DataPreference(mContext).getLongitude())));

                Log.i(GlobalData.TAG, params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
