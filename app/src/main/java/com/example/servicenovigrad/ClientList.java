package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/// Interface that displays User database to Admin (Anyone including clients)
public class ClientList extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseHelper myDB;
    ArrayList<String> user_ID, user_username, user_type;
    CustomAdapter customAdapter;

    Button adminCreerUnCompte;

    Button back;


    public void sendToCreateAccountAdmin(){
        Intent i = new Intent(this, CreateAccount.class);

        ClientList.this.startActivity(i);
    }

    public void sendToAdminHomepage(){
        Intent i = new Intent(this, HomePageAdmin.class);

        ClientList.this.startActivity(i);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        adminCreerUnCompte = findViewById(R.id.adminCreerUnCompte);
        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.back);


        myDB = new DatabaseHelper(ClientList.this);
        user_ID = new ArrayList<>();
        user_username = new ArrayList<>();
        user_type = new ArrayList<>();

        displayData();

        customAdapter = new CustomAdapter(ClientList.this, user_ID, user_username, user_type);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClientList.this));


        adminCreerUnCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendToCreateAccountAdmin();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendToAdminHomepage();

            }
        });
    }

    void displayData(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data.",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                user_ID.add(cursor.getString(0));
                user_username.add(cursor.getString(1));
                user_type.add(cursor.getString(2));
            }
        }
    }
}