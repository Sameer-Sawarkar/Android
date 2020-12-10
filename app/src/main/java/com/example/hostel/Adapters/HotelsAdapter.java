package com.example.hostel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hostel.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.hostel.HotelViewer;
import com.example.hostel.Util.HotelView;


public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<HotelView> hotelList;
    private List<HotelView> hotelListAll;
    public HotelView hotel;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<HotelView> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(hotelList);
            }else{
                for(HotelView h: hotelList){
                    if(h.name.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(h);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //run on ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            hotelList.clear();
            hotelList.addAll((Collection<? extends HotelView>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, location,rating,features;
        public ImageView thumbnail, overflow;
        public Button viewbutton;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            location = (TextView) view.findViewById(R.id.location);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            rating =  view.findViewById(R.id.rating);
            features = view.findViewById(R.id.features);
            viewbutton = view.findViewById(R.id.viewbutton);
        }
    }


    public HotelsAdapter(Context mContext, List<HotelView> hotelList, List<HotelView> hotelListAll) {
        this.mContext = mContext;
        this.hotelList = hotelList;
        this.hotelListAll = new ArrayList<>(hotelListAll);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        hotel = hotelList.get(position);
        holder.title.setText(hotel.getName());
        holder.location.setText("Location: "+hotel.getLocation());
        holder.rating.setText("Ratings: "+hotel.getRating());
        holder.features.setText("Features: "+hotel.getFeatures());
        // loading album cover using Glide library
        Glide.with(mContext).load(hotel.getThumbnail()).into(holder.thumbnail);

        holder.viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HotelViewer.class);
                intent.putExtra("hotelname",holder.title.getText().toString());
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return hotelList.size();
    }

}