package com.alam.serviceforuser.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.alam.serviceforuser.R;


public class ExistApplication {


    public ExistApplication(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(activity.getResources().getString(R.string.close_application))
                .setMessage(activity.getResources().getString(R.string.exit_from_application))
                .setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        activity.finishAffinity();
                        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }

                })
                .setNegativeButton(activity.getResources().getString(R.string.no), null)
                .show();
    }
}
