package com.alam.serviceforuser.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alam.serviceforuser.R;
import com.alam.serviceforuser.data.GlobalData;
import com.alam.serviceforuser.models.Complaint;
import com.alam.serviceforuser.utils.CircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class ComplaintAdapter  extends RecyclerView.Adapter<ComplaintAdapter.MemberViewHolder> {
    private Activity activity;
    private List<Complaint> complaints = new ArrayList<>();

    public ComplaintAdapter(Activity activity, List<Complaint> complaints) {
        this.activity = activity;
        this.complaints = complaints;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_complaint, parent, false);
        MemberViewHolder holder = new MemberViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {

        Log.i(GlobalData.TAG,GlobalData.PHOTO_URL + complaints.get(position).getId() + ".jpg");
        Glide.with(activity).load(GlobalData.PHOTO_URL + complaints.get(position).getId() + ".jpg")
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .thumbnail(0.5f)
               // .bitmapTransform(new CircleTransform(activity))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.compliantPhoto);




        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // members.add(new Member("SSA", "sadd", "asxd", "asdsa", "ASx", "sad"));
//                Complaint member = members.get(position);
//                Intent intent = new Intent(activity, MemberProfileActivity.class);
//                intent.putExtra("Member", member);
//                activity.startActivity(intent);

            }
        });

        holder.txtProblem.setText(complaints.get(position).getComplainType());
        holder.txtStatus.setText(complaints.get(position).getStatus());
        holder.txtDate.setText(complaints.get(position).getEntryDate());
        holder.txtDescription.setText(complaints.get(position).getComplaint());

    }

    @Override
    public int getItemCount() {

        return complaints.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView compliantPhoto;

        TextView txtDescription, txtProblem,txtDate,txtStatus;
        LinearLayout linearLayout;

        public MemberViewHolder(View itemView) {
            super(itemView);
            compliantPhoto = (ImageView) itemView.findViewById(R.id.compliantPhoto);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtProblem = itemView.findViewById(R.id.txtProblem);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            linearLayout = itemView.findViewById(R.id.items);
        }
    }
}
