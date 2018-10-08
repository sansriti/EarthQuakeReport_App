package com.example.android.quakereport;

/**
 * Created by sansriti on 12-08-2018.
 */

public class Word {
   private String area1;
   private Long date1;
   private  double mag1;
   private String url;
    Word(double mag,String area,Long date,String url1){
        mag1=mag;
        area1=area;
        date1=date;
        url=url1;
    }
    public String getUrl(){return url;}
    public double getMangnitude(){return mag1;}
    public String getArea(){return area1;}
    public Long getDate(){return date1;}
}
