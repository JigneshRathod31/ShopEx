package com.jignesh.shopex.shopkeeper.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;
import com.jignesh.shopex.adapters.ShopkeeperOrderRequestAdapter;
import com.jignesh.shopex.adapters.StoreProductAdapter;
import com.jignesh.shopex.models.ProductModel;
import com.jignesh.shopex.models.ShopkeeperOrderRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopkeeperOrderRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopkeeperOrderRequestsFragment extends Fragment {

    RecyclerView rvOrderRequests;
    ShopkeeperOrderRequestAdapter shopkeeperOrderRequestAdapter;
    ArrayList<ShopkeeperOrderRequestModel> alShopkeeperOrderRequestModel;

    FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopkeeperOrderRequestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopkeeperOrderRequestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopkeeperOrderRequestsFragment newInstance(String param1, String param2) {
        ShopkeeperOrderRequestsFragment fragment = new ShopkeeperOrderRequestsFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopkeeper_order_requests, container, false);

        db = FirebaseFirestore.getInstance();

        rvOrderRequests = view.findViewById(R.id.rv_order_requests);

        alShopkeeperOrderRequestModel = new ArrayList<>();
        rvOrderRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        shopkeeperOrderRequestAdapter = new ShopkeeperOrderRequestAdapter(alShopkeeperOrderRequestModel, getContext());
        rvOrderRequests.setAdapter(shopkeeperOrderRequestAdapter);

        retrieveOrderRequestsFromFirebase();

        return view;
    }

    void retrieveOrderRequestsFromFirebase(){
        try {
            db.collection("gnr")
                    .document("pnp")
                    .collection("OrderRequests")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){

                                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                                fetchOrderRequestDetails(documents, 0);
                            }else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("error", e.toString());
        }

    }

    void fetchOrderRequestDetails(List<DocumentSnapshot> documents, int i){
        if (i < documents.size()){
            DocumentSnapshot document = documents.get(i);
            String orderRequestId = document.getId();

            db.collection("gnr")
                    .document("pnp")
                    .collection("OrderRequests")
                    .document(orderRequestId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                            try {
                                if (task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()){
                                        Map<String, Object> orderRequestDetails = document.getData();
                                        ShopkeeperOrderRequestModel shopkeeperOrderRequestModel = new ShopkeeperOrderRequestModel();

                                        shopkeeperOrderRequestModel.setProductName(orderRequestDetails.get("product_name").toString());
                                        shopkeeperOrderRequestModel.setCustomerName(orderRequestDetails.get("customer_name").toString());
                                        shopkeeperOrderRequestModel.setProductQuantity(orderRequestDetails.get("product_quantity").toString());
                                        shopkeeperOrderRequestModel.setCustomerAddress(orderRequestDetails.get("customer_address").toString());
                                        shopkeeperOrderRequestModel.setDeliveryStatus(orderRequestDetails.get("delivery_status").toString());

                                        alShopkeeperOrderRequestModel.add(shopkeeperOrderRequestModel);
                                        shopkeeperOrderRequestAdapter.notifyDataSetChanged();

                                        fetchOrderRequestDetails(documents, i+1);
                                    }else {
                                        Log.d("error", "Esa koi document nahi hai bhai...");
                                    }
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}