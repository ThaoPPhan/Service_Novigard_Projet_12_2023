package com.example.servicenovigrad;

import android.annotation.SuppressLint;
import android.content.Context;
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

/// To display USER database to Admin
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList user_ID, user_username, user_type;
    private DatabaseHelper databaseHelper;
    int position;
    CustomAdapter(Context context, ArrayList user_ID, ArrayList user_username, ArrayList user_type){
        this.context = context;
        this.user_ID = user_ID;
        this.user_username = user_username;
        this.user_type = user_type;
        this.databaseHelper = new DatabaseHelper(context);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recycle_viewer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.user_id_txt.setText(String.valueOf(user_ID.get(position)));
        holder.user_username_txt.setText(String.valueOf(user_username.get(position)));
        holder.user_type_txt.setText(String.valueOf(user_type.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// get the ID of the clicked user
                int clickedUserID = Integer.parseInt(String.valueOf(user_ID.get(position)));
                showDeleteConfirmationDialog(clickedUserID, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return user_ID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

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
        // Check if the userID is 1 (ID of the first account)
        if (userID == 1) {
            // Display a message that the first account cannot be deleted
            new AlertDialog.Builder(context)
                    .setTitle("Erreur")
                    .setMessage("Impossible de supprimer le compte admin.")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            // Display the confirmation dialog for other accounts
            new AlertDialog.Builder(context)
                    .setTitle("Confirmation")
                    .setMessage("Voulez-vous vraiment supprimer ce compte?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked "Yes," delete the account
                            databaseHelper.delete(userID);
                            user_ID.remove(position);
                            user_username.remove(position);
                            user_type.remove(position);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked "No," do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

}
