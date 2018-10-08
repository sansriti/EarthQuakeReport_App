package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by sansriti on 26-08-2018.
 */

class Alternate extends AsyncTaskLoader<List<Word>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    String USGS= "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public Alternate(Context context)
    {
        super(context);
    }

    @Override
    protected void onStartLoading()
    { Log.i(LOG_TAG , "onStartLoading");
        forceLoad();
    }

    public List<Word> loadInBackground() {
        Log.i(LOG_TAG , "loadInBackground");
        List<Word> result = QueryUtils.fetchEarthquakeData(USGS);
        return result;
    }

}