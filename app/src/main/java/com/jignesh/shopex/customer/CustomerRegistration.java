package com.jignesh.shopex.customer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jignesh.shopex.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class CustomerRegistration extends AppCompatActivity {
    FirebaseFirestore db;

    int[] editTextIds = {R.id.csm_reg_usr_name_et,R.id.csm_reg_email_et,
            R.id.csm_reg_mob_et,R.id.csm_reg_add_et,
            R.id.cms_reg_pass_et,R.id.cms_reg_cfm_pass_et};

    final int pass_in = 4, cfm_pass_in = 5;

    final String ROOT = "gnr";
    final String CUS_DOC = "customer$";
    final String CUS_COLL = "Customers";

    final String[] keys = {"name", "email", "mobile", "address","password"};

    final String[] strPatterns = {"[a-z][A-Z]","^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,3})$","(0/91)?[7-9][0-9]{9}","([a-z A-z 0-9 - ,]+){10,25}","[a-z A-Z 0-9]{1,9}","[a-z A-Z 0-9]{1,9}"};

    EditText[] editTexts = new EditText[editTextIds.length];
    Button csmRegBtn;
    TextView csmLgnTv;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_customer_registration);

        db = FirebaseFirestore.getInstance();
        init();

        /** Register Button **/
        csmRegBtn.setOnClickListener(View -> {
            String[] data = getData();

            if(Arrays.stream(data).anyMatch(singleData -> singleData.equals("null"))) {
                return;
            }
            else {
                if (IntStream.of(validateData(data)).anyMatch(singleData -> singleData != 0)){
                    HashMap<String,Object> customerData = new HashMap<>(5);
                    for(int i = 0; i < data.length; i++){
                        customerData.put(keys[i],data[i]);
                    }

                    final String CUSTOMER = String.valueOf(customerData.get(keys[1]));

                    db.collection(ROOT).document(CUS_DOC).collection(CUS_COLL).document(CUSTOMER).set(customerData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Data registration successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CustomerRegistration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    csmRegBtn.setEnabled(false);
            }
        });

        csmLgnTv.setOnClickListener(View -> {
            startActivity(new Intent(this,CustomerLogin.class));
        });
    }

    private void init(){
        int i, len1 = editTextIds.length;
        for(i = 0; i < len1; i++) {
            editTexts[i] = findViewById(editTextIds[i]);
        }
        csmRegBtn = findViewById(R.id.cms_reg_btn_register);
        csmLgnTv = findViewById(R.id.tv_csm_reg_login_here);
    }

    private String[] getData(){
        int i, len = editTextIds.length - 1;
        String[] data = new String[len];
            for(i = 0; i < len; i++){
                if(i != pass_in)
                    data[i] = editTexts[i].getText().toString();
                else{
                    if(i == pass_in && editTexts[pass_in].getText().toString().equals(editTexts[cfm_pass_in].getText().toString())) {
                        data[i] = editTexts[pass_in].getText().toString();
                        break;
                    }
                    else
                        data[i] = "null";
                }
            }

        return data;
    }

    private int[] validateData(String[] data){
        int[] flags = new int[data.length];
            for(int i = 0; i < data.length; i++)
                if(String.valueOf(Pattern.compile(strPatterns[i]).matcher(data[i]).matches()).equals("true"))
                    flags[i] = 1;
                else{
                    flags[i] = 0;
                    csmRegBtn.setEnabled(true);
                }
        return flags;
    }
}