package com.example.order;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class CartFragment extends Fragment {

    private LinearLayout cartItemContainer;
    private TextView tvTotalItems, tvTotalPrice;
    private Button btnCheckout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartItemContainer = view.findViewById(R.id.cartItemContainer);
        tvTotalItems = view.findViewById(R.id.tvTotalItems);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        updateCartView();

        btnCheckout.setOnClickListener(v -> {
            showCouponDialog();
        });

        return view;
    }

    private void updateCartView() {
        cartItemContainer.removeAllViews();
        List<CartItem> cartItems = ShoppingCart.getInstance().getItemList();

        double totalPrice = 0;
        int totalCount = 0;

        for (CartItem item : cartItems) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_product, cartItemContainer, false);

            TextView tvName = itemView.findViewById(R.id.tvCartItemName);
            TextView tvPrice = itemView.findViewById(R.id.tvCartItemPrice);
            TextView tvQuantity = itemView.findViewById(R.id.tvCartItemQuantity);
            ImageView ivImage = itemView.findViewById(R.id.ivCartItemImage);
            Button btnDecrease = itemView.findViewById(R.id.btnDecrease);
            Button btnIncrease = itemView.findViewById(R.id.btnIncrease);

            tvName.setText(item.getName());
            tvPrice.setText("¥" + item.getPrice());
            tvQuantity.setText(String.valueOf(item.getQuantity()));
            ivImage.setImageResource(item.getImageResId());

            btnDecrease.setOnClickListener(v -> {
                int currentQuantity = item.getQuantity();
                if (currentQuantity > 1) {
                    item.setQuantity(currentQuantity - 1);
                } else {
                    ShoppingCart.getInstance().removeItem(item);
                }
                updateCartView();
            });

            btnIncrease.setOnClickListener(v -> {
                int currentQuantity = item.getQuantity();
                item.setQuantity(currentQuantity + 1);
                updateCartView();
            });

            cartItemContainer.addView(itemView);

            totalPrice += item.getPrice() * item.getQuantity();
            totalCount += item.getQuantity();
        }

        tvTotalItems.setText("共 " + totalCount + " 件");
        tvTotalPrice.setText("合计：¥" + String.format("%.2f", totalPrice));
    }

    private void showCouponDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_coupon_select, null);
        CheckBox cbCoupon5 = dialogView.findViewById(R.id.cbCoupon5);
        CheckBox cbCoupon10 = dialogView.findViewById(R.id.cbCoupon10);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirmCheckout);

        // 互斥选择
        cbCoupon5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) cbCoupon10.setChecked(false);
        });
        cbCoupon10.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) cbCoupon5.setChecked(false);
        });

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

        btnConfirm.setOnClickListener(view -> {
            double discount = 0;
            if (cbCoupon5.isChecked()) {
                discount = 5;
            } else if (cbCoupon10.isChecked()) {
                discount = 10;
            }

            dialog.dismiss();
            showQRCodeDialog(discount);
        });

        dialog.show();
    }

    private void showQRCodeDialog(double discount) {
        View qrView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_payment_qr, null);
        ImageView imgQRCode = qrView.findViewById(R.id.payment_success);
        // 默认二维码图片为 ic_qrcode_sample.png，应放在 drawable 目录

        double originalTotal = calculateTotalPrice();
        double finalTotal = Math.max(0, originalTotal - discount);

        new AlertDialog.Builder(getContext())
                .setTitle("恭喜您！（实付 ¥" + String.format("%.2f", finalTotal) + "）")
                .setView(qrView)
                .setPositiveButton("关闭", null)
                .show();
    }

    private double calculateTotalPrice() {
        List<CartItem> cartItems = ShoppingCart.getInstance().getItemList();
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
