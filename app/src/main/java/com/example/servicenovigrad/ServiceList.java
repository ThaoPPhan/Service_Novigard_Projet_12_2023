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

/// Interface that displays Service database to Users
public class ServiceList extends AppCompatActivity {

    RecyclerView recyclerView;

    ServiceDatabaseHelper myDB;
    ArrayList<String> service_ID, service_name, service_description;
    ServiceCustomAdapter customAdapter;

    Button back;


    public void sendToBackToLogin(){
        Intent i = new Intent(this, HomePageUser.class);

        ServiceList.this.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);


        myDB = new ServiceDatabaseHelper(ServiceList.this);
        service_ID = new ArrayList<>();
        service_name = new ArrayList<>();
        service_description = new ArrayList<>();

        displayData();

        customAdapter = new ServiceCustomAdapter(ServiceList.this, service_ID, service_name, service_description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceList.this));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToBackToLogin();

            }
        });
    }

    void displayData(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data.",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                service_ID.add(cursor.getString(0));
                service_name.add(cursor.getString(1));
                service_description.add(cursor.getString(2));
            }
        }
    }
}