package com.jignesh.shopex.customer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.R;
import com.jignesh.shopex.adapters.CustomerCartProductAdapter;
import com.jignesh.shopex.adapters.CustomerStoreAdapter;
import com.jignesh.shopex.models.ProductModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    RecyclerView rvCartProducts;
    CustomerCartProductAdapter customerCartProductAdapter;
    ArrayList<ProductModel> alCustomerCartProductModel;

    Button btnBuyNow;

    FirebaseFirestore db;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
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
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        db = FirebaseFirestore.getInstance();
        rvCartProducts = view.findViewById(R.id.rv_cart_products);
        btnBuyNow = view.findViewById(R.id.btn_buy_now);

        alCustomerCartProductModel = new ArrayList<>();
        rvCartProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        customerCartProductAdapter = new CustomerCartProductAdapter(alCustomerCartProductModel, getContext());
        rvCartProducts.setAdapter(customerCartProductAdapter);

        retrieveCartProductsFromFirebase();

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductsToMyOrderOnFirebase(alCustomerCartProductModel.size()-1);
            }
        });

        return view;
    }

    void addProductsToMyOrderOnFirebase(int i){
        if (i >= 0){
            HashMap<String, String> orderDetails = new HashMap<>();
            orderDetails.put("order_date", "02/03/2024");
            orderDetails.put("order_quantity", alCustomerCartProductModel.get(i).getOrderQuantity());
            orderDetails.put("product_name", alCustomerCartProductModel.get(i).getProductName());
            orderDetails.put("product_description", alCustomerCartProductModel.get(i).getProductDescription());
            orderDetails.put("product_image", alCustomerCartProductModel.get(i).getProductImage());
            orderDetails.put("product_price", alCustomerCartProductModel.get(i).getProductPrice());
            orderDetails.put("shop_name", alCustomerCartProductModel.get(i).getShopName());
            orderDetails.put("delivery_status", "pending");

            HashMap<String, String> requestOrderDetails = new HashMap<>();
            requestOrderDetails.put("customer_address", "GEC Gandhinagar, Sector 28.");
            requestOrderDetails.put("customer_name", "JK");
            requestOrderDetails.put("delivery_status", "pending");
            requestOrderDetails.put("product_name", alCustomerCartProductModel.get(i).getProductName());
            requestOrderDetails.put("product_quantity", alCustomerCartProductModel.get(i).getOrderQuantity());

            Calendar calendar = Calendar.getInstance();

            db.collection("gnr")
                            .document("pnp")
                            .collection("OrderRequests")
                            .document(String.valueOf(calendar.getTimeInMillis()))
                            .set(requestOrderDetails)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("success", "Request generated successfully");
                                    }else {
                                        Log.d("error", task.getException().toString());
                                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .collection("MyOrders")
                    .document(alCustomerCartProductModel.get(i).getProductName())
                    .set(orderDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                deleteCartProductFromFirebase(alCustomerCartProductModel.get(i).getProductName());

                                alCustomerCartProductModel.remove(i);
                                customerCartProductAdapter.notifyItemRemoved(i);

                                addProductsToMyOrderOnFirebase(i-1);
                            }else {
                                Log.d("error", task.getException().toString());
                            }
                        }
                    });
        }

    }

    void deleteCartProductFromFirebase(String product){
        db.collection("gnr")
                .document("customer$")
                .collection("Customers")
                .document("jk@gmail.com")
                .collection("Cart")
                .document(product)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("success", "Cart Product deleted successfully");
                        }else {
                            Log.d("error", task.getException().toString());
                        }
                    }
                });
    }

    void retrieveCartProductsFromFirebase(){
        try {
            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .collection("Cart")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){

                                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                                fetchStoreDetails(documents, 0);
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

    void fetchStoreDetails(List<DocumentSnapshot> documents, int i){
        if (i < documents.size()){
            DocumentSnapshot document = documents.get(i);
            String product = document.getId();

            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .collection("Cart")
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
                                        ProductModel productModel = new ProductModel();

                                        productModel.setProductImage(productDetails.get("product_image").toString());
                                        productModel.setProductName(productDetails.get("product_name").toString());
                                        productModel.setProductDescription(productDetails.get("product_description").toString());
                                        productModel.setProductPrice(productDetails.get("product_price").toString());
                                        productModel.setProductQuantity(productDetails.get("product_quantity").toString());
                                        productModel.setProductOnboard(productDetails.get("product_onboard").toString());
                                        productModel.setOrderQuantity(productDetails.get("order_quantity").toString());
                                        productModel.setShopName(productDetails.get("shop_name").toString());
                                        
                                        alCustomerCartProductModel.add(productModel);
                                        customerCartProductAdapter.notifyDataSetChanged();

                                        fetchStoreDetails(documents, i+1);
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