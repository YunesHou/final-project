package com.example.funnyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import unirest.HttpResponse;
import unirest.JsonNode;
import unirest.Unirest;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FunnyApplication:Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/autosuggest/v1.0/UK/GBP/en-GB/?query=Stockholm";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("Debug", response);
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

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        TextView from = findViewById(R.id.from);
        TextView to = findViewById(R.id.to);
        TextView date = findViewById(R.id.date);
        Button start = findViewById(R.id.start);

        //findViewById(R.id.start).setOnClickListener(v -> flight());
    }
    public void openActivity (View view){
        Intent intent = new Intent(this, result.class);
        startActivity(intent);
    }

    String getTextIn(final int editor) {
        return ((EditText) findViewById(editor)).getText().toString();
    }

    boolean validSetup() {
        try {
            String from = getTextIn(R.id.from);
            String to = getTextIn(R.id.to);
            String date = getTextIn(R.id.date);

            /*
             * We check for validity using the ConnectN factory constructor, which should return null if the combination
             * of parameters is invalid.
             */
            //return ConnectN.create(from, to, date) != null;
            return false;
        } catch (Exception e) {
            // This probably means we couldn't parse a text field as a number. Return false in that case.
            return false;
        }
    }

    void startSearch() {

        /*
         * In Android we launch another screen using a so-called <em>Intent</em>. The Intent has to be configured
         * with which Activity we want to launch: in this case GameActivity.class.
         */
        Intent intent = new Intent(this, result.class);

        /*
         * Frequently when we launch another screen we also want to pass some extra information, similar to how we
         * would when calling a function. Intents allow us to add extra fields with custom names and information. As
         * long as the sender and receiver of the Intent agree on the name and format of these fields, we can use
         * this to pass certain types of information similar to how we would using function arguments.
         *
         * Here we use the extra fields of the intent to tell the result activity how the game has been configured:
         * what the departure, destination, and date value are.
         */
        intent.putExtra("From", getTextIn(R.id.fromcity));
        intent.putExtra("To", getTextIn(R.id.tocity));
        intent.putExtra("Date", getTextIn(R.id.date));

        // Actually start the GameActivity Activity, causing that screen to launch.
        startActivity(intent);

        /*
         * At that point the SetupActivity is no longer needed and can exit. In Android we do this using finish,
         * which cleans up and then destroys this activity.
         */
        finish();
    }

}
