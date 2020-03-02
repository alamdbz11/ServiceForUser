package com.alam.serviceforuser;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alam.serviceforuser.data.GlobalData;
import com.alam.serviceforuser.models.Complaint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ComplaintDetailActivity extends AppCompatActivity {

    private AppCompatActivity mContext;
    private Complaint model;
    private TextView txtComplaintNo, txtName, txtPhone, txtAddress, txtDate, txtComplaint;
    private ImageView photo1, photo2, photo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);
        setToolBar();
        uiInitialize();
        setData();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        //setting the title
        toolbar.setTitle(getResources().getString(R.string.complaint_details_title));
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setData() {
        txtComplaintNo.setText(getResources().getString(R.string.complaint_no) + " " + model.getId());
        txtComplaint.setText(model.getComplaint());
        txtName.setText(getResources().getString(R.string.name) + ": " + model.getName());
        txtDate.setText(model.getEntryDate());

        String photoUrl = "";
        String p1 = model.getUrlPhoto();
        String p2 = model.getUrlPhoto2();
        String p3 = model.getUrlPhoto3();

        if (p1.length() > 10) {
            photo1.setVisibility(View.VISIBLE);
            Glide.with(this).load(GlobalData.PHOTO_URL + p1)
                    // .override(StaticConfig.THUMBNAIL_SIZE, StaticConfig.THUMBNAIL_SIZE)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(photo1);

        }
        if (p2.length() > 10) {
            photo2.setVisibility(View.VISIBLE);
            Glide.with(this).load(GlobalData.PHOTO_URL + p2)
                    // .override(StaticConfig.THUMBNAIL_SIZE, StaticConfig.THUMBNAIL_SIZE)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(photo1);

        }
        if (p3.length() > 10) {
            photo3.setVisibility(View.VISIBLE);
            Glide.with(this).load(GlobalData.PHOTO_URL + p3)
                    // .override(StaticConfig.THUMBNAIL_SIZE, StaticConfig.THUMBNAIL_SIZE)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(photo3);

        }

    }

    private void uiInitialize() {
        mContext = this;
        model = (Complaint) getIntent().getSerializableExtra("model");
        txtComplaintNo = findViewById(R.id.txtNo);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtDate = findViewById(R.id.txtDate);
        txtComplaint = findViewById(R.id.txtComplaint);
        photo1 = findViewById(R.id.compliantPhoto1);
        photo2 = findViewById(R.id.compliantPhoto2);
        photo3 = findViewById(R.id.compliantPhoto3);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            // finish the activity
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
