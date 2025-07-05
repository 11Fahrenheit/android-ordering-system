package com.example.order;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private LinearLayout dishesContainer;
    private EditText etSearch;

    private List<Dish> dishList = new ArrayList<>();
    private List<View> dishViews = new ArrayList<>();

    static class Dish {
        String name;
        double price;
        int imageResId;

        Dish(String name, double price, int imageResId) {
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
        }
    }

    public MainFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        dishesContainer = view.findViewById(R.id.dishes_container);
        etSearch = view.findViewById(R.id.et_search);

        initDishData();
        initDishViews(inflater);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s,int start,int count,int after){}
            @Override public void onTextChanged(CharSequence s,int start,int before,int count){
                filterDishes(s.toString());
            }
            @Override public void afterTextChanged(Editable s){}
        });

        return view;
    }

    private void initDishData() {
        dishList.clear();
        dishList.add(new Dish("黄金炒饭", 45.0, R.drawable.dish_image1));
        dishList.add(new Dish("魔幻麻婆豆腐",38.0,R.drawable.dish_image2));
        dishList.add(new Dish("炒青江菜",28.0,R.drawable.dish_image3));
        dishList.add(new Dish("鱼香肉丝",48.0,R.drawable.dish_image4));
        dishList.add(new Dish("凤凰翡翠饺子",38.0,R.drawable.dish_image5));
        dishList.add(new Dish("煎饺",25.0,R.drawable.dish_image6));
        dishList.add(new Dish("国士无双面",48.0,R.drawable.dish_image7));
        dishList.add(new Dish("鲤鱼面",108.0,R.drawable.dish_image8));
        dishList.add(new Dish("鲶鱼面",78.0,R.drawable.dish_image9));
        dishList.add(new Dish("酸梅炒饭",58.0,R.drawable.dish_image10));

        // 可继续添加更多菜品
    }

    private void initDishViews(LayoutInflater inflater) {
        dishesContainer.removeAllViews();
        dishViews.clear();

        for (Dish dish : dishList) {
            View itemView = inflater.inflate(R.layout.item_dish, dishesContainer, false);

            ImageView dishImage = itemView.findViewById(R.id.dish_image);
            TextView dishName = itemView.findViewById(R.id.dish_name);
            TextView dishPrice = itemView.findViewById(R.id.dish_price);
            Button btnSelectSpec = itemView.findViewById(R.id.btnSelectSpec);

            dishImage.setImageResource(dish.imageResId);
            dishName.setText(dish.name);
            dishPrice.setText("￥" + dish.price);

            btnSelectSpec.setOnClickListener(v -> showSelectSpecDialog(dish));

            dishesContainer.addView(itemView);
            dishViews.add(itemView);
        }
    }

    private void filterDishes(String keyword) {
        keyword = keyword.toLowerCase().trim();
        for (int i = 0; i < dishList.size(); i++) {
            Dish dish = dishList.get(i);
            View itemView = dishViews.get(i);
            if (dish.name.toLowerCase().contains(keyword)) {
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }

    private void showSelectSpecDialog(Dish dish) {
        if (getContext() == null) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_select_spec, null);

        RadioGroup rgSpicy = dialogView.findViewById(R.id.radioGroupSpice);
        EditText etRemarks = dialogView.findViewById(R.id.editNote);

        new AlertDialog.Builder(getContext())
                .setTitle("选择规格 - " + dish.name)
                .setView(dialogView)
                .setPositiveButton("确定", (dialog, which) -> {
                    int selectedId = rgSpicy.getCheckedRadioButtonId();
                    String spicy = "不辣";
                    if (selectedId == R.id.rb_none) spicy = "不辣";
                    else if (selectedId == R.id.rb_mild) spicy = "微辣";
                    else if (selectedId == R.id.rb_medium) spicy = "中辣";
                    else if (selectedId == R.id.rb_spicy) spicy = "麻辣";

                    String remarks = etRemarks.getText().toString().trim();

                    addToCart(dish, spicy, remarks);

                    Toast.makeText(getContext(),
                            "已加入购物车\n规格：" + spicy + "\n备注：" + (remarks.isEmpty() ? "无" : remarks),
                            Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void addToCart(Dish dish, String spicy, String remarks) {
        String note = "规格：" + spicy;
        if (!remarks.isEmpty()) {
            note += "，备注：" + remarks;
        }

        CartItem newItem = new CartItem(dish.name, dish.price, dish.imageResId);
        Log.d("CartDebug", "添加菜品：" + dish.name + "，图片ID: " + dish.imageResId);

        newItem.setNote(note);

        // 调用单例购物车添加
        ShoppingCart.getInstance().addItem(newItem);
    }
}
