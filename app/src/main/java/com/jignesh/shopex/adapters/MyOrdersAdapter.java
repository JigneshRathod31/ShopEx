package com.jignesh.shopex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jignesh.shopex.R;
import com.jignesh.shopex.models.MyOrderModel;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    ArrayList<MyOrderModel> alMyOrdersModel;
    Context context;

    public MyOrdersAdapter(ArrayList<MyOrderModel> alMyOrdersModel, Context context) {
        this.alMyOrdersModel = alMyOrdersModel;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_orders_item_ui, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder holder, int position) {
        holder.ivOrderProductImage.setImageResource(R.mipmap.ic_launcher_foreground);

        holder.tvOrderProductName.setText(alMyOrdersModel.get(position).getProductName());
        holder.tvOrderDate.setText("Date: " + alMyOrdersModel.get(position).getOrderDate());
        holder.tvOrderProductDescription.setText(alMyOrdersModel.get(position).getProductDescription());
        holder.tvOrderQuantity.setText(alMyOrdersModel.get(position).getOrderQuantity());
    }

    @Override
    public int getItemCount() {
        return alMyOrdersModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivOrderProductImage, ivProductDelivered;
        TextView tvOrderProductName, tvOrderDate, tvOrderProductDescription, tvOrderQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivOrderProductImage = itemView.findViewById(R.id.iv_order_product_image);
            ivProductDelivered = itemView.findViewById(R.id.iv_product_delivered);

            tvOrderProductName = itemView.findViewById(R.id.tv_order_product_name);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvOrderProductDescription = itemView.findViewById(R.id.tv_order_product_description);
            tvOrderQuantity = itemView.findViewById(R.id.tv_order_quantity);
        }
    }
}
