package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by sansriti on 12-08-2018.
 */

public class Adapter extends ArrayAdapter<Word> {
    Context mContext;
    ArrayList array;
    int magnitudeColorResourceId;
    public Adapter(Context context,ArrayList arrayList){
        super(context,0,arrayList);
        mContext=context;
        array=arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view1=convertView;
        if(view1==null){
            view1= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        final Word word = getItem(position);
        double magnitude=word.getMangnitude();
        DecimalFormat formater = new DecimalFormat("0.00");
        String mag1=formater.format(magnitude);
        String area=word.getArea();
        Long date=word.getDate();
        TextView mag = (TextView)view1.findViewById(R.id.magnitude);
        mag.setText(mag1);
        int magnitude2=(int)Math.floor(magnitude);
        switch (magnitude2){
            case 0:
            case 1:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude9);
                Log.i(this.getClass().getName(), "color is"+magnitudeColorResourceId);
                break;
            default:
                magnitudeColorResourceId = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }
       GradientDrawable gradientDrawable = (GradientDrawable)mag.getBackground();
        gradientDrawable.setColor(magnitudeColorResourceId);
        TextView Area = (TextView)view1.findViewById(R.id.location1);
        String[] Area_array =area.split(",");
        String area1=Area_array[0];
        Area.setText(area1);
        if(Area_array.length==2){
            Area.setText(Area.getText()+",");
        String area2=Area_array[1];
        TextView Area2 = (TextView) view1.findViewById(R.id.location2);
        Area2.setText(area2);}
        TextView Date = (TextView)view1.findViewById(R.id.date);
        String date_string=Formatdate(date);
        TextView time=(TextView)view1.findViewById(R.id.time);
        String time_string=FormatTime(date);
        String[] Date_array=date_string.split(",");
        Date.setText(Date_array[0]+","+Date_array[1]);
        time.setText(time_string);
        return view1;
    }
    String Formatdate(Long date){
        Date date_obj = new Date(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLL dd,yyyy");
        return simpleDateFormat.format(date_obj);
    }
    String FormatTime(Long date){
        Date date_obj = new Date(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(date_obj);
    }
}
