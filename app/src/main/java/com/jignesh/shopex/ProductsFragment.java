package com.jignesh.shopex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jignesh.shopex.adapters.CustomerStoreAdapter;
import com.jignesh.shopex.adapters.StoreProductAdapter;
import com.jignesh.shopex.models.CustomerStoreModel;
import com.jignesh.shopex.models.ProductModel;
import com.jignesh.shopex.shopkeeper.ui.ShopkeeperStoreFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    RecyclerView rvProducts;
    StoreProductAdapter storeProductAdapter;
    ArrayList<ProductModel> alStoreProductModel;

    FirebaseFirestore db;
    FloatingActionButton fabAddProduct;

    private static final String ARG_PARAM1 = "shopName";
    private static final String ARG_PARAM2 = "canAdd";
    private String mShopName;
    private String mCanAdd;


    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
            mShopName = getArguments().getString(ARG_PARAM1);
            mCanAdd = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        try {
            db = FirebaseFirestore.getInstance();
            rvProducts = view.findViewById(R.id.rv_products);
            fabAddProduct = view.findViewById(R.id.fab_add_product);

            if (mCanAdd.equals("true")){
                fabAddProduct.setVisibility(View.VISIBLE);
            }else {
                fabAddProduct.setVisibility(View.GONE);
            }

            alStoreProductModel = new ArrayList<>();
            rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
            storeProductAdapter = new StoreProductAdapter(alStoreProductModel, getContext(), mShopName, mCanAdd);
            rvProducts.setAdapter(storeProductAdapter);

            retrieveProductDataFromFirebase();

            fabAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.s_frameLayout, new ShopkeeperStoreFragment());
                    ft.commit();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    void retrieveProductDataFromFirebase(){
        try {
            db.collection("gnr")
                    .document(mShopName)
                    .collection("Products")
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
                    .document(mShopName)
                    .collection("Products")
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
                                        productModel.setShopName(productDetails.get("shop_name").toString());

                                        if (productDetails.get("review_rate") != null){
                                            productModel.setReviewRate(productDetails.get("review_rate").toString());
                                        }

                                        alStoreProductModel.add(productModel);
                                        storeProductAdapter.notifyDataSetChanged();

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