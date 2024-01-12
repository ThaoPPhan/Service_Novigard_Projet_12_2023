package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/// not necessary
public class UserUpdateActivity extends AppCompatActivity {

    EditText newUsername, newPassword, userType;
    Button btn_save, btn_delete, btnCancel;

    String username, password, type;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);
        userType = findViewById(R.id.userType);
        btn_save = findViewById(R.id.updateService);
        btn_delete = findViewById(R.id.btn_delete);
        btnCancel = findViewById(R.id.btnCancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getIntentData();


    }

    void getIntentData(){
        Cursor cursor = myDB.readAllData();
            /// Getting Data from intent

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASSWORD));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_TYPE));

            newUsername.setText(username);
            newPassword.setText(password);
            userType.setText(type);

            cursor.close();


        } else {
            Toast.makeText(this, "Aucune donnée", Toast.LENGTH_SHORT).show();
        }
    }
}