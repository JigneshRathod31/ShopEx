package com.jignesh.shopex.shopkeeper.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jignesh.shopex.ProductsFragment;
import com.jignesh.shopex.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopkeeperStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopkeeperStoreFragment extends Fragment {

    ImageView ivAddProductImage, ivGetProductImage;
    EditText etAddProductName, etAddProductDescription, etAddProductPrice, etAddProductQuantity;
    Button btnAddProduct;
    ProgressBar progressBar;

    int GALLERY_REQ_CODE = 2004;
    Uri imgUri;
    String imageName;
    StorageReference storageReference;

    FirebaseFirestore db;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopkeeperStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopkeeperStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopkeeperStoreFragment newInstance(String param1, String param2) {
        ShopkeeperStoreFragment fragment = new ShopkeeperStoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_shopkeeper_store, container, false);

        try {

        db = FirebaseFirestore.getInstance();

        ivAddProductImage = view.findViewById(R.id.iv_add_product_image);
        ivGetProductImage = view.findViewById(R.id.iv_get_product_image);

        etAddProductName = view.findViewById(R.id.et_add_product_name);
        etAddProductDescription = view.findViewById(R.id.et_add_product_description);
        etAddProductPrice = view.findViewById(R.id.et_add_product_price);
        etAddProductQuantity = view.findViewById(R.id.et_add_product_quantity);

        btnAddProduct = view.findViewById(R.id.btn_add_product);
        progressBar = view.findViewById(R.id.progress_bar);

        ivGetProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inChooseImg = new Intent(Intent.ACTION_PICK);
                inChooseImg.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(inChooseImg, GALLERY_REQ_CODE);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    btnAddProduct.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);

                    uploadProductImageOnStorage();

                    addProductOnFirebase();

                    progressBar.setVisibility(View.GONE);

                    Fragment productFragment = new ProductsFragment();
                    Bundle args = new Bundle();
                    args.putString("shopName", "pnp");
                    args.putString("canAdd", "true");
                    productFragment.setArguments(args);

                    FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.s_frameLayout, productFragment);
                    ft.commit();

                } catch (Exception e) {
                    Log.d("dalle", e.toString());
//                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        } catch (Exception e) {
            Log.d("dalle", e.toString());
        }


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == getActivity().RESULT_OK){
                if (requestCode == GALLERY_REQ_CODE){
                    if (data != null){
                        imgUri = data.getData();
                        ivAddProductImage.setImageURI(data.getData());
                        imageName = imgUri.getLastPathSegment();
                    }
                }
            }
        }catch (Exception e){
            Log.d("dalle", e+"");
        }
    }

    void uploadProductImageOnStorage(){
        try {
            if (imgUri != null){
                storageReference = FirebaseStorage.getInstance().getReference().child("products").child(etAddProductName.getText().toString().trim());

                try{
                    storageReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            try {
                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.d("dalle", e+"");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                } catch (Exception e) {
                    Log.d("dalle", e+"");
                }

//                storageReference.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        try {
//                            if (task.isSuccessful()){
//                                Log.d("success", "Product added successfully");
//                                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Log.d("error", task.getException().toString());
//                                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.d("dalle", e.toString());
//                        }
//                    }
//                });
            }
        } catch (Exception e) {
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    void addProductOnFirebase(){
        try {
            String productName = etAddProductName.getText().toString().trim();
            String productImage = "products/" + productName;
            String productDesc = etAddProductDescription.getText().toString().trim();
            String productPrice = etAddProductPrice.getText().toString().trim();
            String productQty = etAddProductQuantity.getText().toString().trim();
            String productOnboard = "true";

            HashMap<String, String> productDetails = new HashMap<>();
            productDetails.put("product_image", productImage);
            productDetails.put("product_name", productName);
            productDetails.put("product_description", productDesc);
            productDetails.put("product_price", productPrice);
            productDetails.put("product_quantity", productQty);
            productDetails.put("product_onboard", productOnboard);

            db.collection("gnr")
                    .document("pnp")
                    .collection("Products")
                    .document(productName)
                    .set(productDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("success", "Product added successfully");
                            }else {
                                Log.d("error", task.getException().toString());
                            }
                        }
                    });
        } catch (Exception e) {
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}