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

public class Create_Account_Succusale extends AppCompatActivity {
    Button btn_annuler_of_succusale;
    Button btn_save_of_succusale;

    DatabaseSuccusale databaseSuccusale;

    private EditText newUsernameSuccusale;
    private EditText newPasswordSuccusale;
    private EditText addressSuccusale;
    private EditText hourSuccusale;
    private EditText typeServiceSuccusale;


    public void sendBackToLoginPage(){
        Intent i = new Intent(this, LoginPage.class);

        Create_Account_Succusale.this.startActivity(i);
    }

    public boolean checkExistingUser(String utilisateur) {
        // can sua
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
        setContentView(R.layout.activity_create_account_succusale);

        newUsernameSuccusale = findViewById(R.id.newUsernameOfSuccursale);
        newPasswordSuccusale = findViewById(R.id.newPasswordOfSuccursale);
        addressSuccusale = findViewById(R.id.addressOfSuccursale);
        hourSuccusale = findViewById(R.id.heuresDeTravail);
        typeServiceSuccusale = findViewById(R.id.typesDeServices);
        btn_annuler_of_succusale = findViewById(R.id.btnCancelOfSuccursale);
        btn_save_of_succusale = findViewById(R.id.updateServiceOfSuccursale);

        databaseSuccusale = new DatabaseSuccusale(this);
        try {
            databaseSuccusale.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        /// Cancel button send user back to Login Page
        btn_annuler_of_succusale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendBackToLoginPage();

            }
        });

        /// Execution when pressing save button
        btn_save_of_succusale.setOnClickListener(new View.OnClickListener() {

            /// Registers input of username password and category
            /// If usernameAvailable is True, insert new data in Database
            /// If usernameAvailable is False, show a Toast message asking to use different username
            @Override
            public void onClick(View view) {
                String newUsernameData = newUsernameSuccusale.getText().toString();
                String newPasswordData = newPasswordSuccusale.getText().toString();
                String address = addressSuccusale.getText().toString();
                String hour = hourSuccusale.getText().toString();
                String typeService = typeServiceSuccusale.getText().toString();
                String userType = "succusale";

                if (TextUtils.isEmpty(newUsernameData) || TextUtils.isEmpty(newPasswordData)|| TextUtils.isEmpty(address) || TextUtils.isEmpty(hour) || TextUtils.isEmpty(typeService)) {
                    Toast.makeText(Create_Account_Succusale.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    databaseSuccusale.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }

                boolean usernameAvailable = checkExistingUser(newUsernameData);

                if (usernameAvailable) {
                    databaseSuccusale.insertuserdata(newUsernameData,newPasswordData,address,hour,typeService);

                    databaseSuccusale.close();

                    String message = "Compte créé avec succès!";
                    Toast.makeText(Create_Account_Succusale.this, message, Toast.LENGTH_SHORT).show();

                    sendBackToLoginPage();
                } else {
                    Toast.makeText(Create_Account_Succusale.this, "Nom d'utilisateur déjà utilisé. Veuillez en sélectionner un autre.", Toast.LENGTH_LONG).show();
                }


            }

        });
    }
}