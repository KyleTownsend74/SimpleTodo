package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    EditText etText;
    Button btnSave;
    CheckBox cbDate;
    Spinner sMonth;
    Spinner sDay;
    Spinner sYear;
    String selectedMonth;
    int selectedDay;
    int selectedYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etText = findViewById(R.id.etText);
        btnSave = findViewById(R.id.btnSave);
        cbDate = findViewById(R.id.cbDate);
        sMonth = findViewById(R.id.sMonth);
        sDay = findViewById(R.id.sDay);
        sYear = findViewById(R.id.sYear);

        // Create and populate lists for use by the spinners
        List<String> months = new ArrayList<>();
        List<Integer> days = new ArrayList<>();
        List<Integer> years = new ArrayList<>();

        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        days.add(1);
        days.add(2);
        days.add(3);
        days.add(4);
        days.add(5);
        days.add(6);
        days.add(7);
        days.add(8);
        days.add(9);
        days.add(10);
        days.add(11);
        days.add(12);
        days.add(13);
        days.add(14);
        days.add(15);
        days.add(16);
        days.add(17);
        days.add(18);
        days.add(19);
        days.add(20);
        days.add(21);
        days.add(22);
        days.add(23);
        days.add(24);
        days.add(25);
        days.add(26);
        days.add(27);
        days.add(28);
        days.add(29);
        days.add(30);
        days.add(31);

        years.add(2020);
        years.add(2021);
        years.add(2022);
        years.add(2023);
        years.add(2024);
        years.add(2025);

        // Create and set up adapters for the spinners
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sMonth.setAdapter(monthAdapter);
        sDay.setAdapter(dayAdapter);
        sYear.setAdapter(yearAdapter);

        // Set what happens when the spinner is interacted with
        sMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getSupportActionBar().setTitle("Edit item");

        etText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        cbDate.setChecked(getIntent().getBooleanExtra(MainActivity.KEY_ITEM_DATE, false));
        sMonth.setSelection(monthAdapter.getPosition(getIntent().getStringExtra(MainActivity.KEY_ITEM_MONTH)));
        sDay.setSelection(dayAdapter.getPosition(getIntent().getIntExtra(MainActivity.KEY_ITEM_DAY, 1)));
        sYear.setSelection(yearAdapter.getPosition(getIntent().getIntExtra(MainActivity.KEY_ITEM_YEAR, 2021)));

        // When the user is done editing, they click the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create an intent which will contain the results
                Intent intent = new Intent();

                // pass the data (results of editing)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, etText.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                intent.putExtra(MainActivity.KEY_ITEM_DATE, cbDate.isChecked());
                intent.putExtra(MainActivity.KEY_ITEM_MONTH, selectedMonth);
                intent.putExtra(MainActivity.KEY_ITEM_DAY, selectedDay);
                intent.putExtra(MainActivity.KEY_ITEM_YEAR, selectedYear);

                // set the result of the intent
                setResult(RESULT_OK, intent);

                // finish activity, close the screen and go back
                finish();
            }
        });
    }
}