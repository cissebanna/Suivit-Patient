package com.example.patient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListeConsultationActivity extends ArrayAdapter<String> {

    private String[] id;
    private String[] patient;
    private String[] diagnostic;
    private String[] date;
    private String[] description;
    private String[] first_name;
    private String[] last_name;
    private Activity context;

    public ListeConsultationActivity(Activity context, String[] id, String[] patient, String[] diagnosticCons, String[] dateCons, String[] descriptionCons, String[] first_nameCons, String[] last_nameCons) {
        super(context, R.layout.layout_consultation,diagnosticCons);
        this.id = id;
        this.context = context;
        this.patient = patient;
        this.diagnostic = diagnosticCons;
        this.date = dateCons;
        this.description = descriptionCons;
        this.first_name = first_nameCons;
        this.last_name = last_nameCons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ListeConsultationActivity.ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_consultation,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ListeConsultationActivity.ViewHolder)r.getTag();
        }

        viewHolder.tvDiagnosticCons.setText(diagnostic[position]);
        viewHolder.tvDescriptionCons.setText(description[position]);
        viewHolder.tvDateCons.setText(date[position]);
        viewHolder.tvNomPatientCons.setText(first_name[position]);
        viewHolder.tvPrenomPatientCons.setText(last_name[position]);

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Je suis cliquee", Toast.LENGTH_SHORT).show();
            }
        });

        return r;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDiagnosticCons ,tvDateCons , tvDescriptionCons , tvNomPatientCons , tvPrenomPatientCons;
        View parentLayout;

        ViewHolder(View v){
            super(v);
            tvDiagnosticCons =(TextView)v.findViewById(R.id.diagnosticCons);
            tvDateCons =(TextView)v.findViewById(R.id.dateCons);
            tvDescriptionCons =(TextView)v.findViewById(R.id.descriptionCons);
            tvNomPatientCons =(TextView)v.findViewById(R.id.nomPatientCons);
            tvPrenomPatientCons =(TextView)v.findViewById(R.id.prenomPatientCons);
            parentLayout = v.findViewById(R.id.parent_layout_consultation);
        }

    }
}