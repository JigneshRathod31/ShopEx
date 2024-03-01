package com.jignesh.shopex.customer.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jignesh.shopex.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CustomerAccountFragment extends Fragment {

    int[] textInputLayoutIds = {R.id.frg_csm_acc_usr_name, R.id.frg_csm_acc_usr_email, R.id.frg_csm_acc_usr_mob, R.id.frg_csm_acc_usr_add};
    TextInputLayout[] textInputLayouts = new TextInputLayout[textInputLayoutIds.length];

    String ROOT = "gnr";
    String CUS_DOC = "custmers$";
    String CUS_COLL = "Customers";
    String USER = "jk@gmail.com";

    // Firebase Objects

    FirebaseApp firebaseApp;
    FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerAccountFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerAccountFragment newInstance(String param1, String param2) {
        CustomerAccountFragment fragment = new CustomerAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseApp = FirebaseApp.initializeApp(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accFragment = inflater.inflate(R.layout.fragment_customer_account, container, false);

        try{
            FirebaseApp.initializeApp(getContext());
            db = FirebaseFirestore.getInstance();
            int i, length = textInputLayoutIds.length;
            for(i = 0; i < length; i++)
                textInputLayouts[i] = accFragment.findViewById(textInputLayoutIds[i]);
        }
        catch (Exception e){
            Log.d("error", e.toString());
            Toast.makeText(getContext(), "Errorva: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        loadData();

        Button frgCsmSaveBtn = accFragment.findViewById(R.id.frg_csm_acc_save_btn);
        frgCsmSaveBtn.setOnClickListener(View -> {
            /** FireStore Code **/
        });

        return accFragment;
    }

    private void loadData(){
            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();

                                Toast.makeText(getContext(), document.get("name").toString(), Toast.LENGTH_SHORT).show();
                            }else {

                            }
                        }
                    });
    }
}