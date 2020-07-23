package com.example.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InscriptionActivity extends AppCompatActivity {

    private EditText txtFirstName, txtLastName, txtAdresse, txtPhone, txtLogin, txtPassword;
    private CheckBox cbMale, cbFemale;
    private String firstName, lastName, sexe, phone, login, password, adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtPhone = findViewById(R.id.txtTelephone);
        txtAdresse = findViewById(R.id.txtAdresse);
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        cbFemale = findViewById(R.id.cbFemale);
        cbMale = findViewById(R.id.cbMale);

        Button btnSave = findViewById(R.id.btnSaveuser);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                phone = txtPhone.getText().toString().trim();
                adresse = txtAdresse.getText().toString().trim();
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                sexe = "";
                if(cbMale.isChecked())
                {
                    sexe+=cbMale.getText().toString().trim()+" ";
                }
                if(cbFemale.isChecked())
                {
                    sexe+=cbFemale.getText().toString().trim()+" ";
                }


                if(login.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
                        || adresse.isEmpty() || phone.isEmpty() || sexe.isEmpty())
                {
                    String error = getString(R.string.error_fields);
                    Toast.makeText(InscriptionActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else {
                    inscription(login, password, firstName, lastName, adresse, phone, sexe);

                    //vide les champs apres l'inscription
                    champs();

                }

                String resume = firstName+"\n\n"+lastName+"\n\n"+phone+"\n\n"+adresse+"\n\n"+sexe;

                Toast.makeText(InscriptionActivity.this, resume, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void champs()
    {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtLogin.setText("");
        txtAdresse.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
    }

    public void inscription(String login, String password, String firstName, String lastName, String adresse, String phone, String sexe){

        try {
<<<<<<< HEAD
            String url ="http://192.168.1.8/devmobile/inscription.php";
=======
            UrlBase path =new UrlBase();
            String url =path.url+"/inscription.php";
>>>>>>> d2861d3582d4d9dc5b26247ce1fec65647241528

            OkHttpClient client =new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("login", login)
                    .add("password", password)
                    .add("first_name", firstName)
                    .add("last_name", lastName)
                    .add("adresse", adresse)
                    .add("tel", phone)
                    .add("sexe", sexe)
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
                            Toast.makeText(InscriptionActivity.this, error, Toast.LENGTH_SHORT).show();

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
                                    Toast.makeText(InscriptionActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message= getString(R.string.success_save);
                                    Toast.makeText(InscriptionActivity.this, message, Toast.LENGTH_SHORT).show();
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
