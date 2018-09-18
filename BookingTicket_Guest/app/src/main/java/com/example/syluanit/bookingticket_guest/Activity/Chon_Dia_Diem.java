package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Adapter.Chon_Dia_Diem_Adapter;
import com.example.syluanit.bookingticket_guest.Model.DiaDiem;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class Chon_Dia_Diem extends AppCompatActivity {

    ListView listView;
    TextView tvKhongCoDiaDiem;
    Chon_Dia_Diem_Adapter chon_dia_diem_adapter;
    ArrayList<DiaDiem> diaDiemArrayList;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon__dia__diem);

        listView = (ListView) findViewById(R.id.lv_chon_dia_diem);
        tvKhongCoDiaDiem = (TextView) findViewById(R.id.tvKhongCoDiaDiem);
        iv_back = (ImageView) findViewById(R.id.back_pressed);

        diaDiemArrayList = new ArrayList<>();
        diaDiemArrayList.add(new DiaDiem("Bình Định"));
        diaDiemArrayList.add(new DiaDiem("Đà Nẵng"));
        diaDiemArrayList.add(new DiaDiem("Nha Trang"));
        diaDiemArrayList.add(new DiaDiem("Sài Gòn"));
        diaDiemArrayList.add(new DiaDiem("Hà Nội"));
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Chon_Dia_Diem.this).onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        SpannableString spanString = new SpannableString(("Tìm địa điểm...").toString());
        int end = spanString.length();
        spanString.setSpan(new RelativeSizeSpan(0.8f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        searchView.setQueryHint(spanString);
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
