package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
///login page
public class LoginPage extends AppCompatActivity {
    Button seConnecter;

    Button creerUnCompte;
    Button creerUnCompterForSuccusale;
    EditText nomDutilisateur;
    EditText motDePasse;
    String userStatus = "user";
    String nomDutilisateurString;

    DatabaseManager dbManager;

    public static final String USER_PREFS = "UserPrefs";

    /// Intents
    public void openAccountUser(){
        Intent i = new Intent(this, HomePageUser.class);
        i.putExtra( "Username",nomDutilisateurString);
        i.putExtra( "Status",userStatus);
        LoginPage.this.startActivity(i);
    }
    public void openAccountAdmin(){
        Intent i = new Intent(this, HomePageAdmin.class);
        i.putExtra( "Username",nomDutilisateurString);
        i.putExtra( "Status",userStatus);

        LoginPage.this.startActivity(i);
    }

    public void openAccountSuccursale() {
        Intent i = new Intent(this, HomePageSuccusale.class);
        i.putExtra( "Username",nomDutilisateurString);
        i.putExtra( "Status",userStatus);
        LoginPage.this.startActivity(i);
    }

    public void createAccountRegular(){
        Intent i = new Intent(this, CreateAccountForClient.class);

        LoginPage.this.startActivity(i);
    }

    public void createAccountForSuccursale(){
        Intent i = new Intent(this, Create_Account_Succusale.class);

        LoginPage.this.startActivity(i);
    }



    /// Checking login credentials with Database, returns True if present else returns False
    public boolean checkLoginCredentials(String utilisateur,String mdp) {

        DatabaseManager databaseManager = new DatabaseManager(this);

        try {
            databaseManager.open();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Fetch data from the database

        Cursor cursor = databaseManager.fetch();
        if (cursor != null){
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD));
                @SuppressLint("Range") String userType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TYPE));

                if (username.equals(utilisateur) && password.equals(mdp)) {

                    userStatus = userType;

                    SharedPreferences.Editor editor = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit();
                    editor.putString("Username", username);
                    editor.putString("Password", password);
                    editor.putString("Status", userType);
                    editor.apply();

                    databaseManager.close();
                    return true;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        databaseManager.close();
        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///Create instance of username/password info when login button is pressed
        ///Create a toast message for unsuccessful login
        nomDutilisateur = findViewById(R.id.nomDutilisateur);
        motDePasse = findViewById(R.id.motDePasse);
        seConnecter = findViewById(R.id.seConnecter);
        creerUnCompte = findViewById(R.id.creerUnCompte);
        creerUnCompterForSuccusale = findViewById(R.id.createAccountForSuccusale);


        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        }

        catch (Exception e){
            e.printStackTrace();
        }


        ///Execution when seConnecter button is pressed
        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Save variables utilisateur/mdp to check back with database


                nomDutilisateurString = nomDutilisateur.getText().toString();
                String mdp = motDePasse.getText().toString();

                /// Boolean logic for authentication
                /// If loginAccepted is True, sends user to the respective welcome page
                /// If loginAccepted is False, return Mauvais identifiant message
                boolean loginAccepted = checkLoginCredentials(nomDutilisateurString, mdp);

                if (loginAccepted) {
                    SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
                    String retrievedStatus = sharedPreferences.getString("Status", "");

                    if ("admin".equals(retrievedStatus)) {
                        openAccountAdmin();
                    } else if ("client".equals(retrievedStatus)) {
                        openAccountUser();
                    } else if ("employee".equals(retrievedStatus)) {
                        openAccountUser();
                    } else if ("succursale".equals(retrievedStatus)) {
                        openAccountSuccursale();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "Mauvais identifiant, réessayez.", Toast.LENGTH_LONG).show();
                }


            }
        });

        /// Send user to create account page
        creerUnCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                createAccountRegular();

            }
        });

        creerUnCompterForSuccusale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to create account for succusale
                createAccountForSuccursale();
            }
        });

    }

}