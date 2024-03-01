package com.jignesh.shopex.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.jignesh.shopex.ProductsFragment;
import com.jignesh.shopex.models.CustomerStoreModel;
import com.jignesh.shopex.R;

import java.util.ArrayList;

public class CustomerStoreAdapter extends RecyclerView.Adapter<CustomerStoreAdapter.ViewHolder> {

    ArrayList<CustomerStoreModel> alCustomerStoreModel;
    Context context;

    public CustomerStoreAdapter(ArrayList<CustomerStoreModel> alCustomerStoreModel, Context context) {
        this.alCustomerStoreModel = alCustomerStoreModel;
        this.context = context;

        Log.d("ada", "ada cons.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        Toast.makeText(context, "Adapter constructor", Toast.LENGTH_SHORT).show();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            holder.tvShopName.setText(alCustomerStoreModel.get(position).getShopName());
            holder.tvShopCategory.setText(alCustomerStoreModel.get(position).getCategory());
            holder.tvActiveDays.setText(alCustomerStoreModel.get(position).getActiveDays());
            holder.tvAddress.setText(alCustomerStoreModel.get(position).getAddress());
            holder.ivShopLogo.setImageResource(R.mipmap.ic_launcher_foreground);

            holder.ivMobile.setImageResource(R.drawable.ic_baseline_phone_24);

            holder.cvStoreItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment productFragment = new ProductsFragment();
                    Bundle args = new Bundle();
                    args.putString("shopName", alCustomerStoreModel.get(position).getShopName());
                    args.putString("canAdd", "false");
                    productFragment.setArguments(args);

                    FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.c_frameLayout, productFragment);
                    ft.commit();
                }
            });

            holder.ivMobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+alCustomerStoreModel.get(position).getMobile()));
                        context.startActivity(callIntent);
                    } catch (Exception e) {
                        Log.d("error", e.toString());
//                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return alCustomerStoreModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvStoreItem;
        TextView tvShopName, tvShopCategory, tvActiveDays, tvAddress;
        ImageView ivShopLogo, ivMobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvStoreItem = itemView.findViewById(R.id.cv_store_item);
            tvShopName = itemView.findViewById(R.id.tv_shop_name);
            tvShopCategory = itemView.findViewById(R.id.tv_shop_category);
            tvActiveDays = itemView.findViewById(R.id.tv_active_days);
            tvAddress = itemView.findViewById(R.id.tv_address);
            ivShopLogo = itemView.findViewById(R.id.iv_shop_logo);
            ivMobile = itemView.findViewById(R.id.iv_mobile);
        }
    }
}
