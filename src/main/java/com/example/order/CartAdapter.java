package com.example.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;

    public CartAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText("¥" + item.getPrice());
        holder.tvQuantity.setText("数量: " + item.getQuantity());
        holder.ivImage.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        ImageView ivImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivCartItemImage);
            tvName = itemView.findViewById(R.id.tvCartItemName);
            tvPrice = itemView.findViewById(R.id.tvCartItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvCartItemQuantity);
        }
    }
}

