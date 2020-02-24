package com.alam.serviceforuser;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alam.serviceforuser.data.DataPreference;

import java.util.List;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 200;
    private static final boolean TODO = true;
    private AppCompatActivity mActivity;
    Pattern pattern;
    Account[] account;
    private EditText edtComplainerName, edtComplainerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiInitialize();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionAlreadyGranted()) {

                isSimAvailable();
                GetAccountsName();
            } else {
                checkPermission();
            }
        }

    }

    private void uiInitialize() {
        mActivity = this;
        pattern = Patterns.EMAIL_ADDRESS;

        edtComplainerName = findViewById(R.id.edtComplainerName);
        edtComplainerAddress = findViewById(R.id.edtComplainerAddress);
    }

    public void isSimAvailable() {
        SubscriptionManager mSubscriptionManager = SubscriptionManager.from(getBaseContext());
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        List<SubscriptionInfo> subscriptions = mSubscriptionManager.getActiveSubscriptionInfoList();

        if (subscriptions.size() == 1) {
            new DataPreference(mActivity).setSimOneNumber(subscriptions.get(0).getNumber());
        }
        if (subscriptions.size() == 2) {
            new DataPreference(mActivity).setSimOneNumber(subscriptions.get(0).getNumber());
            new DataPreference(mActivity).setSimTwoNumber(subscriptions.get(1).getNumber());
        }

    }

    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int result1 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE);
        int result3 = ContextCompat.checkSelfPermission(this, GET_ACCOUNTS);

        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                mActivity, Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(
                mActivity, READ_PHONE_STATE) + ContextCompat.checkSelfPermission(
                mActivity, GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Do something, when permissions not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.READ_PHONE_STATE) || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, Manifest.permission.GET_ACCOUNTS)) {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mActivity);
                builder.setMessage("Camera, Read Contacts and Write External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.GET_ACCOUNTS
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.GET_ACCOUNTS
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        } else {
            // Do something, when permissions are already granted
            //  Toast.makeText(mActivity,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if ((grantResults.length > 0) && (grantResults[0]
                        + grantResults[1]
                        + grantResults[2]
                        == PackageManager.PERMISSION_GRANTED
                )
                ) {
                    // Permissions are granted
                    Toast.makeText(mActivity, "Permissions granted.", Toast.LENGTH_SHORT).show();
                    isSimAvailable();
                    GetAccountsName();
                } else {
                    // Permissions are denied
                    Toast.makeText(mActivity, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void GetAccountsName() {

        try {
            account = AccountManager.get(MainActivity.this).getAccounts();
        } catch (SecurityException e) {

        }

        for (Account TempAccount : account) {
            if (pattern.matcher(TempAccount.name).matches()) {
                new DataPreference(mActivity).setEmail(TempAccount.name);
            }
        }

    }

    public void btnLogin(View view) {

        new DataPreference(mActivity).setName(edtComplainerName.getText().toString());
        new DataPreference(mActivity).setCurrentAddress(edtComplainerAddress.getText().toString());
        Intent intent = new Intent(this, ComplainActivity.class);
        startActivity(intent);
    }
}
