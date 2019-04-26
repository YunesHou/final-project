package com.example.funnyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.CalendarView;


public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private static final String TAG = "FunnyApplication:Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);

        EditText date = findViewById(R.id.userdate);

        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String yearString = Integer.toString(year);
                String monthString = Integer.toString(month + 1);
                String dayString = Integer.toString(dayOfMonth);
                date.setText(yearString + "-" + monthString + "-" + dayString);
            }
        });

        findViewById(R.id.start).setOnClickListener(v -> startSearch());

    }
    public void openActivity (View view){
        Intent intent = new Intent(this, result.class);
        startActivity(intent);
    }

    String getTextIn(final int editor) {
        return ((EditText) findViewById(editor)).getText().toString();
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
        intent.putExtra("Date", getTextIn(R.id.userdate));

        // Actually start the GameActivity Activity, causing that screen to launch.
        startActivity(intent);

        /*
         * At that point the SetupActivity is no longer needed and can exit. In Android we do this using finish,
         * which cleans up and then destroys this activity.
         */
        finish();
    }

}
