package com.jignesh.shopex.customer.ui;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;
import com.jignesh.shopex.adapters.CustomerCartProductAdapter;
import com.jignesh.shopex.adapters.MyOrdersAdapter;
import com.jignesh.shopex.models.MyOrderModel;
import com.jignesh.shopex.models.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerMyOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerMyOrdersFragment extends Fragment {

    RecyclerView rvMyOrders;
    MyOrdersAdapter myOrdersAdapter;
    ArrayList<MyOrderModel> alMyOrderModel;

    FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerMyOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerMyOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerMyOrdersFragment newInstance(String param1, String param2) {
        CustomerMyOrdersFragment fragment = new CustomerMyOrdersFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_my_orders, container, false);

        db = FirebaseFirestore.getInstance();
        rvMyOrders = view.findViewById(R.id.rv_my_orders);

        alMyOrderModel = new ArrayList<>();
        rvMyOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        myOrdersAdapter = new MyOrdersAdapter(alMyOrderModel, getContext());
        rvMyOrders.setAdapter(myOrdersAdapter);

        retrieveMyOrdersFromFirebase();

        return view;
    }

    void retrieveMyOrdersFromFirebase(){
        try {
            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .collection("MyOrders")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                                fetchOrderDetails(documents, 0);
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

    void fetchOrderDetails(List<DocumentSnapshot> documents, int i){
        if (i < documents.size()){
            DocumentSnapshot document = documents.get(i);
            String product = document.getId();

            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .collection("MyOrders")
                    .document(product)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                            try {
                                if (task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()){
                                        Map<String, Object> productDetails = document.getData();
                                        MyOrderModel orderModel = new MyOrderModel();

                                        orderModel.setProductImage(productDetails.get("product_image").toString());
                                        orderModel.setProductName(productDetails.get("product_name").toString());
                                        orderModel.setProductDescription(productDetails.get("product_description").toString());
                                        orderModel.setProductPrice(productDetails.get("product_price").toString());
                                        orderModel.setOrderQuantity(productDetails.get("order_quantity").toString());

                                        alMyOrderModel.add(orderModel);
                                        myOrdersAdapter.notifyDataSetChanged();

                                        fetchOrderDetails(documents, i+1);
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