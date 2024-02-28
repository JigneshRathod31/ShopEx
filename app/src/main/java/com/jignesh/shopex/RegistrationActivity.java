package com.jignesh.shopex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jignesh.shopex.customer.CustomerActivity;
import com.jignesh.shopex.shopkeeper.ShopkeeperActivity;

public class RegistrationActivity extends AppCompatActivity {

    TextView tvLoginHere;
    FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tvLoginHere = findViewById(R.id.tv_login_here);

        startActivity(new Intent(RegistrationActivity.this, ShopkeeperActivity.class));

        tvLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }
}