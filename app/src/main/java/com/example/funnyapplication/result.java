package com.example.funnyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class result extends AppCompatActivity {
    private static final String TAG = "FunnyApplication:Main";

    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        Button start = findViewById(R.id.start);
        final TextView PlaceId = findViewById(R.id.PlaceId);
        final TextView PlaceName = findViewById(R.id.PlaceName);
        final TextView CountryId = findViewById(R.id.CountryId);
        final TextView CityId = findViewById(R.id.CityId);
        final TextView CountryName = findViewById(R.id.CountryName);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startAPICall("US", "USD", "en-US", "LAX-sky", "SFO-sky", "2019-01-01");
                PlaceId.setVisibility(View.VISIBLE);
                PlaceName.setVisibility(View.VISIBLE);
                CountryId.setVisibility(View.VISIBLE);
                CityId.setVisibility(View.VISIBLE);
                CountryName.setVisibility(View.VISIBLE);
            }
        });
    }

    protected void onPause() {
        super.onPause();
    }


    void startAPICall(final String country, final String currency, final String locale, final String destinationplace, final String originplace, final String outboundpartialdate) {
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/GBP/en-GB/?query=Stockholm";

        final ArrayList<HashMap<String, String>> flightlist = new ArrayList<>();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        apiCallDone(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Debug", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-RapidAPI-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "9b857f71d3msh9140ba791ab214ap16f3c1jsn74ae2d471f73");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    void apiCallDone(final String response) {
        try {
            TextView PlaceIdText = findViewById(R.id.PlaceId);
            TextView PlaceNameText = findViewById(R.id.PlaceName);
            TextView CountryIdText = findViewById(R.id.CountryId);
            TextView CountryNameText = findViewById(R.id.CountryName);
            TextView CityIdText = findViewById(R.id.CityId);

            final ArrayList flightlist = new ArrayList<>();

            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(response).getAsJsonObject();

            //JSONObject jsonObj = new JSONObject();
            JsonArray resultArray = jsonObj.getAsJsonArray("Places");
            for (int i = 0; i < resultArray.size(); i++) {
                JsonObject a = resultArray.get(i).getAsJsonObject();
                String PlaceId = a.get("PlaceId").getAsString();
                String PlaceName = a.get("PlaceName").getAsString();
                String CountryId = a.get("CountryId").getAsString();
                //String RegionId = a.get("RegionId").getAsString();
                String CityId = a.get("CityId").getAsString();
                String CountryName = a.get("CountryName").getAsString();

                //HashMap<String, String> flight = new HashMap<>();

                //flight.put("PlaceId", PlaceId);
                //flight.put("PlaceName", PlaceName);
                //flight.put("CountryId", CountryId);
                //flight.put("RegionId", RegionId);
                //flight.put("CityId", CityId);
                //flight.put("CountryName", CountryName);

                flightlist.add(PlaceId);
                flightlist.add(PlaceName);
                flightlist.add(CountryId);
                //flightlist.add(RegionId);
                flightlist.add(CityId);
                flightlist.add(CountryName);
            }
            for (int i = 0; i < flightlist.size(); i++) {
                PlaceIdText.setText("Place ID: "+ flightlist.get(0));
                PlaceNameText.setText("Place name: "+ flightlist.get(1));
                CountryIdText.setText("Country ID: "+ flightlist.get(2));
                CityIdText.setText("City ID: "+ flightlist.get(3));
                CountryNameText.setText("Country Name: "+ flightlist.get(4));

            }
            //result.setText("Response is: "+ flightlist);
        } catch (final Exception e) {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
