package com.jignesh.shopex.customer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;
import com.jignesh.shopex.adapters.CustomerStoreAdapter;
import com.jignesh.shopex.models.CustomerStoreModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerHomeFragment extends Fragment {

    RecyclerView rvShopkeeperStores;
    CustomerStoreAdapter customerStoreAdapter;
    ArrayList<CustomerStoreModel> alCustomerStoreModel;

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

            alCustomerStoreModel = new ArrayList<>();
            rvShopkeeperStores.setLayoutManager(new LinearLayoutManager(getContext()));
            customerStoreAdapter = new CustomerStoreAdapter(alCustomerStoreModel, getContext());
            rvShopkeeperStores.setAdapter(customerStoreAdapter);

//            addStoreDetailsOnFirebase();

            retrieveStoreDataFromFirebase();

        } catch (Exception e) {
            Log.d("error", e.toString());
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    void retrieveStoreDataFromFirebase(){
        try {
            db.collection("gnr")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){

                                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                                fetchStoreDetails(documents, 0);
                            }else{
                                Log.d("error", task.getException().toString());
//                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } catch (Exception e) {
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("error", e.toString());
        }

    }

    void fetchStoreDetails(List<DocumentSnapshot> documents, int i){
        if (i < documents.size()){
            DocumentSnapshot document = documents.get(i);
            String shop = document.getId().trim();

            if (shop.equals("customer$")){
                fetchStoreDetails(documents, i+1);
            }

            db.collection("gnr")
                    .document(shop)
                    .collection("Details")
                    .document("details_doc")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()){
                                    Map<String, Object> shopDetails = document.getData();
                                    CustomerStoreModel customerStoreModel = new CustomerStoreModel();

                                    customerStoreModel.setShopName(shopDetails.get("shop_name").toString());
                                    customerStoreModel.setShopOwner(shopDetails.get("shop_owner").toString());
                                    customerStoreModel.setCategory(shopDetails.get("category").toString());
                                    customerStoreModel.setActiveDays(shopDetails.get("active_days").toString());
                                    customerStoreModel.setAddress(shopDetails.get("address").toString());
                                    customerStoreModel.setShopLogo(shopDetails.get("shop_logo").toString());
                                    customerStoreModel.setMobile(shopDetails.get("mobile").toString());
                                    customerStoreModel.setEmail(shopDetails.get("email").toString());

                                    alCustomerStoreModel.add(customerStoreModel);
                                    customerStoreAdapter.notifyDataSetChanged();

                                    fetchStoreDetails(documents, i+1);
                                }else {
                                    Log.d("error", "Esa koi document nahi hai bhai...");
                                }
                            }
                        }
                    });
        }
    }

    void addStoreDetailsOnFirebase(){
        try {
            HashMap<String, String> storeDetails = new HashMap<>();
            storeDetails.put("shop_name", "KK Cyber Cafe");
            storeDetails.put("shop_owner", "KK");
            storeDetails.put("shop_logo", "Coming Soon");
            storeDetails.put("category", "Cyber Cafe");
            storeDetails.put("email", "kk@gmail.com");
            storeDetails.put("mobile", "1234567890");
            storeDetails.put("active_days", "Mon-Wed");
            storeDetails.put("address", "Aage wali gali, Surat");

            HashMap<String, String> storeDummyField = new HashMap<>();
            storeDummyField.put("dummy", "lorem");

            db.collection("gnr")
                            .document("kk_cyber_cafe")
                                    .set(storeDummyField)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Log.d("dummy", "Dummy stored successfully...");
                                                    }else{
                                                        Log.d("dummy", "Failed to store dummy...");
                                                    }
                                                }
                                            });

            db.collection("gnr").document("kk_cyber_cafe")
                    .collection("Details").document("details_doc")
                    .set(storeDetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("success", "Data stored...");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("error", "Shop data not added...");
                        }
                    });
        } catch (Exception e) {
            Log.d("error", e.toString());
        }

    }
}