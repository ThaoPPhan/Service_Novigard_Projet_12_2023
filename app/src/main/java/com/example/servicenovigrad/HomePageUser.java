package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class HomePageUser extends AppCompatActivity {
    TextView nomUtilisateurUser;
    TextView userStatusU;
    Button seDeconnecter;
    Button clientViewService;

    public void sendBackToLoginPage(){
        Intent i = new Intent(this, LoginPage.class);

        HomePageUser.this.startActivity(i);
    }

    public void sendToServiceList(){
        Intent i = new Intent(this,ServiceList.class);

        HomePageUser.this.startActivity(i);
    }

    /// Display information of user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_client);
        seDeconnecter = findViewById(R.id.adminSeDeconnecter);
        nomUtilisateurUser = findViewById(R.id.userNameU);
        userStatusU = findViewById(R.id.userStatusAdminU);
        clientViewService = findViewById(R.id.clientViewService);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String nomDutilisateurString = extras.getString("Username");
            String userStatusString = extras.getString("Status");

        nomUtilisateurUser.setText(nomDutilisateurString);
        userStatusU.setText(userStatusString);

        }
        /// Send user back to login page
        seDeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendBackToLoginPage();

            }
        });

        clientViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendToServiceList();

            }
        });

    }
}