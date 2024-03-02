package com.jignesh.shopex.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jignesh.shopex.R;
import com.jignesh.shopex.models.ShopkeeperOrderRequestModel;

import java.util.ArrayList;

public class ShopkeeperOrderRequestAdapter extends RecyclerView.Adapter<ShopkeeperOrderRequestAdapter.ViewHolder> {

    ArrayList<ShopkeeperOrderRequestModel> alShopkeeperOrderRequestModel;
    Context context;

    FirebaseFirestore db;

    public ShopkeeperOrderRequestAdapter(ArrayList<ShopkeeperOrderRequestModel> alShopkeeperOrderRequestModel, Context context) {
        this.alShopkeeperOrderRequestModel = alShopkeeperOrderRequestModel;
        this.context = context;

        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ShopkeeperOrderRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopkeeper_order_requests_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopkeeperOrderRequestAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvOrderRequestProductName.setText(alShopkeeperOrderRequestModel.get(position).getProductName());
        holder.tvOrderRequestCustomerName.setText(alShopkeeperOrderRequestModel.get(position).getCustomerName());
        holder.tvOrderRequestProductQuantity.setText(alShopkeeperOrderRequestModel.get(position).getProductQuantity());
        holder.tvOrderRequestCustomerAddress.setText(alShopkeeperOrderRequestModel.get(position).getCustomerAddress());

        if (alShopkeeperOrderRequestModel.get(position).getDeliveryStatus().equals("delivered")){
            holder.cbDeliver.setVisibility(View.GONE);

            holder.ivOrderRequestDone.setVisibility(View.VISIBLE);
        }

        holder.cbDeliver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.btnDeliveryDone.setEnabled(true);
                }else {
                    holder.btnDeliveryDone.setEnabled(false);
                }
            }
        });

        holder.btnDeliveryDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cbDeliver.setVisibility(View.GONE);

                holder.ivOrderRequestDone.setVisibility(View.VISIBLE);

                holder.btnDeliveryDone.setEnabled(false);

                updateCustomerOrderDeliveryStatus("arrived", alShopkeeperOrderRequestModel.get(position).getProductName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return alShopkeeperOrderRequestModel.size();
    }

    void updateCustomerOrderDeliveryStatus(String status, String productName){
        db.collection("gnr")
                .document("customer$")
                .collection("Customers")
                .document("jk@gmail.com")
                .collection("MyOrders")
                .document(productName)
                .update("delivery_status", status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("success", "Status updated successfully");
                        }else {
                            Log.d("error", task.getException().toString());
                        }
                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderRequestProductName, tvOrderRequestCustomerName, tvOrderRequestProductQuantity,tvOrderRequestCustomerAddress;
        CheckBox cbDeliver;
        ImageView ivOrderRequestDone;
        Button btnDeliveryDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderRequestProductName = itemView.findViewById(R.id.tv_order_request_product_name);
            tvOrderRequestCustomerName = itemView.findViewById(R.id.tv_order_request_customer_name);
            tvOrderRequestProductQuantity = itemView.findViewById(R.id.tv_order_request_product_quantity);
            tvOrderRequestCustomerAddress = itemView.findViewById(R.id.tv_order_request_customer_address);
            cbDeliver = itemView.findViewById(R.id.cb_deliver);
            ivOrderRequestDone = itemView.findViewById(R.id.iv_order_request_done);
            btnDeliveryDone = itemView.findViewById(R.id.btn_delivery_done);
        }
    }
}
