package com.example.funnyapplication;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final TextView a = findViewById(R.id.Name);
        TextView b = findViewById(R.id.Director);
        TextView c = findViewById(R.id.Year);
        Button d = findViewById(R.id.start);
    }
}
