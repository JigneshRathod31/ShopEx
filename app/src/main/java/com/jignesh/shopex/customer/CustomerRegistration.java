package com.jignesh.shopex.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jignesh.shopex.R;

public class CustomerRegistration extends AppCompatActivity {
    int[] editTextIds = {R.id.csm_reg_usr_name_et,R.id.csm_reg_email_et,
            R.id.csm_reg_mob_et,R.id.csm_reg_add_et,
            R.id.cms_reg_pass_et,R.id.cms_reg_cfm_pass_et};

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
        int i, length = editTextIds.length;
        for(i = 0; i < length; i++)
            editTexts[i] = findViewById(editTextIds[i]);

        csmRegBtn = findViewById(R.id.cms_reg_btn_register);
        csmLgnTv = findViewById(R.id.tv_csm_reg_login_here);
    }

    private String[] getData(){
        int i = 0;
        String[] data = new String[editTextIds.length];

        for(EditText singleET : editTexts)
            data[i++] = singleET.getText().toString();

        return data;
    }
}