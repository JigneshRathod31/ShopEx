package com.jignesh.shopex.shopkeeper.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jignesh.shopex.databinding.FragmentShopkeeperStoreBinding;

public class ShopkeeperStoreFragment extends Fragment {

    RecyclerView rvShopkeeperStores;
    private FragmentShopkeeperStoreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopkeeperStoreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvShopkeeperStores = binding.rvShopkeeperStores;



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}