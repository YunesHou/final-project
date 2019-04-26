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
        final String origin = intent.getStringExtra("From");
        final String destination = intent.getStringExtra("To");
        final String date = intent.getStringExtra("Date");

        Button start = findViewById(R.id.start);
        final TextView QuoteId = findViewById(R.id.QuoteId);
        final TextView MinPrice = findViewById(R.id.MinPrice);
        final TextView CarrierId = findViewById(R.id.CarrierId);
        final TextView Name = findViewById(R.id.Name);
        final TextView CountryName = findViewById(R.id.CountryName);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startAPICall("US", "USD", "en-US", origin, destination, date, "2019-10-01");
                QuoteId.setVisibility(View.VISIBLE);
                MinPrice.setVisibility(View.VISIBLE);
                CarrierId.setVisibility(View.VISIBLE);
                Name.setVisibility(View.VISIBLE);
                CountryName.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.back).setOnClickListener(v -> {
            Intent setupIntent = new Intent(this, MainActivity.class);
            startActivity(setupIntent);
            finish();
        });
    }

    protected void onPause() {
        super.onPause();
    }


    void startAPICall(final String country, final String currency, final String locale, final String originplace, final String destinationplace, final String outboundpartialdate,final String inboundpartialdate) {
        Intent intent = getIntent();
        final String origin = intent.getStringExtra("From");
        final String destination = intent.getStringExtra("To");
        final String date = intent.getStringExtra("Date");
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/" + country + "/" + currency + "/" + locale + "/" + origin + "/" + destination + "/" + date + "?" + "inboundpartialdate=2019-10-01";
        //String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/{country}/{currency}/{locale}/{originplace}/{destinationplace}/{outboundpartialdate}";

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
            TextView QuoteIdText = findViewById(R.id.QuoteId);
            TextView MinPriceText = findViewById(R.id.MinPrice);
            TextView CarrierIdText = findViewById(R.id.CarrierId);
            TextView NameText = findViewById(R.id.Name);
            TextView CountryNameText = findViewById(R.id.CountryName);


            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(response).getAsJsonObject();

            final ArrayList flightQuotes = new ArrayList<>();
            JsonArray quotes = jsonObj.getAsJsonArray("Quotes");
            for (int i = 0; i < quotes.size(); i++) {
                JsonObject a = quotes.get(i).getAsJsonObject();
                String QuoteId = a.get("QuoteId").getAsString();
                String MinPrice = a.get("MinPrice").getAsString();

                flightQuotes.add(QuoteId);
                flightQuotes.add(MinPrice);
            }
            System.out.println(flightQuotes);
            QuoteIdText.setText("Quote ID: "+ flightQuotes.get(0));
            MinPriceText.setText("MinPrice: "+ flightQuotes.get(1));

            final ArrayList flightCarriers = new ArrayList<>();
            JsonArray Carriers = jsonObj.getAsJsonArray("Carriers");
            for (int i = 0; i < Carriers.size(); i++) {
                JsonObject a = Carriers.get(i).getAsJsonObject();
                String CarrierId = a.get("CarrierId").getAsString();
                String Name = a.get("Name").getAsString();

                flightCarriers.add(CarrierId);
                flightCarriers.add(Name);
            }
            CarrierIdText.setText("Carrier ID: "+ flightCarriers.get(0));
            NameText.setText("Name: "+ flightCarriers.get(1));
        } catch (final Exception e) {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
