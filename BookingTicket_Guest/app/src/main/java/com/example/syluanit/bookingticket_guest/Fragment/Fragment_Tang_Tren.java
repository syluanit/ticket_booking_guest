package com.example.syluanit.bookingticket_guest.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.syluanit.bookingticket_guest.Activity.Home;
import com.example.syluanit.bookingticket_guest.Activity.So_Do_Cho_Ngoi_Activity;
import com.example.syluanit.bookingticket_guest.Adapter.HistoryAdapter;
import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Giuong_Nam_Adapter_Scrolling;
import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Xe_Adapter;
import com.example.syluanit.bookingticket_guest.Adapter.So_Do_Xe_Adapter_Scrolling;
import com.example.syluanit.bookingticket_guest.Model.GheNgoi;
import com.example.syluanit.bookingticket_guest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fragment_Tang_Tren extends Fragment{

    View view;
    private ArrayList<GheNgoi> gheNgoiArrayList;
    public static So_Do_Xe_Adapter_Scrolling adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.giuong_nam, container, false);

        gheNgoiArrayList = new ArrayList<>();

        SetSeatPosition();

        adapter = new So_Do_Xe_Adapter_Scrolling(view.getContext(), gheNgoiArrayList);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_GiuongNam);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void SetSeatPosition(){
        if (So_Do_Cho_Ngoi_Activity.TicketMap != null) {
            try {
                JSONObject jsonObject = new JSONObject(So_Do_Cho_Ngoi_Activity.TicketMap);
                JSONArray jsonArray = jsonObject.getJSONArray("ve");
                JSONArray jsonArraySoDo = jsonObject.getJSONArray("sodo");
                JSONObject jsonObject1 = (JSONObject) jsonArraySoDo.get(0);
                String s = jsonObject1.getString("Sơ_đồ");
                Log.d("AAA", "SetSeatPosition: " + s);
                String[] sodo = s.substring(0,35).split("(?!^)");
                int j = 0;

//                //lịch sử tim kiếm
//                String[] seatID = new String[0];
//                if (HistoryAdapter.checkHistory == 1) {
//                String[] s1 = Home.currentTicket.getSeat()
//                        .substring(0, Home.currentTicket.getSeat().length() - 1)
//                        .split(",");
//                for (int i = 0; i < s1.length; i++) {
//                    s1[i] = s1[i].trim();
//
//                }
//                seatID = s1;
//            }


                for (int i = 0; i < sodo.length; i++) {
                    if (i == 0) {
                        gheNgoiArrayList.add(new GheNgoi(null, R.mipmap.ic_driver, 0, "", 0));
                    } else if (sodo[i].equals("1")) {
                        JSONObject jsonObjectTicket = (JSONObject) jsonArray.get(j);
                        String seatId = jsonObjectTicket.getString("Mã");
                        if (jsonObjectTicket.getString("Trạng_thái").equals("1")) {
                            gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, jsonObjectTicket.getString("Vị_trí_ghế"), 2));
                            j++;
                        } else if (jsonObjectTicket.getString("Trạng_thái").equals("0")) {
//                            int yeah = 0;
//                            if (!(seatID.length == 0)) {
//                                for (int l = 0; l < seatID.length; l++) {
//                                    //ghế lịch sử tim kiếm
//                                    if (seatID[l].equals("A" + j)) {
//                                        gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, "A" + j, 1));
//                                        List<String> list = new ArrayList<String>(Arrays.asList(s));
//                                        list.remove("A" + j);
//                                        seatID = list.toArray(new String[0]);
//                                        yeah = 1;
//                                        j++;
//                                    }
//                                }
//                            }
//                            //ghế trống
//                            if (yeah == 0) {
                                gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, jsonObjectTicket.getString("Vị_trí_ghế"), 0));
                                j++;
//                            }
                        }
                        else if (jsonObjectTicket.getString("Trạng_thái").equals("2")) {
                            gheNgoiArrayList.add(new GheNgoi(seatId, R.drawable.custom_seat, 0, jsonObjectTicket.getString("Vị_trí_ghế"), 2));
                            j++;
                        }
                    } else {
                        gheNgoiArrayList.add(new GheNgoi(0, ""));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    public void SetSeatPositionSpecial (){
//        int j = 1;
//        if (So_Do_Cho_Ngoi_Activity.TicketMap != null){
////        String [] arrayString = So_Do_Cho_Ngoi_Activity.TicketMap.split("");
//            String abc = "1000011000110000001010010011111";
//            String abc1 = abc.substring(1, abc.length());
//            String [] arrayString = abc1.split("(?!^)");
//            String[] s = new String[0];
//
//            if (HistoryAdapter.checkHistory == 1) {
//                String[] s1 = Home.currentTicket.getSeat()
//                        .substring(0, Home.currentTicket.getSeat().length() - 1)
//                        .split(",");
//                for (int i = 0; i < s1.length; i++) {
//                    s1[i] = s1[i].trim();
//
//                }
//                s = s1;
//            }
//        for (int i = 0; i < arrayString.length; i++) {
//                //cửa
//            if (i == 4) {
//                if (arrayString[i].equals("0")){
//                    int yeah = 0;
//                    if (!(s.length == 0)) {
//                        for (int l = 0; l < s.length; l++) {
//                            //ghế lịch sử tim kiếm
//                            if (s[l].equals("A" + j)) {
//                                gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, 0, "A" + j, 1));
//                                List<String> list = new ArrayList<String>(Arrays.asList(s));
//                                list.remove("A" + j);
//                                s = list.toArray(new String[0]);
//                                yeah = 1;
//                                j++;
//                            }
//                        }
//                    }
//                    //ghế trống
//                    if (yeah == 0) {
//                        gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, 0, "A" + j, 0));
//                        j++;
//                    }
//                }
//                else if (arrayString[i].equals("1")){
//                    gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, 1, "A" + j, 2));
//                    j++;
//                }
//            } else if (  i < 25 && (i % 5) % 2 != 0) {
//                //đương đi
//                gheNgoiArrayList.add(new GheNgoi(0, ""));
//            } else {
//                //ghê đã đặt
//                if (arrayString[i].equals("1")) {
//                    gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, 0, "A" + j, 2));
//                    j++;
//                }
//                //ghế chưa đặt
//                else if (arrayString[i].equals("0")){
//                    int yeah = 0;
//                    if (!(s.length == 0)) {
//                        for (int l = 0; l < s.length; l++) {
//                            //ghế lịch sử tim kiếm
//                            if (s[l].equals("A" + j)) {
//                                So_Do_Cho_Ngoi_Activity.currentSeat.add(new GheNgoi(R.drawable.custom_seat, 0, "A" + j, 1));
//                                gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, 0, "A" + j, 1));
//                                List<String> list = new ArrayList<String>(Arrays.asList(s));
//                                list.remove("A" + j);
//                                s = list.toArray(new String[0]);
//                                yeah = 1;
//                                j++;
//                            }
//                        }
//                    }
//                    //ghế trống
//                    if (yeah == 0) {
//                        gheNgoiArrayList.add(new GheNgoi(R.drawable.custom_seat, 0, "A" + j, 0));
//                        j++;
//                    }
//                }
//            }
//
//        }
//        setSeatPositionText();
//        }
//        else {
//            Toast.makeText(getActivity(), "Vui lòng kiểm tra kết nối mạng hoặc thử lại", Toast.LENGTH_SHORT).show();
//        }
//    }

}
