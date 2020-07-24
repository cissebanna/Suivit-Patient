package com.example.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RendezVousActivity extends AppCompatActivity {
    private EditText txtTel;
    private CheckBox cbControle, cbVisite;
    private String txttel;
    private String motif;
    private Date editTextDate;
    private Time editTextHeure;

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
                picker = new DatePickerDialog(RendezVousActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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


        eTextheure=(EditText) findViewById(R.id.editHeureText1);
        eTextheure.setInputType(InputType.TYPE_NULL);

        eTextheure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerheure = new TimePickerDialog(RendezVousActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eTextheure.setText(sHour + ":" + sMinute + ":00");
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

        txtTel = findViewById(R.id.txtTel);
        cbControle = findViewById(R.id.cbControle);
        cbVisite = findViewById(R.id.cbVisite);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txttel = txtTel.getText().toString().trim();
                editTextDate = Date.valueOf(eText.getText().toString().trim());
                editTextHeure = Time.valueOf(eTextheure.getText().toString().trim());
                motif = "";
                if(cbControle.isChecked())
                {
                    motif+=cbControle.getText().toString().trim()+" ";
                }
                if(cbVisite.isChecked())
                {
                    motif+=cbVisite.getText().toString().trim()+" ";
                }

                if(txttel.isEmpty() || editTextHeure.equals("") || editTextDate.equals("") || motif.isEmpty())
                {
                    String error = getString(R.string.error_fields);
                    Toast.makeText(RendezVousActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else {
                    insertionRDV(txttel, editTextDate, editTextHeure, motif);

                    //vider les champs
                    champs();
                }
            }
        });
    }

    public void champs()
    {
        eText.setText("");
        eTextheure.setText("");
        txtTel.setText("");
        cbControle.setChecked(false);
        cbVisite.setChecked(false);
    }


    public void insertionRDV(String tel, Date editTextDate, Time editTextHeure, String motif){

        try {

            UrlBase path =new UrlBase();
            String url =path.url+"/rendezVous.php";

            OkHttpClient client =new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("motif", motif)
                    .add("dateRdv", String.valueOf(editTextDate))
                    .add("heureRdv", String.valueOf(editTextHeure))
                    .add("tel", tel)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String error = getString(R.string.error_connection);
                            Toast.makeText(RendezVousActivity.this, error, Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    try {
                        JSONObject jo = new JSONObject(response.body().string());
                        String status = jo.getString("status");
                        if(status.equalsIgnoreCase("KO"))
                        {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message= getString(R.string.error_parameters);
                                    Toast.makeText(RendezVousActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message= getString(R.string.success_save);
                                    Toast.makeText(RendezVousActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }catch (Exception e){

                    }


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}