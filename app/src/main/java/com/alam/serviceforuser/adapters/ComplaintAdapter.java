package com.alam.serviceforuser.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alam.serviceforuser.ComplaintDetailActivity;
import com.alam.serviceforuser.R;
import com.alam.serviceforuser.data.GlobalData;
import com.alam.serviceforuser.models.Complaint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

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
    public void onBindViewHolder(final MemberViewHolder holder, final int position) {
         ArrayList<String> pictures = new ArrayList<>();
        final Complaint model=complaints.get(position);

        String p1 = complaints.get(position).getUrlPhoto();
        String p2 = complaints.get(position).getUrlPhoto2();
        String p3 = complaints.get(position).getUrlPhoto3();

        if (p1.length() > 10)
            pictures.add(p1);
         if (p2.length() > 10)
             pictures.add(p2);
         if (p3.length() > 10)
             pictures.add(p3);
//        Log.i(GlobalData.TAG,GlobalData.PHOTO_URL + photoUrl);

        final SliderAdapterExample adapter = new SliderAdapterExample(activity, pictures, model);
        //  adapter.setCount(5);

        holder.sliderView.setSliderAdapter(adapter);
        holder.sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        holder.sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        // holder.sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        holder.sliderView.setIndicatorSelectedColor(Color.WHITE);
        holder.sliderView.setIndicatorUnselectedColor(Color.GRAY);
        holder.sliderView.setAutoCycle(false);


        holder.sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                holder.sliderView.setCurrentPagePosition(position);
            }
        });



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComplaintDetailActivity.class);
                intent.putExtra("model", model);
                activity.startActivity(intent);

            }
        });

        holder.txtComId.setText(activity.getResources().getString(R.string.complaint_no) + " " + complaints.get(position).getId());
        holder.txtProblem.setText(activity.getResources().getString(R.string.type_of_complaint_label) + ": " + complaints.get(position).getComplainType());
        holder.txtStatus.setText(complaints.get(position).getStatus());
        holder.txtDate.setText(complaints.get(position).getEntryDate());

    }

    @Override
    public int getItemCount() {

        return complaints.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView compliantPhoto;

        TextView txtComId,txtDescription, txtProblem,txtDate,txtStatus;
        LinearLayout linearLayout;
        SliderView sliderView;
        public MemberViewHolder(View itemView) {
            super(itemView);
            txtComId =itemView.findViewById(R.id.txtComId);
            compliantPhoto = (ImageView) itemView.findViewById(R.id.compliantPhoto);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtProblem = itemView.findViewById(R.id.txtProblem);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            sliderView = itemView.findViewById(R.id.imageSlider);
            linearLayout = itemView.findViewById(R.id.items);
        }
    }
}
