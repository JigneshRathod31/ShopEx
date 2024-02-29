package com.jignesh.shopex.shopkeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.jignesh.shopex.R;

import org.jetbrains.annotations.NotNull;

public class ShopkeeperRegistration extends AppCompatActivity {
    int[] editTextIds = {R.id.skp_reg_usr_name_et,R.id.skp_reg_email_et,
            R.id.skp_reg_mob_et,R.id.skp_reg_add_et,
            R.id.skp_reg_shop_name_et, R.id.skp_reg_shop_owner_et,
            R.id.skp_reg_pass_et,R.id.skp_reg_cfm_pass_et};

    EditText[] editTexts = new EditText[editTextIds.length];
    Button skpRegBtn;
    TextView skpLgnTv;

    TextInputLayout skpRegCtg;

    RadioGroup skpRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_registration);

        init();

        /** Register Button **/
        skpRegBtn.setOnClickListener(View ->{
            String[] data = getData();
            if(!data[data.length - 1].equals("")){
                /** Write Code here (JK) **/
            }
        });

        skpLgnTv.setOnClickListener(View -> {
            startActivity(new Intent(this,ShopkeeperLogin.class));
        });
    }

    private void init(){
        int i, length = editTextIds.length;
        for(i = 0; i < length; i++)
            editTexts[i] = findViewById(editTextIds[i]);
        skpRegBtn = findViewById(R.id.skp_reg_btn_register);
        skpLgnTv = findViewById(R.id.tv_skp_reg_login_here);
        skpRegCtg = findViewById(R.id.skp_reg_ctg_et);
        skpRadioGroup = findViewById(R.id.skp_reg_work_days);
    }

    private String[] getData(){
        int i = 0;
        String[] data = new String[editTextIds.length + 2];
        for(EditText singleET : editTexts)
            data[i++] = singleET.getText().toString();

        data[i++] = skpRegCtg.getEditText().getText().toString();
        int selectedId = skpRadioGroup.getCheckedRadioButtonId();
        if(selectedId == R.id.rb1 || selectedId == R.id.rb2 || selectedId == R.id.rb3)
            data[i] = ((RadioButton) findViewById(selectedId)).getText().toString();
        else
            data[i] = "";

        return data;
    }
}