package com.jignesh.shopex.shopkeeper.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jignesh.shopex.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopkeeperDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopkeeperDashboardFragment extends Fragment {

    BarChart barChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopkeeperDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopkeeperDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopkeeperDashboardFragment newInstance(String param1, String param2) {
        ShopkeeperDashboardFragment fragment = new ShopkeeperDashboardFragment();
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
        View dashFragment = inflater.inflate(R.layout.fragment_shopkeeper_dashboard, container, false);
        barChart = dashFragment.findViewById(R.id.barChart);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(400,200));
        data.add(new BarEntry(500,250));
        data.add(new BarEntry(600,300));
        data.add(new BarEntry(700,350));
        data.add(new BarEntry(800,400));
        data.add(new BarEntry(900,450));
        data.add(new BarEntry(1000,500));

        BarDataSet barDataSet = new BarDataSet(data,"Sales");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(50f);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Sales");
        barChart.animateX(2000);

        return dashFragment;
    }
}