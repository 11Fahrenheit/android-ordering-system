package com.example.order;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CouponActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        Toolbar toolbar = findViewById(R.id.toolbar_coupon);
        setSupportActionBar(toolbar);

        // 设置返回箭头
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);           // 显示返回箭头
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back); // 自定义返回图标（可选）
        }
    }

    // 返回按钮点击事件
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
