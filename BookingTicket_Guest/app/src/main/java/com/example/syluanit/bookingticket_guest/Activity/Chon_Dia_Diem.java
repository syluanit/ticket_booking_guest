package com.example.syluanit.bookingticket_guest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Adapter.Chon_Dia_Diem_Adapter;
import com.example.syluanit.bookingticket_guest.Model.DiaDiem;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class Chon_Dia_Diem extends AppCompatActivity {

    ListView listView;
    TextView tvKhongCoDiaDiem;
    EditText etSearch;
    Chon_Dia_Diem_Adapter chon_dia_diem_adapter;
    ArrayList<DiaDiem> diaDiemArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon__dia__diem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chọn Địa Điểm");

        listView = (ListView) findViewById(R.id.lv_chon_dia_diem);
        tvKhongCoDiaDiem = (TextView) findViewById(R.id.tvKhongCoDiaDiem);

        diaDiemArrayList = new ArrayList<>();
        diaDiemArrayList.add(new DiaDiem("Binh Định",R.mipmap.ic_dia_diem));
        diaDiemArrayList.add(new DiaDiem("Đa Nang",R.mipmap.ic_dia_diem));
        diaDiemArrayList.add(new DiaDiem("Nha Trang",R.mipmap.ic_dia_diem));
        diaDiemArrayList.add(new DiaDiem("Sai Gon",R.mipmap.ic_dia_diem));
        diaDiemArrayList.add(new DiaDiem("Ha Noi",R.mipmap.ic_dia_diem));
        chon_dia_diem_adapter = new Chon_Dia_Diem_Adapter(this, R.layout.dong_dia_diem ,diaDiemArrayList);
        listView.setAdapter(chon_dia_diem_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dia_diem = diaDiemArrayList.get(position).getPlace();
                Intent intent = new Intent();
                intent.putExtra("diadiem", dia_diem);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Tìm địa điểm...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("BBB", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
