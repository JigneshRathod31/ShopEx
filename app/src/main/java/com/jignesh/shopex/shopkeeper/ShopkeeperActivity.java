package com.jignesh.shopex.shopkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jignesh.shopex.ProductsFragment;
import com.jignesh.shopex.R;
import com.jignesh.shopex.customer.ui.CustomerAccountFragment;
import com.jignesh.shopex.customer.ui.CustomerHomeFragment;
import com.jignesh.shopex.customer.ui.CustomerMyOrdersFragment;
import com.jignesh.shopex.shopkeeper.ui.ShopkeeperAccountFragment;
import com.jignesh.shopex.shopkeeper.ui.ShopkeeperDashboardFragment;
import com.jignesh.shopex.shopkeeper.ui.ShopkeeperOrderRequestsFragment;
import com.jignesh.shopex.shopkeeper.ui.ShopkeeperStoreFragment;
import com.jignesh.shopex.utils.SendNotification;

public class ShopkeeperActivity extends AppCompatActivity {

    BottomNavigationView shopkeeperBNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper);

        shopkeeperBNV = findViewById(R.id.shopkeeper_bottomNavigationView);
        replaceFragment(new ShopkeeperDashboardFragment());

        shopkeeperBNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.s_dashboard:
                        replaceFragment(new ShopkeeperDashboardFragment());
                        break;
                    case R.id.s_store:
                        Fragment productFragment = new ProductsFragment();
                        Bundle args = new Bundle();
                        args.putString("shopName", "pnp");
                        args.putString("canAdd", "true");
                        productFragment.setArguments(args);

                        replaceFragment(productFragment);
                        break;
                    case R.id.s_account:
                        replaceFragment(new ShopkeeperAccountFragment());
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopkeeper_order_request_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.order_request_menu_item){
            replaceFragment(new ShopkeeperOrderRequestsFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.s_frameLayout, fragment);
        ft.commit();
    }

}