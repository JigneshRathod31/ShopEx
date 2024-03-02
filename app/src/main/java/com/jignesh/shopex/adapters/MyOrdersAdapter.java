package com.jignesh.shopex.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jignesh.shopex.R;
import com.jignesh.shopex.models.MyOrderModel;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    ArrayList<MyOrderModel> alMyOrdersModel;
    Context context;

    FirebaseFirestore db;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;

    public MyOrdersAdapter(ArrayList<MyOrderModel> alMyOrdersModel, Context context) {
        this.alMyOrdersModel = alMyOrdersModel;
        this.context = context;

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_orders_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loadProductImage(alMyOrdersModel.get(position).getProductImage(), holder);

        holder.tvOrderProductName.setText(alMyOrdersModel.get(position).getProductName());
        holder.tvOrderShopName.setText(alMyOrdersModel.get(position).getShopName());
        holder.tvOrderDate.setText("Date: " + alMyOrdersModel.get(position).getOrderDate());
        holder.tvOrderProductDescription.setText(alMyOrdersModel.get(position).getProductDescription());
        holder.tvOrderQuantity.setText(alMyOrdersModel.get(position).getOrderQuantity());
        holder.tvOrderProductPrice.setText("₹"+alMyOrdersModel.get(position).getProductPrice());

        String deliveryStatus = alMyOrdersModel.get(position).getDeliveryStatus().trim();

        if (deliveryStatus.equals("arrived")){
            holder.ivProductDelivered.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }else {
            holder.ivProductDelivered.setImageResource(R.drawable.ic_baseline_pending_24);
        }

        holder.ivFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBar ratingBar;
                EditText etFeedback;
                Button btnSubmitReview;

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                View adView = LayoutInflater.from(context).inflate(R.layout.review_alert_dialog, null);
                adb.setView(adView);

                ratingBar = adView.findViewById(R.id.product_rating_bar);
                etFeedback = adView.findViewById(R.id.et_product_feedback);
                btnSubmitReview = adView.findViewById(R.id.btn_product_feedback);

                AlertDialog ad = adb.create();
                ad.show();

                btnSubmitReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float rating = ratingBar.getRating();
                        String feedback = etFeedback.getText().toString();

                        addReviewToProduct(alMyOrdersModel.get(position).getProductName(), String.valueOf(rating), feedback);
//                        Toast.makeText(context, rating+", "+feedback, Toast.LENGTH_SHORT).show();
                        ad.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return alMyOrdersModel.size();
    }

    void loadProductImage(String productImage, @NonNull MyOrdersAdapter.ViewHolder holder){
        storageRef = firebaseStorage.getReference();
        storageRef.child(productImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(context)
                        .load(uri)
                        .into(holder.ivOrderProductImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    void addReviewToProduct(String productName, String reviewRate, String feedback){
        try {
            db.collection("gnr")
                    .document("pnp")
                    .collection("Products")
                    .document(productName)
                    .update("review_rate", reviewRate,
                            "review", feedback)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("success", "review updated");
                            }else {
                                Log.d("error", task.getException().toString());
                            }
                        }
                    });
        } catch (Exception e) {
//            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivOrderProductImage, ivProductDelivered, ivFeedback;
        TextView tvOrderProductName, tvOrderShopName, tvOrderDate, tvOrderProductDescription, tvOrderQuantity, tvOrderProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivOrderProductImage = itemView.findViewById(R.id.iv_order_product_image);
            ivProductDelivered = itemView.findViewById(R.id.iv_product_delivered);
            ivFeedback = itemView.findViewById(R.id.iv_feedback);

            tvOrderProductName = itemView.findViewById(R.id.tv_order_product_name);
            tvOrderShopName = itemView.findViewById(R.id.tv_order_shop_name);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvOrderProductDescription = itemView.findViewById(R.id.tv_order_product_description);
            tvOrderQuantity = itemView.findViewById(R.id.tv_order_quantity);
            tvOrderProductPrice = itemView.findViewById(R.id.tv_order_product_price);
        }
    }
}
