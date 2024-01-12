package com.example.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

/// Custom Adapter for ServiceListAdmin
public class ServiceUpdateCustomAdapter extends RecyclerView.Adapter<ServiceUpdateCustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList service_ID, service_name, service_description;
    private ServiceDatabaseHelper databaseHelper;

    ServiceUpdateCustomAdapter(Context context, ArrayList service_ID, ArrayList service_name, ArrayList service_description) {
        this.context = context;
        this.service_ID = service_ID;
        this.service_name = service_name;
        this.service_description = service_description;
        this.databaseHelper = new ServiceDatabaseHelper(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recycle_viewer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceUpdateCustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.user_id_txt.setText(String.valueOf(service_ID.get(position)));
        holder.user_username_txt.setText(String.valueOf(service_name.get(position)));
        holder.user_type_txt.setText(String.valueOf(service_description.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// get the ID of the clicked user
                int clickedUserID = Integer.parseInt(String.valueOf(service_ID.get(position)));
                showDeleteConfirmationDialog(clickedUserID, position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return service_ID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_id_txt, user_username_txt, user_type_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_id_txt = itemView.findViewById(R.id.user_id_txt);
            user_username_txt = itemView.findViewById(R.id.user_username_txt);
            user_type_txt = itemView.findViewById(R.id.user_type_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    private void showDeleteConfirmationDialog(int userID, int position) {

        new AlertDialog.Builder(context)
                .setTitle("Gestionnaire")
                .setMessage("Que voulez-vous faire avec ce service?")
                .setNegativeButton("Modifier le service", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int clickedUserID = Integer.parseInt(String.valueOf(service_ID.get(position)));
                        Intent intent = new Intent(context, ServiceUpdateActivity.class);
                        intent.putExtra("SERVICE_ID", clickedUserID);
                        context.startActivity(intent);
                    }
                })
                .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Annuler," do nothing or handle accordingly
                    }
                })
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Supprimer," delete the service
                        databaseHelper.delete(userID);
                        service_ID.remove(position);
                        service_name.remove(position);
                        service_description.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setIcon(android.R.drawable.ic_menu_edit)
                .show();
    }
}

