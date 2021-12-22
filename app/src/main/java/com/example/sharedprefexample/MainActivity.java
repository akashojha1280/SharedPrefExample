package com.example.sharedprefexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // initializing the 5 UI elements we created in activity_main.xml
    private TextView textView;
    private EditText editText;
    private Button applyTextButton;
    private Button saveButton;
    private Switch switch1;

    // loadData specific local instance variables
    private String text;
    private boolean switchOnOff;

    ////////////////////////////////////////////////
    // shared preferences variable grouping below //
    ////////////////////////////////////////////////

    // Explanation: the "key" that references the file all of our shared preferences are stored to.
    // Is only meant to be used in the getSharedPreferences()  method
    public static final String SHARED_PREFS = "sharedPrefs";

    // Explanation: meant to be the object that holds any text saved between sessions
    public static final String TEXT = "text";

    // Explanation: meant to be the object that holds the state of the switch between sessions
    public static final String SWITCH1 = "switch1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        applyTextButton = (Button) findViewById(R.id.apply_text_button);
        saveButton = (Button) findViewById(R.id.save_button);
        switch1 = (Switch) findViewById(R.id.switch1);

        // applyTextButton sets the textView to whatever we just typed in
        applyTextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // we are setting the textView to the result of the editText field
                textView.setText(editText.getText().toString());


            }
        });

        // this is the button that SAVES our text to last beyond the grave
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // save the data
                saveData();
            }
        });

        // makes sure we can call and display data from previous runs; back-end
        loadData();
        // updates the view itself; front-end
        updateViews();
    }

    public void saveData() {
        // create a SharedPreferences object called sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        // creates an editor option so we can write to sharedPreferences file
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // we save the data in the textView to the sharedPref file
        editor.putString(TEXT, textView.getText().toString());
        // sets the switch value where is should be via sharedPref
        editor.putBoolean(SWITCH1, switch1.isChecked());

        // needed after any usage of the SharedPreferences Editor object
        editor.apply();
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        // this has to be created separately within every method
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // if you have data saved in the sharedPref based TEXT object, send it to your local instance variable text
        // if you did not have data saved in the sharedPref based TEXT object, use the default value ""
        text = sharedPreferences.getString(TEXT, "");

        // If switch1 had a state saved from previous runs, have it set to that
        // If not, it'll default to false (not switched on)
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    // the equivalent of a setState method in flutter
    public void updateViews() {
        textView.setText(text);
        switch1.setChecked(switchOnOff);
    }
}