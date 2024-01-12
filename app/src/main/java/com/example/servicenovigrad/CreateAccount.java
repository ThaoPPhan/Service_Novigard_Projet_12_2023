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
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

/// Create account for Admin
public class CreateAccount extends AppCompatActivity {
    private Spinner spnCategory;
    private CategoryAdapter categoryAdapter;

    private Button btnSave;
    private Button btnCancel;
    private EditText newUsername;
    private EditText newPassword;

    DatabaseManager dbManager;

    /// Intent
    public void sendBackToAdminWelcomePage(){
        Intent i = new Intent(this, HomePageAdmin.class);

        CreateAccount.this.startActivity(i);
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
        setContentView(R.layout.activity_create_account);

        spnCategory = findViewById(R.id.spn_category);
        categoryAdapter = new CategoryAdapter(this,R.layout.item_selected, getListCategory());
        spnCategory.setAdapter(categoryAdapter);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        dbManager = new DatabaseManager(this);
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        /// Execution when pressing save button
        btnSave.setOnClickListener(new View.OnClickListener() {

            /// Registers input of username password and category
            /// If usernameAvailable is True, insert new data in Database
            /// If usernameAvailable is False, show a Toast message asking to use different username
            public void onClick(View view) {
                String newUsernameData = newUsername.getText().toString();
                String newPasswordData = newPassword.getText().toString();

                if (TextUtils.isEmpty(newUsernameData) || TextUtils.isEmpty(newPasswordData)) {
                    Toast.makeText(CreateAccount.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Category selectedCategoryObject = (Category) spnCategory.getSelectedItem();

                String selectedCategory = selectedCategoryObject.getName();

                try {
                    dbManager.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }

                boolean usernameAvailable = checkExistingUser(newUsernameData);

                if (usernameAvailable) {
                    dbManager.insert(newUsernameData, newPasswordData, selectedCategory);

                    dbManager.close();

                    String message = "Compte créé avec succès!";
                    Toast.makeText(CreateAccount.this, message, Toast.LENGTH_SHORT).show();

                    sendBackToAdminWelcomePage();
                } else {
                    Toast.makeText(CreateAccount.this, "Nom d'utilisateur déjà utilisé. Veuillez en sélectionner un autre.", Toast.LENGTH_LONG).show();
                }
            }

        });

        /// Cancel button send user back to Welcome admin page
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Send user to create account for client accounts
                sendBackToAdminWelcomePage();

            }
        });
    }

    /// Category of spinner
    private List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("client"));
        list.add(new Category("employee"));
        list.add(new Category("succursale"));

        return list;
    }
}