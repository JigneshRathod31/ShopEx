package com.jignesh.shopex.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;
import com.jignesh.shopex.adapters.CustomerStoreAdapter;
import com.jignesh.shopex.models.CustomerStoreModel;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerHomeFragment extends Fragment {

    RecyclerView rvShopkeeperStores;
    CustomerStoreAdapter customerStoreAdapter;
    ArrayList<CustomerStoreModel> alCustomerStoreModel = new ArrayList<>();

    // Firebase Objects

    FirebaseApp firebaseApp;
    FirebaseFirestore db;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerHomeFragment newInstance(String param1, String param2) {
        CustomerHomeFragment fragment = new CustomerHomeFragment();
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
        View root = inflater.inflate(R.layout.fragment_customer_home, container, false);

        try {
            FirebaseApp.initializeApp(getContext());
            db = FirebaseFirestore.getInstance();
            rvShopkeeperStores = root.findViewById(R.id.rv_shopkeeper_stores);

            rvShopkeeperStores.setLayoutManager(new LinearLayoutManager(getContext()));
            customerStoreAdapter = new CustomerStoreAdapter(alCustomerStoreModel, getContext());
            rvShopkeeperStores.setAdapter(customerStoreAdapter);

        } catch (Exception e) {
            Log.d("shuThayu", e.toString());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    void retrieveDataFromFirebase(){
        db.collection("gnr").document("pnp").collection("Details").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document : task.getResult()) {
                        CustomerStoreModel customerStoreModel = new CustomerStoreModel();

                        customerStoreModel.setShopName(document.get("shop_name").toString());
                        customerStoreModel.setShopOwner(document.get("shop_owner").toString());
                        customerStoreModel.setActiveDays(document.get("active_days").toString());
                        customerStoreModel.setAddress(document.get("address").toString());
                        customerStoreModel.setShopLogo(document.get("shop_logo").toString());
                        customerStoreModel.setMobile(document.get("mobile").toString());
                        customerStoreModel.setEmail(document.get("email").toString());

                        alCustomerStoreModel.add(customerStoreModel);
                    }
                }
            }
        });

        customerStoreAdapter.notifyDataSetChanged();
    }

    void storeSomeDummyDataOnFirebase(){
        for (int i=0; i<10; i++){
            HashMap<String, String> storeDetails = new HashMap<>();
            storeDetails.put("shop_name", "pnp");
            storeDetails.put("shop_owner", "Shailesh Rana");
            storeDetails.put("shop_logo", "Coming Soon");
            storeDetails.put("category", "Food Shop");
            storeDetails.put("email", "shaileshrana@gmail.com");
            storeDetails.put("mobile", "1234567890");
            storeDetails.put("active_days", "Everyday");
            storeDetails.put("address", "Near GH-6, Gandhinagar");

            db.collection("gnr").document("pnp")
                    .collection("Details").document("details_doc")
                    .set(storeDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("store", "Data stored successfully");
                            }else {
                                Log.d("store", task.getException().toString());
                            }
                        }
                    });
        }
    }
}