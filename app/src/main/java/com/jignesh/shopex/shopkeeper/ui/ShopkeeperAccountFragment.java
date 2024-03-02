package com.jignesh.shopex.shopkeeper.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
 * Use the {@link ShopkeeperAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopkeeperAccountFragment extends Fragment {

    FirebaseApp firebaseApp;
    FirebaseFirestore db;

    final String ROOT = "gnr";
    final String pnp_DOC = "pnp";
    final String DETAILS_COLL = "Details";
    final String DETAILS_DOC = "details_doc";

    int[] textInputLayoutIds = {R.id.frg_skp_acc_shop_name, R.id.frg_skp_acc_shop_owner,
            R.id.frg_skp_acc_shop_email, R.id.frg_skp_acc_shop_mob,
            R.id.frg_skp_acc_shop_add, R.id.frg_skp_acc_shop_category, R.id.frg_skp_acc_shop_work_days};

    TextView greet_txt_name;

    TextInputLayout[] textInputLayouts = new TextInputLayout[textInputLayoutIds.length];

    final String[] keys = {"shop_name","shop_owner","email","mobile","address","category","active_days"};
    Button frgSkpSaveBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopkeeperAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopkeeperAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopkeeperAccountFragment newInstance(String param1, String param2) {
        ShopkeeperAccountFragment fragment = new ShopkeeperAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseApp = FirebaseApp.initializeApp(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FirebaseApp.initializeApp(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accFragment = inflater.inflate(R.layout.fragment_shopkeeper_account, container, false);

        try{
            db = FirebaseFirestore.getInstance();
        }
        catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        loadData();

        int i, length = textInputLayoutIds.length;
        for(i = 0; i < length; i++)
            textInputLayouts[i] = accFragment.findViewById(textInputLayoutIds[i]);

        greet_txt_name = accFragment.findViewById(R.id.shop_keeper_name);

        frgSkpSaveBtn = accFragment.findViewById(R.id.frg_skp_acc_save_btn);

        frgSkpSaveBtn.setOnClickListener(View -> {
            /** FireStore Code **/
        });

        return accFragment;
    }

    private void loadData(){
        db.collection(ROOT).document(pnp_DOC).collection(DETAILS_COLL).document(DETAILS_DOC).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                int i, len = document.getData().size() - 2;

                greet_txt_name.setText(document.get(keys[1]).toString());
                for(i = 0; i < len; i++){
                    textInputLayouts[i].getEditText().setText(document.getString(keys[i]));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Data couldn't be fetched", Toast.LENGTH_SHORT).show();
            }
        });
    }
}