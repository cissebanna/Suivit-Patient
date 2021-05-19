package com.example.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListeRendezVousActivity extends ArrayAdapter<String> {

    private String[] id;
    private String[] patient;
    private String[] motif;
    private String[] date;
    private String[] heure;
    private String[] first_name;
    private String[] last_name;
    private Activity context;

    public ListeRendezVousActivity(Activity context, String[] id, String[] patientName, String[] motifR, String[] dateR, String[] heureR ,String[] first_nameRdv, String[] last_nameRdv){
        super(context, R.layout.layout_rendez_vous,motifR);
        this.context=context;
        this.id=id;
        this.patient =patientName;
        this.motif =motifR;
        this.date =dateR;
        this.heure =heureR;
        this.first_name = first_nameRdv;
        this.last_name = last_nameRdv;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_rendez_vous,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)r.getTag();
        }

        viewHolder.tvMotif.setText(motif[position]);
        viewHolder.tvHeureRdv.setText(heure[position]);
        viewHolder.tvDateRdv.setText(date[position]);
        viewHolder.tvNomPatientRdv.setText(first_name[position]);
        viewHolder.tvPrenomPatientRdv.setText(last_name[position]);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Je suis cliquee", Toast.LENGTH_SHORT).show();
            }
        });

        return r;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMotif ,tvDateRdv , tvHeureRdv , tvNomPatientRdv , tvPrenomPatientRdv;
        View parentLayout;

        ViewHolder(View v){
            super(v);
            tvMotif =(TextView)v.findViewById(R.id.tvRdvMotif);
            tvDateRdv =(TextView)v.findViewById(R.id.dateRdv);
            tvHeureRdv =(TextView)v.findViewById(R.id.heureRdv);
            tvNomPatientRdv =(TextView)v.findViewById(R.id.nomPatientRdv);
            tvPrenomPatientRdv =(TextView)v.findViewById(R.id.prenomPatientRdv);
            parentLayout = v.findViewById(R.id.parent_layout_rdv);
        }

    }
}