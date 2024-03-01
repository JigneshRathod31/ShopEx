package com.jignesh.shopex.customer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jignesh.shopex.R;

public class CustomerLogin extends AppCompatActivity {

    EditText csmLgnEmail, csmLgnPass;
    Button csmLgnBtn;
    TextView csmRegTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        csmLgnEmail = findViewById(R.id.csm_lgn_email_et);
        csmLgnPass = findViewById(R.id.csm_lgn_password_et);
        csmLgnBtn = findViewById(R.id.csm_lgn_btn_login);
        csmRegTv = findViewById(R.id.tv_csm_register_here);
        csmLgnBtn.setOnClickListener(View -> {
            /** FireStore Code **/

            startActivity(new Intent(this,CustomerActivity.class));
        });

        csmRegTv.setOnClickListener(View -> {
            startActivity(new Intent(this,CustomerRegistration.class));
        });
    }
}