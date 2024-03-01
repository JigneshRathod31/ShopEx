package com.jignesh.shopex.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.jignesh.shopex.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CustomerAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerAccountFragment newInstance(String param1, String param2) {
        CustomerAccountFragment fragment = new CustomerAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accFragment = inflater.inflate(R.layout.fragment_customer_account, container, false);

        int[] textInputLayoutIds = {R.id.frg_csm_acc_usr_name, R.id.frg_csm_acc_usr_email, R.id.frg_csm_acc_usr_mob, R.id.frg_csm_acc_usr_add};
        TextInputLayout[] textInputLayouts = new TextInputLayout[textInputLayoutIds.length];

        int i, length = textInputLayoutIds.length;
        for(i = 0; i < length; i++)
            textInputLayouts[i] = accFragment.findViewById(textInputLayoutIds[i]);

        Button frgCsmSaveBtn = accFragment.findViewById(R.id.frg_csm_acc_save_btn);
        frgCsmSaveBtn.setOnClickListener(View -> {
            /** FireStore Code **/
        });

        return accFragment;
    }
}