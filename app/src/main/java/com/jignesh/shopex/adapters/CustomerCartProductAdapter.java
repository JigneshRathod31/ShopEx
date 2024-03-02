package com.jignesh.shopex.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jignesh.shopex.R;
import com.jignesh.shopex.models.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerCartProductAdapter extends RecyclerView.Adapter<CustomerCartProductAdapter.ViewHolder> {

    ArrayList<ProductModel> alCustomerCartProductModel;
    Context context;

    FirebaseFirestore db;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;

    public CustomerCartProductAdapter(ArrayList<ProductModel> alCustomerCartProductModel, Context context) {
        this.alCustomerCartProductModel = alCustomerCartProductModel;
        this.context = context;

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public CustomerCartProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_n_orders_product_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerCartProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loadProductImage(alCustomerCartProductModel.get(position).getProductImage(), holder);

        holder.tvCartProductName.setText(alCustomerCartProductModel.get(position).getProductName());
        holder.tvCartShopName.setText(alCustomerCartProductModel.get(position).getShopName());
        holder.tvCartProductPrice.setText("₹"+alCustomerCartProductModel.get(position).getProductPrice());
        holder.tvCartProductCount.setText(alCustomerCartProductModel.get(position).getOrderQuantity());

        holder.ivDecreaseCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productCount = Integer.parseInt(holder.tvCartProductCount.getText().toString());
                if (productCount != 0){
                    holder.tvCartProductCount.setText(String.valueOf(productCount-1));
                }
            }
        });

        holder.ivIncreaseCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int productCount = Integer.parseInt(holder.tvCartProductCount.getText().toString());
                int productQuantity = Integer.parseInt(alCustomerCartProductModel.get(position).getProductQuantity());

                if (productCount > productQuantity){
                    Toast.makeText(context, "Out of stock!", Toast.LENGTH_SHORT).show();
                }else {
                    holder.tvCartProductCount.setText(String.valueOf(productCount+1));
                }
            }
        });

        holder.ivRemoveCartProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    deleteCartProductFromFirebase(alCustomerCartProductModel.get(position).getProductName());
                    alCustomerCartProductModel.remove(position);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d("error", e.toString());
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alCustomerCartProductModel.size();
    }

    void loadProductImage(String productImage, @NonNull CustomerCartProductAdapter.ViewHolder holder){
        storageRef = firebaseStorage.getReference();
        storageRef.child(productImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context)
                        .load(uri)
                        .into(holder.ivCartProductImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCartProductImage, ivDecreaseCartProduct, ivIncreaseCartProduct, ivRemoveCartProduct;
        TextView tvCartProductName, tvCartShopName, tvCartProductPrice, tvCartProductCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCartProductImage = itemView.findViewById(R.id.iv_cart_product_image);
            ivDecreaseCartProduct = itemView.findViewById(R.id.iv_decrease_cart_product);
            ivIncreaseCartProduct = itemView.findViewById(R.id.iv_increase_cart_product);
            ivRemoveCartProduct = itemView.findViewById(R.id.iv_remove_cart_product);

            tvCartProductName = itemView.findViewById(R.id.tv_cart_product_name);
            tvCartShopName = itemView.findViewById(R.id.tv_cart_shop_name);
            tvCartProductPrice = itemView.findViewById(R.id.tv_cart_product_price);
            tvCartProductCount = itemView.findViewById(R.id.tv_cart_product_count);
        }
    }
}
