package com.example.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private ImageView ivAvatar;
    private LinearLayout btnCoupons, btnSettings, btnOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivAvatar = view.findViewById(R.id.iv_avatar);
        btnCoupons = view.findViewById(R.id.btn_coupons);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnOrders = view.findViewById(R.id.btn_orders);

        ivAvatar.setOnClickListener(v -> {
            // 可自定义点击头像行为
        });

        // 跳转到 CouponActivity
        btnCoupons.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CouponActivity.class);
            startActivity(intent);
        });

        // 可选的设置功能（占位）
        btnSettings.setOnClickListener(v -> {
            // 示例：Toast 或者打开设置界面
        });

        // 跳转到 OrderActivity
        btnOrders.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
