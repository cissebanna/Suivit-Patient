package com.example.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimeActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eText;
    /*Button btnGetDate;
    TextView txtview;*/

    TimePickerDialog pickerheure;
    EditText eTextheure;
    /*Button btnGetheure;
    TextView txtviewheure;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rendez_vous);
        //txtview=(TextView)findViewById(R.id.textView1);
        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);

        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(DateTimeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        /*btnGetDate=(Button)findViewById(R.id.button1);
        btnGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtview.setText("Date choisie: "+ eText.getText());
            }
        });*/

        /*===============================  Heure  ==================================*/

        //txtviewheure=(TextView)findViewById(R.id.textHeureView1);
        eTextheure=(EditText) findViewById(R.id.editHeureText1);
        eTextheure.setInputType(InputType.TYPE_NULL);

        eTextheure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerheure = new TimePickerDialog(DateTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eTextheure.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickerheure.show();
            }
        });
        /*btnGetheure=(Button)findViewById(R.id.buttonHeure1);
        btnGetheure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtviewheure.setText("Heure choisie: "+ eTextheure.getText());
            }
        });*/
    }

}