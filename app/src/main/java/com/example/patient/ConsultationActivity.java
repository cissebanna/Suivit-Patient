package com.example.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConsultationActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eTextDate ,txtTel,txtmalad,txtdescription ;
    Date editTextDate;

    String tel,description,maladie;
    /*Button btnGetDate;
    TextView txtview;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consultation);
        eTextDate=(EditText) findViewById(R.id.editText1);
        eTextDate.setInputType(InputType.TYPE_NULL);

        eTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ConsultationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eTextDate.setText(  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
        txtTel = findViewById(R.id.txttel);
        txtmalad = findViewById(R.id.txtmaladie);
        txtdescription = findViewById(R.id.detail);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tel = txtTel.getText().toString().trim();
                editTextDate = Date.valueOf(eTextDate.getText().toString().trim());
                description= txtdescription.getText().toString().trim();
                maladie = txtmalad.getText().toString().trim();

                if(tel.isEmpty() || editTextDate.equals("") || maladie.isEmpty() || description.isEmpty())
                {
                    String error = getString(R.string.error_fields);
                    Toast.makeText(ConsultationActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else {
                    insertionConsultation(tel, editTextDate, maladie, description);
                    //Toast.makeText(InscriptionActivity.this, "bien clique", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void insertionConsultation(String tel, Date editTextDate, String maladie, String description){

        try {
            String url ="http://192.168.1.7/devmobile/consultation.php";

            OkHttpClient client =new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("diagnostic", maladie)
                    .add("date", String.valueOf(editTextDate))
                    .add("description", description)
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
                            Toast.makeText(ConsultationActivity.this, error, Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(ConsultationActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message= getString(R.string.success_save);
                                    Toast.makeText(ConsultationActivity.this, message, Toast.LENGTH_SHORT).show();
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