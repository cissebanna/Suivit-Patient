package com.example.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnexionActivity extends AppCompatActivity {

    private EditText txtLogin, txtPassword;
    private Button btnConnect, btnSignIn;
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        btnConnect = findViewById(R.id.btnConnect);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString();

                if(login.isEmpty() || password.isEmpty())
                {
                    String error = getString(R.string.error_fields);
                    Toast.makeText(ConnexionActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    authentification(login, password);
                }
            }
        });

    }

    public void authentification(String login, String password){

        try {

            String url ="http://192.168.1.7/devmobile/connexion.php?login="+login+"&password="+password;


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String error = getString(R.string.error_connection);
                            Toast.makeText(ConnexionActivity.this, error, Toast.LENGTH_SHORT).show();

                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {

                    try {
                        JSONObject jo = new JSONObject(response.body().string());
                        String status = jo.getString("status");
                        if (status.equalsIgnoreCase("KO")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String message = getString(R.string.error_parameters);
                                    Toast.makeText(ConnexionActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
