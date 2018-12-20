package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.syluanit.bookingticket_guest.Adapter.Chon_Dia_Diem_Adapter;
import com.example.syluanit.bookingticket_guest.Model.DiaDiem;
import com.example.syluanit.bookingticket_guest.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;

public class Chon_Dia_Diem extends AppCompatActivity {

    ListView listView;
    TextView  noPlace;
    Chon_Dia_Diem_Adapter chon_dia_diem_adapter;
    ArrayList<DiaDiem> diaDiemArrayList;
    ImageView iv_back;
    MaterialSearchView searchView;
    Dialog dialog;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon__dia__diem);

        listView = (ListView) findViewById(R.id.lv_chon_dia_diem);
        noPlace = (TextView) findViewById(R.id.noPlace);
        iv_back = (ImageView) findViewById(R.id.back_pressed);

        diaDiemArrayList = new ArrayList<>();

        String ip = getResources().getString(R.string.ip);
        String address = getResources().getString(R.string.address);
        url = ip + address + "/gettinh";
//        url = "http://192.168.43.218/busmanager/public/gettinh";
        receiveUserData(url);
        showProgressDialog();

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_place);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);


        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                // nếu trở vê không search nữa
                listView = (ListView) findViewById(R.id.lv_chon_dia_diem);
                chon_dia_diem_adapter = new Chon_Dia_Diem_Adapter(getApplicationContext(), R.layout.dong_dia_diem ,diaDiemArrayList);
                listView.setAdapter(chon_dia_diem_adapter);

                // set click event aph ter back search
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
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()){
                    final ArrayList<DiaDiem> diaDiemSearch = new ArrayList<>();
                    for( DiaDiem item:diaDiemArrayList){
                    // compare the item in list with the new text user type
                        if (Normalizer.normalize(item.getPlace().toLowerCase(),Normalizer.Form.NFD)
                                .replaceAll("[^\\p{ASCII}]","").
                              contains(Normalizer.normalize(newText.toLowerCase(),Normalizer.Form.NFD)
                                      .replaceAll("[^\\p{ASCII}]",""))){
                            diaDiemSearch.add(item);
                        }
                        chon_dia_diem_adapter = new Chon_Dia_Diem_Adapter(getApplicationContext(),
                                R.layout.dong_dia_diem ,diaDiemSearch);
                        listView.setAdapter(chon_dia_diem_adapter);
                        // you need set click event again
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String dia_diem = diaDiemSearch.get(position).getPlace();
                                Intent intent = new Intent();
                                intent.putExtra("diadiem", dia_diem);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                }
                else {
                    chon_dia_diem_adapter = new Chon_Dia_Diem_Adapter(getApplicationContext(), R.layout.dong_dia_diem ,diaDiemArrayList);
                    listView.setAdapter(chon_dia_diem_adapter);
                }
                return  true;
            }
        });

    }

    private void receiveUserData (String url){
        RequestQueue requestQueue = Volley.newRequestQueue(Chon_Dia_Diem.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.cancel();
                        if (response != null) {
                            noPlace.setVisibility(View.GONE);
                            try {
                                JSONArray jsonArray = response.getJSONArray("kq");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                    String province = jsonObject.getString("Tên");
                                    Intent intent = getIntent();
                                    if (province.equals(intent.getStringExtra("to")) ||
                                            province.equals(intent.getStringExtra("from")) )
                                    {} else {
                                    diaDiemArrayList.add(new DiaDiem(province));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            chon_dia_diem_adapter.notifyDataSetChanged();
                        }
                        else {
                            noPlace.setVisibility(View.VISIBLE);
                            Toast.makeText(Chon_Dia_Diem.this, "Vui lòng kiểm tra kết nối mạng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                noPlace.setVisibility(View.VISIBLE);
                Log.d("AAA", "onErrorResponse: " + error.toString());
                Toast.makeText(Chon_Dia_Diem.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //search menu
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void showProgressDialog (){
        dialog = new Dialog(Chon_Dia_Diem.this);
        dialog.setContentView(R.layout.progressdialog);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
        final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.Seekbar);
        seekBar.setMax(100);
        progressBar.setMax(100);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        CountDownTimer countDownTimer = new CountDownTimer(10000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current >= progressBar.getMax())
                {
                   current = 0;
                }
                progressBar.setProgress(current + 10);
                seekBar.setProgress(current + 10);
            }

            @Override
            public void onFinish() {
                dialog.cancel();
            }
        };
        countDownTimer.start();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
