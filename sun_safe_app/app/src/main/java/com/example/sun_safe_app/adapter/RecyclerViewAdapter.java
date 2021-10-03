package com.example.sun_safe_app.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sun_safe_app.MainActivity;
import com.example.sun_safe_app.R;
import com.example.sun_safe_app.databinding.RvLayoutBinding;
import com.example.sun_safe_app.room.entity.EventRecord;
import com.example.sun_safe_app.ui.activityPlan.ActivityFragmentDialog;
import com.example.sun_safe_app.ui.map.GoogleMapsFragmentDialog;
import com.example.sun_safe_app.utils.DatePickerDialog;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {
    private List<EventRecord> painRecords;
    private MainActivity parentActivity;
    public RecyclerViewAdapter(List<EventRecord> painRecords, MainActivity parentActivity) {
        for (int i = painRecords.size()-1; i >= 0; i--) {
            String completeEndDate = painRecords.get(i).date + " " + painRecords.get(i).end_time;
            Date endDate = null;
            String format = "yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                endDate= sdf.parse(completeEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (endDate.getTime()<= System.currentTimeMillis()){
                painRecords.remove(i);
            }
        }
        this.painRecords = painRecords;
        this.parentActivity = parentActivity;
    }
    //This method creates a new view holder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutBinding binding= RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder
                                         viewHolder, int position) {
        if(position > painRecords.size() - 1) {
            viewHolder.binding.latestContentLayout.setVisibility(View.INVISIBLE);
            return;
        }
        final EventRecord eventRecord = painRecords.get(position);
        viewHolder.binding.adviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag",eventRecord.uid + "");
            }
        });
        if (eventRecord.highest_uvi == -1){
            viewHolder.binding.highestUV.setText("Highest UV: Unknown");
            viewHolder.binding.highestUVHour.setVisibility(View.GONE);
            viewHolder.binding.highestUVNumber.setVisibility(View.GONE);
            viewHolder.binding.rightSideLayout.setBackgroundColor(0xFF4B4B4B);

        }
        else{
            if (eventRecord.highest_uvi < 2.5){
                viewHolder.binding.highestUV.setText("Low peak UVI");
                viewHolder.binding.highestUVNumber.setText(new BigDecimal(eventRecord.highest_uvi).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFF4CAF50);
            }
            else  if (eventRecord.highest_uvi < 5.5){
                viewHolder.binding.highestUV.setText("Medium peak UVI");
                viewHolder.binding.highestUVNumber.setText(new BigDecimal(eventRecord.highest_uvi).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFFEDB200);

            }
            else  if (eventRecord.highest_uvi < 7.5){
                viewHolder.binding.highestUV.setText("High peak UVI");
                viewHolder.binding.highestUVNumber.setText(new BigDecimal(eventRecord.highest_uvi).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFFE86C00);

            }
            else  if (eventRecord.highest_uvi < 10.5){
                viewHolder.binding.highestUV.setText("Very High peak UVI");
                viewHolder.binding.highestUVNumber.setText(new BigDecimal(eventRecord.highest_uvi).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFFFD3324);

            }
            else {
                viewHolder.binding.highestUV.setText("Extremely High peak UVI");
                viewHolder.binding.highestUVNumber.setText(new BigDecimal(eventRecord.highest_uvi).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
                viewHolder.binding.rightSideLayout.setBackgroundColor(0xFF9C27B0);
            }
        }
        viewHolder.binding.date.setText(eventRecord.date + "");
        viewHolder.binding.title.setText(eventRecord.title + "");
        viewHolder.binding.location.setText(eventRecord.address);
        viewHolder.binding.time.setText(eventRecord.start_time + "\n" + eventRecord.end_time);
        if ( !eventRecord.highest_uvi_hour.equals(""))
            viewHolder.binding.highestUVHour.setText("at " + eventRecord.highest_uvi_hour);

        String startDateString = eventRecord.date;
        String startTime = eventRecord.start_time;
        String endTime = eventRecord.end_time;
        String completeStartDate = startDateString + " " + startTime;
        String completeEndDate = startDateString + " " + endTime;
        Date startDate = null;
        Date endDate = null;
        Date currentDate = new Date(System.currentTimeMillis());//获取当前时间
        String format = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            startDate = sdf.parse(completeStartDate);
            endDate= sdf.parse(completeEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (endDate.getTime() <= currentDate.getTime()){
            viewHolder.binding.doing.setText("Done");
        }
        else if (endDate.getTime() > currentDate.getTime() && startDate.getTime() <= currentDate.getTime()){
            viewHolder.binding.doing.setText("Doing");
        }
        else {
            Date startDay = null;
            Date currentDateTrans = null;
            String format2 = "yyyy-MM-dd";
            SimpleDateFormat sdf2 = new SimpleDateFormat(format2);

            String currentDateString = sdf2.format(currentDate);

            try {
                startDay = sdf2.parse(startDateString);
                currentDateTrans = sdf2.parse(currentDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diff = startDay.getTime() - currentDateTrans.getTime();
            long nd = 1000 * 24 * 60 * 60;
            int day = (int) (diff/nd);
            if (day == 0){
                viewHolder.binding.doing.setText("Today");
            }
            else if (day == 1){
                viewHolder.binding.doing.setText("1 day");
            }
            else{
                viewHolder.binding.doing.setText(day + " days");
            }

        }

//        viewHolder.binding.adviceCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ActivityFragmentDialog myDialog = new ActivityFragmentDialog();
////                myDialog.show(parentActivity.getSupportFragmentManager(), null);
////                parentActivity.replaceFragment(null);
//
//            }
//        });

        Bundle bundle = new Bundle();
        bundle.putInt("uid",eventRecord.uid);
        bundle.putString("title",eventRecord.title);
        bundle.putString("date",eventRecord.date);
        bundle.putString("address",eventRecord.address);
        bundle.putString("startTime",eventRecord.start_time);
        bundle.putString("endTime",eventRecord.end_time);
        bundle.putString("dayLeft",viewHolder.binding.doing.getText().toString());
        bundle.putFloat("highestUV",eventRecord.highest_uvi);
        bundle.putString("highestUVHour",eventRecord.highest_uvi_hour);



        viewHolder.binding.adviceCard.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_navigation_activity_to_activityDataFragment, bundle));
    }
    // this method get the record size
    @Override
    public int getItemCount() {
        return painRecords.size() + 1;
    }

    // this class is the viewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}