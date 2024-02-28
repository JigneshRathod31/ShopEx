package com.jignesh.shopex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerStoreAdapter extends RecyclerView.Adapter<CustomerStoreAdapter.ViewHolder> {

    ArrayList<CustomerStoreModel> alCustomerStoreModel;
    Context context;

    public CustomerStoreAdapter(ArrayList<CustomerStoreModel> alCustomerStoreModel, Context context) {
        this.alCustomerStoreModel = alCustomerStoreModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvShopName.setText(alCustomerStoreModel.get(position).shop_name);
        holder.tvShopCategory.setText(alCustomerStoreModel.get(position).category);
        holder.tvActiveTime.setText(alCustomerStoreModel.get(position).active_days);
        holder.tvAddress.setText(alCustomerStoreModel.get(position).address);
        holder.ivShopLogo.setImageResource(R.drawable.ic_launcher_background);

        holder.ivMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+alCustomerStoreModel.get(position).mobile));
//                startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alCustomerStoreModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvShopName, tvShopCategory, tvActiveTime, tvAddress;
        ImageView ivShopLogo, ivMobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShopName = itemView.findViewById(R.id.tv_shop_name);
            tvShopCategory = itemView.findViewById(R.id.tv_shop_category);
            tvActiveTime = itemView.findViewById(R.id.tv_active_time);
            tvAddress = itemView.findViewById(R.id.tv_address);
            ivShopLogo = itemView.findViewById(R.id.iv_shop_logo);
            ivMobile = itemView.findViewById(R.id.iv_mobile);
        }
    }
}
