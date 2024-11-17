package com.example.befit.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.befit.R;
import com.google.android.material.button.MaterialButton;

public class RegActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        EditText Email = (EditText) findViewById(R.id.Email);
        EditText Password = (EditText) findViewById(R.id.Password);
        EditText Name = (EditText) findViewById(R.id.Name);
        EditText Age = (EditText) findViewById(R.id.Year);
        EditText Weight = (EditText) findViewById(R.id.Weight);

        // Register button
        MaterialButton Singup = (MaterialButton) findViewById(R.id.btnSingUp);

        Singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String name = Name.getText().toString();
                String age = Age.getText().toString();
                String weight = Weight.getText().toString();
            }
        });
    }
}