package com.alam.serviceforuser.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alam.serviceforuser.ComplaintDetailActivity;
import com.alam.serviceforuser.R;
import com.alam.serviceforuser.data.GlobalData;
import com.alam.serviceforuser.models.Complaint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private ArrayList<String> pictures = new ArrayList<>();
    private String propertyId = "";
    private Complaint model;

    public SliderAdapterExample(Context context, ArrayList<String> pictures, Complaint model) {
        this.context = context;
        this.pictures = pictures;
        this.model = model;
    }

    public void setCount(int count) {
        int mCount = pictures.size();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return pictures.size();
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ComplaintDetailActivity.class);
                intent.putExtra("model", model);
                context.startActivity(intent);

                //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(context).load(GlobalData.PHOTO_URL + pictures.get(position))
               // .override(StaticConfig.THUMBNAIL_SIZE, StaticConfig.THUMBNAIL_SIZE)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(viewHolder.imageViewBackground);

    }


     class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

         SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
