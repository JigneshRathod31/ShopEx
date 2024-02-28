package com.jignesh.shopex.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jignesh.shopex.R;
import com.jignesh.shopex.customer.ui.CustomerAccountFragment;
import com.jignesh.shopex.customer.ui.CustomerHomeFragment;
import com.jignesh.shopex.customer.ui.CustomerMyOrdersFragment;

public class CustomerActivity extends AppCompatActivity {

    BottomNavigationView customerBNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

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

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.c_frameLayout, fragment);
        ft.commit();
    }

}