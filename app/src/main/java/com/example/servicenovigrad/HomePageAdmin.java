package com.example.servicenovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
/// welcome page admin
public class HomePageAdmin extends AppCompatActivity {

    Button adminSeDeconnecter;


    Button adminViewAccounts;

    Button adminViewService;

    TextView nomUtilisateurUser;
    TextView userStatusU;

    /// Intents
    public void sendBackToLoginPage(){
        Intent i = new Intent(this, LoginPage.class);

        HomePageAdmin.this.startActivity(i);
    }


    public void sendToClientList(){
        Intent i = new Intent(this,ClientList.class);

        HomePageAdmin.this.startActivity(i);
    }

    public void sendToServiceList(){
        Intent i = new Intent(this,ServiceListAdmin.class);

        HomePageAdmin.this.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_admin);
        adminSeDeconnecter = findViewById(R.id.adminSeDeconnecter);
        nomUtilisateurUser = findViewById(R.id.userNameAdminU);
        userStatusU = findViewById(R.id.userStatusAdminU);
        adminViewAccounts = findViewById(R.id.adminViewAccounts);
        adminViewService = findViewById(R.id.adminViewService);


        /// Display Username and Status on Welcome page
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String nomDutilisateurString = extras.getString("Username");
            String userStatusString = extras.getString("Status");

            nomUtilisateurUser.setText(nomDutilisateurString);
            userStatusU.setText(userStatusString);

        }

        /// Intent Disconnect sends back to Login Page
        adminSeDeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendBackToLoginPage();

            }
        });


        adminViewAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendToClientList();

            }
        });

        adminViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendToServiceList();

            }
        });
    }
}