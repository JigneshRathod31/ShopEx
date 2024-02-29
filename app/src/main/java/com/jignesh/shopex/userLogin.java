package com.jignesh.shopex;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.jignesh.shopex.customer.CustomerLogin;
import com.jignesh.shopex.shopkeeper.ShopkeeperLogin;

public class userLogin extends AppCompatActivity{

    Button customer_lgn, shop_keeper_lgn;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();

        shop_keeper_lgn.setOnClickListener(View -> {
            shop_keeper_lgn.setBackground(getResources().getDrawable(R.drawable.toggle_bg_user_login));
            shop_keeper_lgn.setTextColor(getResources().getColor(R.color.white));
            customer_lgn.setBackground(getResources().getDrawable(R.drawable.ul_login_btn_wrapper));
            customer_lgn.setTextColor(getResources().getColor(R.color.main_color));
            lottieAnimationView.pauseAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(userLogin.this, ShopkeeperLogin.class));
                }
            },200);
        });

        customer_lgn.setOnClickListener(View -> {
            customer_lgn.setBackground(getResources().getDrawable(R.drawable.toggle_bg_user_login));
            customer_lgn.setTextColor(getResources().getColor(R.color.white));
            shop_keeper_lgn.setBackground(getResources().getDrawable(R.drawable.ul_login_btn_wrapper));
            shop_keeper_lgn.setTextColor(getResources().getColor(R.color.main_color));
            lottieAnimationView.pauseAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(userLogin.this, CustomerLogin.class));
                }
            },200);
        });
    }

    private void init(){
        customer_lgn = findViewById(R.id.UL_customerLGNBtn);
        shop_keeper_lgn = findViewById(R.id.UL_shopKeeperLGNBtn);
        lottieAnimationView = findViewById(R.id.lottie_anim);
    }

}