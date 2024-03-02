package com.jignesh.shopex.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;

import java.util.List;
import java.util.regex.Pattern;

public class CustomerLogin extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText csmLgnEmail, csmLgnPass;
    Button csmLgnBtn;
    TextView csmRegTv;

    final String ROOT = "gnr";
    final String CUS_DOC = "customer$";
    final String CUS_COLL = "Customers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_customer_login);

        csmLgnEmail = findViewById(R.id.csm_lgn_email_et);
        csmLgnPass = findViewById(R.id.csm_lgn_password_et);
        csmLgnBtn = findViewById(R.id.csm_lgn_btn_login);
        csmRegTv = findViewById(R.id.tv_csm_register_here);
        csmLgnBtn.setOnClickListener(View -> {

            final String email = csmLgnEmail.getText().toString();
            final String password = csmLgnPass.getText().toString();

            if (Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,3})$", email
            )
                    && Pattern.matches("[a-z A-Z 0-9]{1,9}", password)) {

                CollectionReference customerCollectionRef = db.collection(ROOT).document(CUS_DOC).collection(CUS_COLL);
                customerCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean flag = false;
                        List<DocumentSnapshot> qds = task.getResult().getDocuments();
                        int i, len = qds.size();
                        for (i = 0; i < len; i++) {
                            if (qds.get(i).getId().equals(email)) {
                                flag = true;
                                customerCollectionRef.document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().getString("password").equals(password)) {
                                                startActivity(new Intent(CustomerLogin.this, CustomerActivity.class));
                                                finish();
                                            } else
                                                Toast.makeText(CustomerLogin.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                        if(!flag)
                            Toast.makeText(CustomerLogin.this, "account not found", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            } else
                Toast.makeText(this, "Enter credentials in proper format", Toast.LENGTH_SHORT).show();
        });

        csmRegTv.setOnClickListener(View -> {
            startActivity(new Intent(this, CustomerRegistration.class));
        });
    }
}