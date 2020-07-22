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

public class AfficheRendezVousActivity extends AppCompatActivity {

    String[] id;
    String[] patient;
    String[] motif;
    String[] date;
    String[] heure;
    String[] first_name;
    String[] last_name;

    ListView listViewRendezVous;
    BufferedInputStream is;
    String ligne =null;
    String result=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_rendez_vous);

        // lier la vu
        listViewRendezVous = findViewById(R.id.lviewRendezVous);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        recupDonneesRendezVous();

        ListeRendezVousActivity  listViewRDV = new ListeRendezVousActivity(this, id, patient, motif, date,  heure ,first_name,last_name);
        listViewRendezVous.setAdapter(listViewRDV);
        listViewRDV.notifyDataSetChanged();
    }

    // Collect rdv data function
    private void recupDonneesRendezVous() {
        //Connection
        try {
            String urladdress = "http://192.168.1.4/devmobile/liste_rendez_vous.php";
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
            motif = new String[ja.length()];
            date = new String[ja.length()];
            heure = new String[ja.length()];
            last_name = new String[ja.length()];
            first_name = new String[ja.length()];

            for (int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);
                id[i] = jo.getString("id");
                patient[i] = jo.getString("patient");
                motif[i] = jo.getString("motif");
                date[i] = jo.getString("date");
                heure[i] = jo.getString("heure");
                first_name[i] = jo.getString("first_name");
                last_name[i] = jo.getString("last_name");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}