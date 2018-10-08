package com.example.android.quakereport;

/**
 * Created by sansriti on 14-08-2018.
 */

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import android.util.Log;

import org.json.JSONArray;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
         static String JsonResponse;
    /** Sample JSON response for a USGS query */
    private static final String SAMPLE_JSON_RESPONSE = JsonResponse;
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     *
     */
    public static URL createUrl(String url){
        URL u=null;
        try {
            u = new URL(url);
        }
        catch (MalformedURLException e){
            //Log.e(Log_TAG, "createUrl: there is an exception"+e,e );
            return null;
        }
        return u;
    }
    public static String makeHTTPRequest(URL url){
        HttpsURLConnection httpsURLConnection = null;
        InputStream inputStream = null;
        JsonResponse="";
        if(url==null){
        }
        try {
            httpsURLConnection = (HttpsURLConnection)url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(1500);
            httpsURLConnection.setReadTimeout(1000);
            httpsURLConnection.connect();
            if(httpsURLConnection.getResponseCode()==200){inputStream = httpsURLConnection.getInputStream();

            JsonResponse=readInputStream(inputStream);
                }
            else {
               // Log.i(getClass().getName(), "makeHTTPRequest: there response code was not 200");
            }
        }
        catch (Exception e){
           // Log.e(getClass().getName(), "makeHTTPRequest: there is an exception"+e ,e );
        }
        finally {
            if(httpsURLConnection!=null)
            {
                httpsURLConnection.disconnect();
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    //Log.i(getClass().getName(), "makeHTTPRequest: this is an inputstream "+e);
                }

            }
        }return  JsonResponse;
    }

    public static String readInputStream(InputStream in) throws IOException{
        StringBuilder output = new StringBuilder();
        if (in != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public QueryUtils() {
    }

    /**
     * Return a list of {@link Word} objects that has been built up from
     * parsing a JSON response.
     */

    public static ArrayList<Word> extractEarthquakes() {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Word> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
             JSONObject jsonObject = new JSONObject(JsonResponse);
             JSONArray jsonArray = jsonObject.optJSONArray("features");
             for(int i=0;i<jsonArray.length();i++){
                 JSONObject c = jsonArray.getJSONObject(i);
                 JSONObject properties = c.getJSONObject("properties");
                 double mag = properties.getDouble("mag");
                 String location = properties.getString("place");
                 Long time = properties.getLong("time");
                 String url1=properties.getString("url");
                 Word w = new Word(mag,location,time,url1);
                 earthquakes.add(w);
             }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    public static List<Word> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        Log.i(EarthquakeActivity.class.getName() , "fetchEarthquakeData");
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (Exception e) {
            //Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Word> earthquakes = extractEarthquakes();

        // Return the list of {@link Earthquake}s
        return earthquakes;
    }

}

