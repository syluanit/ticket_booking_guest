package com.example.syluanit.bookingticket_guest.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Xe_Adapter;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class Fragment_Ghe_Ngoi extends Fragment {
    View view;
    public ArrayList<GheNgoi> gheNgoiArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hang_ghe_ngoi, container, false);

        final GridView gridView = (GridView) view.findViewById(R.id.gv_GheNgoi);
        gheNgoiArrayList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i < 25 && (i % 5) % 2 != 0) {
                gheNgoiArrayList.add(new GheNgoi(0, "" + i));
            } else {
                gheNgoiArrayList.add(new GheNgoi(R.drawable.front_bus, "" + i));
            }
        }

        final So_Do_Xe_Adapter adapter = new So_Do_Xe_Adapter(view.getContext(), R.layout.dong_ghe_ngoi,gheNgoiArrayList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int seatStatus = gheNgoiArrayList.get(position).getTrangThai();
                if (seatStatus == 0){
                    gheNgoiArrayList.get(position).setTrangThai(1) ;
                } else {
                    gheNgoiArrayList.get(position).setTrangThai(0);
                }
                    adapter.notifyDataSetChanged();
            }

        });

        return view;
    }
}
