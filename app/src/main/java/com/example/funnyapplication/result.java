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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

        final String country;
        final String currency;
        final String locale;
        final String destinationplace;
        final String originplace;
        final String outboundpartialdate;


        Intent intent = getIntent();

        Button start = findViewById(R.id.start);
        final TextView result = findViewById(R.id.result);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startAPICall("US", "USD", "en-US", "LAX-sky", "SFO-sky", "2019-01-01");
                result.setVisibility(View.VISIBLE);
            }
        });
    }

    protected void onPause() {
        super.onPause();
    }


    void startAPICall(final String country, final String currency, final String locale, final String destinationplace, final String originplace, final String outboundpartialdate) {
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/GBP/en-GB/?query=Stockholm";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView result = findViewById(R.id.result);
                        // Display the first 500 characters of the response string.
                        result.setText("Response is: "+ response.substring(0,500));
                        Log.d("Debug", response);
                        //Log.i(TAG, response);
                        //result.setText("response");
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
    }
}
