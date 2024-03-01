package com.jignesh.shopex.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jignesh.shopex.R;
import com.jignesh.shopex.models.ProductModel;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.ViewHolder> {

    ArrayList<ProductModel> alStoreProductModel;
    Context context;
    String shopName;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    public StoreProductAdapter(ArrayList<ProductModel> alStoreProductModel, Context context, String shopName) {
        this.alStoreProductModel = alStoreProductModel;
        this.context = context;
        this.shopName = shopName;
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public StoreProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ivProductImage.setImageResource(R.mipmap.ic_launcher_foreground);

        holder.tvProductName.setText(alStoreProductModel.get(position).getProductName());
        holder.tvProductPrice.setText(alStoreProductModel.get(position).getProductPrice());
        holder.tvProductDescription.setText(alStoreProductModel.get(position).getProductDescription());
        holder.tvProductQuantity.setText(alStoreProductModel.get(position).getProductQuantity());

        holder.ivAddProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> productDetails = new HashMap<>();
                productDetails.put("product_name", alStoreProductModel.get(position).getProductName());
                productDetails.put("product_description", alStoreProductModel.get(position).getProductDescription());
                productDetails.put("product_price", alStoreProductModel.get(position).getProductPrice());
                productDetails.put("product_quantity", alStoreProductModel.get(position).getProductQuantity());
                productDetails.put("product_onboard", alStoreProductModel.get(position).getProductOnboard());
                productDetails.put("product_image", alStoreProductModel.get(position).getProductImage());
                addCardProductOnFirebase(productDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alStoreProductModel.size();
    }

    void addCardProductOnFirebase(HashMap<String, String> productDetails){
        try {
            db.collection("gnr")
                    .document("customer$")
                    .collection("Customers")
                    .document("jk@gmail.com")
                    .collection("Cart")
                    .document(productDetails.get("product_name"))
                    .set(productDetails)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("cart_product", "Product added to cart successfully...");
                            }else{
                                Log.d("cart_product", "Failed to add product in cart...");
                            }
                        }
                    });

        } catch (Exception e) {
            Log.d("error", e.toString());
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage, ivAddProductToCart;
        TextView tvProductName, tvProductDescription, tvProductPrice, tvProductQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            ivAddProductToCart = itemView.findViewById(R.id.iv_add_product_to_cart);

            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductDescription = itemView.findViewById(R.id.tv_product_description);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
        }
    }
}
