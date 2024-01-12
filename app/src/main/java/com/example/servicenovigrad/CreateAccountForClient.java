package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLDataException;

///Create account for Client
public class CreateAccountForClient extends AppCompatActivity {

    Button btn_annuler;
    Button btn_save;

    private EditText newUsername;
    private EditText newPassword;

    DatabaseManager dbManager;

    /// Intent
    public void sendBackToLoginPage(){
        Intent i = new Intent(this, LoginPage.class);

        CreateAccountForClient.this.startActivity(i);
    }

    /// Check with Database if the username is already taken
    /// Returns False if taken, otherwise returns True
    public boolean checkExistingUser(String utilisateur) {
        DatabaseManager databaseManager = new DatabaseManager(this);

        try {
            databaseManager.open();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Fetch data from the database

        Cursor cursor = databaseManager.fetch();
        if (cursor != null) {
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));

                if (username.equals(utilisateur)) {
                    databaseManager.close();
                    return false;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        databaseManager.close();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_for_client);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);
        btn_annuler = findViewById(R.id.btnCancel);
        btn_save = findViewById(R.id.updateService);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        /// Cancel button send user back to Login Page
        btn_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendBackToLoginPage();

            }
        });

        /// Execution when pressing save button
        btn_save.setOnClickListener(new View.OnClickListener() {

            /// Registers input of username password and category
            /// If usernameAvailable is True, insert new data in Database
            /// If usernameAvailable is False, show a Toast message asking to use different username
            @Override
            public void onClick(View view) {
                String newUsernameData = newUsername.getText().toString();
                String newPasswordData = newPassword.getText().toString();
                String userType = "client";

                if (TextUtils.isEmpty(newUsernameData) || TextUtils.isEmpty(newPasswordData)) {
                    Toast.makeText(CreateAccountForClient.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    dbManager.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }

                boolean usernameAvailable = checkExistingUser(newUsernameData);

                if (usernameAvailable) {
                    dbManager.insert(newUsernameData, newPasswordData, userType);

                    dbManager.close();

                    String message = "Compte créé avec succès!";
                    Toast.makeText(CreateAccountForClient.this, message, Toast.LENGTH_SHORT).show();

                    sendBackToLoginPage();
                } else {
                    Toast.makeText(CreateAccountForClient.this, "Nom d'utilisateur déjà utilisé. Veuillez en sélectionner un autre.", Toast.LENGTH_LONG).show();
                }


            }

        });
    }
}