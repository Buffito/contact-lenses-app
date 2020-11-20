package com.unipi.p15173.contactlenses;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView textView1, textView2, textView3, textView4;
    Button addBtn, resetBtn;
    int number, temp;
    String _date = String.valueOf(DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void add(View view) {
        editor = sharedPreferences.edit();
        if (!textView1.getText().toString().isEmpty()) {
            number = Integer.parseInt(textView1.getText().toString()) + 1;
        }else{
            number = 1;
        }
        editor.putString("number", (String.valueOf(number)));

        editor.putString("date", _date);
        textView2.setText(_date);
        editor.apply();
        textView1.setText(String.valueOf(number));

        try {
            if (sharedPreferences.getBoolean("notification", false)) {
                if (!checkPref("limit")) {
                    temp = Integer.parseInt(sharedPreferences.getString("limit", "0")) -
                            Integer.parseInt(sharedPreferences.getString("number", "0"));
                    Toast.makeText(this, temp + getApplication().getResources().getString(R.string.limitNotification), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, getApplication().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    public void resetForm(View view) {
        textView1.setText("0");
        textView2.setText("");
        editor = sharedPreferences.edit();
        editor.putString("number", "0");
        editor.putString("date", "");
        editor.apply();
        Toast.makeText(this, getApplication().getResources().getString(R.string.reset), Toast.LENGTH_SHORT).show();
    }


    public void init() {
        addBtn = findViewById(R.id.addButton);
        resetBtn = findViewById(R.id.resetButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView6);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        initPreferences();
    }

    public void initPreferences() {
        try {
            setTexts();
            if (sharedPreferences.getString("number", "0").equals("0")) {
                textView1.setText("0");
                textView2.setText("");
            } else {
                textView1.setText(sharedPreferences.getString("number", "0"));
                String tempDate = sharedPreferences.getString("date", " ");
                textView2.setText(tempDate);
            }

            if (sharedPreferences.getBoolean("notification", false) &&
                    checkPref("number")) {
                if (!checkPref("limit")) {
                    temp = Integer.parseInt(sharedPreferences.getString("limit", "0")) -
                            Integer.parseInt(sharedPreferences.getString("number", "0"));
                    Toast.makeText(this, temp + getApplication().getResources().getString(R.string.limitNotification), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, getApplication().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    public void setTexts() {
        addBtn.setText(R.string.addButton);
        resetBtn.setText(R.string.resetButton);
        textView3.setText(R.string.numberView);
        textView4.setText(R.string.dateView);
        setTitle(R.string.app_name);
    }


    public boolean checkPref(String pref) throws NullPointerException {
        return sharedPreferences.getString(pref, "0").contains("1234567890");
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }
*/

    public void onResume() {
        super.onResume();
        initPreferences();
    }
}
