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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;
import com.jignesh.shopex.adapters.CustomerStoreAdapter;
import com.jignesh.shopex.models.CustomerStoreModel;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerHomeFragment extends Fragment {

    RecyclerView rvShopkeeperStores;
    CustomerStoreAdapter customerStoreAdapter;
    ArrayList<CustomerStoreModel> alCustomerStoreModel;

    // Firebase Objects
    FirebaseApp firebaseApp;
    FirebaseFirestore db;
    DocumentReference docRef;

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
//            docRef = db.collection("gnr");

            rvShopkeeperStores = root.findViewById(R.id.rv_shopkeeper_stores);

            alCustomerStoreModel = new ArrayList<>();
            alCustomerStoreModel.add(new CustomerStoreModel("Everyday", "Near GH-6, Gandhinagar", "Food Shop", "pnp@gmail.com", "9106258146", "Patnagar Panipuri", "Shailesh Rana", "shopLogo"));
            alCustomerStoreModel.add(new CustomerStoreModel("Everyday", "Near GH-6, Gandhinagar", "Food Shop", "pnp@gmail.com", "9106258146", "Shiv Multi Service", "Shailesh Rana", "shopLogo"));
//            alCustomerStoreModel.add(new CustomerStoreModel("Everyday", "Near GH-6, Gandhinagar", "Food Shop", "pnp@gmail.com", "9106258146", "KK Cyber Cafe", "Shailesh Rana", "shopLogo"));
//            alCustomerStoreModel.add(new CustomerStoreModel("Everyday", "Near GH-6, Gandhinagar", "Food Shop", "pnp@gmail.com", "9106258146", "Radhe Sweets", "Shailesh Rana", "shopLogo"));
            rvShopkeeperStores.setLayoutManager(new LinearLayoutManager(getContext()));
            customerStoreAdapter = new CustomerStoreAdapter(alCustomerStoreModel, getContext());
            rvShopkeeperStores.setAdapter(customerStoreAdapter);

//            storeSomeDummyDataOnFirebase();
            retrieveDataFromFirebase();

        } catch (Exception e) {
            Log.d("error", e.toString());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    void retrieveDataFromFirebase(){
        try {

//            db.collection("gnr")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
//                            try {
//                                if (task.isSuccessful()){
//                                    for (DocumentSnapshot document: task.getResult()){
//                                        CustomerStoreModel customerStoreModel = new CustomerStoreModel();
//
//                                        customerStoreModel.setShopName(document.get("shop_name").toString());
//                                        customerStoreModel.setShopOwner(document.get("shop_owner").toString());
//                                        customerStoreModel.setActiveDays(document.get("active_days").toString());
//                                        customerStoreModel.setAddress(document.get("address").toString());
//                                        customerStoreModel.setShopLogo(document.get("shop_logo").toString());
//                                        customerStoreModel.setMobile(document.get("mobile").toString());
//                                        customerStoreModel.setEmail(document.get("email").toString());
//
//                                        alCustomerStoreModel.add(customerStoreModel);
//                                        customerStoreAdapter.notifyDataSetChanged();
//                                    }
//                                }else{
//                                    Log.d("error", "Task Failed to retrieve data");
//                                    Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                }
//        } catch (Exception e) {
//            Log.d("error", e.toString());
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
//        }
//        }
//    });
            DocumentReference documentReference = db.collection("gnr").document("pnp").collection("Details").document("details_doc");
//            DocumentReference docRef1 = db.collection("gnr").document();

//            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()){
//                        DocumentSnapshot document = task.getResult();
//                        Toast.makeText(getContext(), document.get("shop_owner").toString(), Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            db.collection("gnr")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    String shop = document.getId();
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
                                                        }else {
                                                            Log.d("error", "Esa koi document nahi hai bhai...");
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("error", e.toString());
        }

//        customerStoreAdapter = new CustomerStoreAdapter(alCustomerStoreModel, getContext());
//        rvShopkeeperStores.setAdapter(customerStoreAdapter);

    }

    void storeSomeDummyDataOnFirebase(){
        try {
            for (int i=0; i<10; i++){
                HashMap<String, String> storeDetails = new HashMap<>();
                storeDetails.put("shop_name", "pnp");
                storeDetails.put("shop_owner", "Shailesh Rana");
                storeDetails.put("shop_logo", "Coming Soon");
                storeDetails.put("category", "Food Shop");
                storeDetails.put("email", "shaileshrana@gmail.com");
                storeDetails.put("mobile", "1234567890");
                storeDetails.put("active_days", "sdkfhkh");
                storeDetails.put("address", "Near GH-6, Gandhinagar");

                db.collection("gnr").document("pnp0")
                        .collection("Details").document("details_doc")
                        .set(storeDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("store", "Data stored successfully");
                                }else {
                                    Log.d("error", task.getException().toString());
                                }
                            }
                        });
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
        }

    }
}