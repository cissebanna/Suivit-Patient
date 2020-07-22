package com.example.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AfficherConsultationActivity extends AppCompatActivity {

    String[] id;
    String[] patient;
    String[] diagnostic;
    String[] date;
    String[] description;
    String[] first_name;
    String[] last_name;

    ListView listViewConsultation;
    BufferedInputStream is;
    String ligne =null;
    String result=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_consultation);

        // lier la vu
        listViewConsultation = findViewById(R.id.listeViewConsultation);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        recupDonneesConsultation();

        ListeRendezVousActivity  listViewCons = new ListeRendezVousActivity(this, id, patient, diagnostic, date, description ,first_name,last_name);
        listViewConsultation.setAdapter(listViewCons);
        listViewCons.notifyDataSetChanged();
    }

    // Collect consultation data function
    private void recupDonneesConsultation() {
        //Connection
        try {
            String urladdress = "http://192.168.1.4/devmobile/selecteConsultation.php";
            URL url = new URL(urladdress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //content
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((ligne = br.readLine()) != null) {
                sb.append(ligne + "\n");
                ligne = br.readLine();
            }
            is.close();
            result = sb.toString();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        //JSON
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            id = new String[ja.length()];
            patient = new String[ja.length()];
            diagnostic = new String[ja.length()];
            date = new String[ja.length()];
            description = new String[ja.length()];
            last_name = new String[ja.length()];
            first_name = new String[ja.length()];

            for (int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);
                id[i] = jo.getString("id");
                patient[i] = jo.getString("patient");
                diagnostic[i] = jo.getString("diagnostic");
                date[i] = jo.getString("date");
                description[i] = jo.getString("description");
                first_name[i] = jo.getString("first_name");
                last_name[i] = jo.getString("last_name");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}