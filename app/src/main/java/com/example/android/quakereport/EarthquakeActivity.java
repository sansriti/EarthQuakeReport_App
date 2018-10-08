/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    Adapter adapter;
    TextView emptyText;
    ProgressBar progressBar;
    ListView earthquakeListView;
    ArrayList<Word> earthquakes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.earthquake_activity);
         earthquakeListView = (ListView) findViewById(R.id.list);
         emptyText = (TextView)findViewById(R.id.empty);
        earthquakeListView.setEmptyView(emptyText);
        // Create a new {@link ArrayAdapter} of earthquakes
         adapter = new Adapter(getApplication(), new ArrayList<Word>());
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Word currentEarthquake = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        getLoaderManager().initLoader(1, null, this);
        Log.i(LOG_TAG , "onCreate:initLoader ");
      }

    @Override
    public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG , ":onCreateLoader ");
        return new Alternate(EarthquakeActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> words) {
        adapter.clear();
        Log.i(LOG_TAG , "onLoadFinishesd ");
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (words != null && !words.isEmpty()) {
            adapter.addAll(words);
        }
        progressBar=(ProgressBar)findViewById(R.id.spinner);
        progressBar.setVisibility(View.GONE);
        emptyText.setText("No Earthquake Available");
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        Log.i(LOG_TAG , "onLoadReset");
       // adapter.clear();
    }

    // Create a fake list of earthquake locations.
   }





