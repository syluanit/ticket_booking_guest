package com.example.syluanit.bookingticket_guest.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.syluanit.bookingticket_guest.Activity.So_Do_Cho_Ngoi_Activity;
import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Xe_Adapter;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class Fragment_Tang_Duoi extends Fragment {

    View view;
    private ArrayList<GheNgoi> gheNgoiArrayList_tang_duoi;
    public static So_Do_Xe_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.giuong_nam, container, false);

        final GridView gridView = (GridView) view.findViewById(R.id.gv_GheNgoi);
        gheNgoiArrayList_tang_duoi = new ArrayList<>();

        SetSeatPosition();

        adapter = new So_Do_Xe_Adapter(view.getContext(), R.layout.giuong_nam_item, gheNgoiArrayList_tang_duoi);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int seatStatus = gheNgoiArrayList_tang_duoi.get(position).getTrangThai();
                if (seatStatus == 0){
                    So_Do_Cho_Ngoi_Activity.currentSeat.add(gheNgoiArrayList_tang_duoi.get(position));
                    gheNgoiArrayList_tang_duoi.get(position).setTrangThai(1) ;

                } else {
                    for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                        if (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri().equals(gheNgoiArrayList_tang_duoi.get(position).getViTri())) {
                            So_Do_Cho_Ngoi_Activity.currentSeat.remove(i);

                            break;
                        }
                    }
                    gheNgoiArrayList_tang_duoi.get(position).setTrangThai(0);
                }
                setSeatPositionText();
                adapter.notifyDataSetChanged();
            }

        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "" + position , Toast.LENGTH_SHORT).show();
                gheNgoiArrayList_tang_duoi.get(position).setTrangThai(2) ;
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return view;
    }

    public void SetSeatPosition (){
        int j = 1;

        for (int i = 0; i < 30; i++) {
            if (i == 4  ) {
                gheNgoiArrayList_tang_duoi.add(new GheNgoi(R.drawable.custom_seat,1,"B"+ j ,0));
                j++;
            }
            else
            if (i < 25 && (i % 5) % 2 != 0) {
                gheNgoiArrayList_tang_duoi.add(new GheNgoi(0, "" ));
            } else {
                gheNgoiArrayList_tang_duoi.add(new GheNgoi(R.drawable.custom_seat, "B" + j));
                j++;
            }
        }
    }

    private void setSeatPositionText (){
        if (So_Do_Cho_Ngoi_Activity.currentSeat != null) {
            String seat = "";
            for (int i = 0; i < So_Do_Cho_Ngoi_Activity.currentSeat.size(); i++) {
                if (i != So_Do_Cho_Ngoi_Activity.currentSeat.size() - 1) {
                    seat += (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() + ", ");
                } else seat += (So_Do_Cho_Ngoi_Activity.currentSeat.get(i).getViTri() + ".");
            }
            So_Do_Cho_Ngoi_Activity.tv_seatSelected.setText(seat);
        }
    }}
