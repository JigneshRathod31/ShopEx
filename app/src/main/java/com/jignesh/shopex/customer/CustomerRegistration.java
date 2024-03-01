package com.jignesh.shopex.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jignesh.shopex.R;

import java.util.regex.Pattern;

public class CustomerRegistration extends AppCompatActivity {
    int[] editTextIds = {R.id.csm_reg_usr_name_et,R.id.csm_reg_email_et,
            R.id.csm_reg_mob_et,R.id.csm_reg_add_et,
            R.id.cms_reg_pass_et,R.id.cms_reg_cfm_pass_et};

    final String[] strPatterns = {"[a-z][A-Z]","[a-z]","[^[0-5][0-9]{9}]","[a-z A-z 0-9]","[a-z A-Z 0-9]{1,9}","[a-z A-Z 0-9]{1,9}"};

    EditText[] editTexts = new EditText[editTextIds.length];
    Button csmRegBtn;
    TextView csmLgnTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        init();

        /** Register Button **/
        csmRegBtn.setOnClickListener(View -> {
            String[] data = getData();
        });

        csmLgnTv.setOnClickListener(View -> {
            startActivity(new Intent(this,CustomerLogin.class));
        });
    }

    private void init(){
        int i, j, len1 = editTextIds.length, len2 = strPatterns.length;
        for(i = 0, j = 0; i < len1 && j < len2; i++, j++) {
            editTexts[i] = findViewById(editTextIds[i]);
        }

        csmRegBtn = findViewById(R.id.cms_reg_btn_register);
        csmLgnTv = findViewById(R.id.tv_csm_reg_login_here);
    }

    private String[] getData(){
        int i = 0;
        String[] data = new String[editTextIds.length];

        return data;
    }
}