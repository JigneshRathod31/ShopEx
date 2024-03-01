package com.jignesh.shopex.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jignesh.shopex.R;

public class ShopkeeperLogin extends AppCompatActivity {

    EditText skpLgnEmail, skpLgnPass;
    Button skpLgnBtn;
    TextView skpRegTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_login);

        skpLgnEmail = findViewById(R.id.skp_lgn_email_et);
        skpLgnPass = findViewById(R.id.skp_lgn_password_et);
        skpLgnBtn = findViewById(R.id.skp_lgn_btn_login);
        skpRegTv = findViewById(R.id.tv_skp_register_here);
        skpLgnBtn.setOnClickListener(View -> {

        });

        skpRegTv.setOnClickListener(View -> {
            startActivity(new Intent(this, ShopkeeperRegistration.class));
        });
    }
}