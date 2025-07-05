package com.example.order;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // ← 加入 Toolbar 引用
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置 Toolbar 为 ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar_main); // ← 绑定布局中的 Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("中华小当家——点单系统");
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 默认加载 MainFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();

        // 底部导航栏点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new MainFragment();
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });

        // 设置默认选中项
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}
