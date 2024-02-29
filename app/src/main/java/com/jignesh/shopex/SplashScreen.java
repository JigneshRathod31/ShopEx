package com.jignesh.shopex;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jignesh.shopex.shopkeeper.ShopkeeperActivity;
import com.jignesh.shopex.utils.GenerateInvoicePDF;

public class SplashScreen extends AppCompatActivity {

    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivLogo = findViewById(R.id.iv_splash_logo);

        Animation animSplash = AnimationUtils.loadAnimation(this, R.anim.splash_animation);

        ivLogo.startAnimation(animSplash);

        animSplash.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
//                GenerateInvoicePDF.generateInvoicePDF(getApplicationContext());
                startActivity(new Intent(SplashScreen.this, ShopkeeperActivity.class));
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });



    }
}