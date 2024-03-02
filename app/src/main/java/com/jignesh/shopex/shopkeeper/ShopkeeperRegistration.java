package com.jignesh.shopex.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jignesh.shopex.R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ShopkeeperRegistration extends AppCompatActivity {
    FirebaseFirestore db;

    int[] editTextIds = {R.id.skp_reg_email_et,
            R.id.skp_reg_mob_et, R.id.skp_reg_add_et,
            R.id.skp_reg_shop_name_et, R.id.skp_reg_shop_owner_et,
            R.id.skp_reg_pass_et, R.id.skp_reg_cfm_pass_et};

    final int pass_in = 5, cfm_pass_in = 6;

    final String[] keys = {"email","mobile","address","shop_name","shop_owner","password","category","active_days"};

    EditText[] editTexts = new EditText[editTextIds.length];
    Button skpRegBtn;
    TextView skpLgnTv;
    TextInputLayout skpRegCtg;
    RadioGroup skpRadioGroup;

    final String[] strPatterns = {"[a-zA-Z ]+","^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,3})$", "(0/91)?[7-9][0-9]{9}", "^([0-9]{1,4})+([a-zA-z-, ]+){10,25}", "^([A-Z]{1})?[a-z ]+[0-9]*", "[A-Za-z ]+", "[A-Za-z0-9 ]{1,9}", "[A-Za-z0-9 ]{1,9}","[A-Za-z ]+","[A-Z -]+"};

    final String ROOT = "gnr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_shopkeeper_registration);

        init();

        /** Register Button **/
        skpRegBtn.setOnClickListener(View -> {
            String[] data = getData();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (Arrays.stream(data).anyMatch(nullVal -> nullVal.equals("null")))
                    return;
                else {
                    if (Arrays.stream(validateData(data)).anyMatch(falseVal -> !falseVal.equals("false"))) {
                        HashMap<String, Object> shopKeeperData = new HashMap<>();

                        for(int i = 0; i < data.length; i++)
                            shopKeeperData.put(keys[i],data[i]);

                        db.collection(ROOT).document(shopKeeperData.get(keys[3]).toString()).collection("Details").document("details_doc").set(shopKeeperData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ShopkeeperRegistration.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ShopkeeperRegistration.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        skpLgnTv.setOnClickListener(View ->{
            startActivity(new Intent(this, ShopkeeperLogin.class));
        });
    }

    private void init() {
        int i, length = editTextIds.length;
        for (i = 0; i < length; i++)
            editTexts[i] = findViewById(editTextIds[i]);

        db = FirebaseFirestore.getInstance();
        skpRegBtn = findViewById(R.id.skp_reg_btn_register);
        skpLgnTv = findViewById(R.id.tv_skp_reg_login_here);
        skpRegCtg = findViewById(R.id.skp_reg_ctg_et);
        skpRadioGroup = findViewById(R.id.skp_reg_work_days);
    }

    private String[] getData() {
        int i;
        String[] data = new String[editTextIds.length + 1];
        for (i = 0; i < editTexts.length - 1;i++) {
            if(i != 5)
                data[i] = editTexts[i].getText().toString();
            else {
                if (editTexts[pass_in].getText().toString().equals(editTexts[cfm_pass_in].getText().toString()) && i == pass_in)
                    data[i] = editTexts[pass_in].getText().toString();
                else
                    data[i] = "false";
            }
        }

        data[i++] = skpRegCtg.getEditText().getText().toString();
        int selectedId = skpRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.rb1 || selectedId == R.id.rb2 || selectedId == R.id.rb3)
            data[i] = ((RadioButton) findViewById(selectedId)).getText().toString();
        else
            data[i] = "null";

        return data;
    }

    private String[] validateData(String[] data) {
        String[] flags = new String[data.length];
        for (int i = 0; i < flags.length; i++) {
            if (i <= 4 || i == 6)
                flags[i] = String.valueOf(Pattern.matches(strPatterns[i],data[i]));
            else
                flags[i] = String.valueOf(Pattern.matches(strPatterns[i],data[i]) || Pattern.matches("[A-Z ]+",data[i]));
        }
        return flags;
    }
}