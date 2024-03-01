package com.jignesh.shopex.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jignesh.shopex.R;
import com.jignesh.shopex.customer.ui.CustomerAccountFragment;
import com.jignesh.shopex.customer.ui.CustomerHomeFragment;
import com.jignesh.shopex.customer.ui.CustomerMyOrdersFragment;
import com.jignesh.shopex.shopkeeper.ui.ShopkeeperAccountFragment;

public class CustomerActivity extends AppCompatActivity {

    BottomNavigationView customerBNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        try {
            customerBNV = findViewById(R.id.customer_bottomNavigationView);
            replaceFragment(new CustomerHomeFragment());

            customerBNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.c_home:
                            replaceFragment(new CustomerHomeFragment());
                            break;
                        case R.id.c_my_orders:
                            replaceFragment(new CustomerMyOrdersFragment());
                            break;
                        case R.id.c_account:
                            replaceFragment(new CustomerAccountFragment());
                            break;
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            Log.d("bata", e.toString());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.c_frameLayout, fragment);
        ft.commit();
    }

}