package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.syluanit.bookingticket_guest.Adapter.Ticket_Info_Adapter;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;

import java.util.ArrayList;

public class TicketBookingInfo extends AppCompatActivity {

    RecyclerView rv_ticket;
    Ticket_Info_Adapter adapter;
    ArrayList<TicketInfo> ticketInfoArrayList;
    Button next;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking_info);

        rv_ticket = (RecyclerView) findViewById(R.id.rv_ticketInfo);
        next = (Button) findViewById(R.id.ticket_back);
        back = (ImageView) findViewById(R.id.ticket_back_pressed);
        rv_ticket.setHasFixedSize(true);
        ticketInfoArrayList = new ArrayList<>();

        adapter = new Ticket_Info_Adapter(this, ticketInfoArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_ticket.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rv_ticket.addItemDecoration(dividerItemDecoration);
        rv_ticket.setAdapter(adapter);
        prepareTicketInfo();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) TicketBookingInfo.this).onBackPressed();
            }
        });
    }

    private void prepareTicketInfo(){
        ticketInfoArrayList.add(new TicketInfo("Họ và tên","" ));
        ticketInfoArrayList.add(new TicketInfo("Email", ""));
        ticketInfoArrayList.add(new TicketInfo("Điện thoại",""));
        ticketInfoArrayList.add(new TicketInfo("Tuyến đi",
                Home.currentTicket.getStartDestination() + " => "
                        + Home.currentTicket.getEndDestination()));
        ticketInfoArrayList.add(new TicketInfo("Ngày đi",Home.currentTicket.getDay()));
        ticketInfoArrayList.add(new TicketInfo("Thời gian",Home.currentTicket.getTimeDep()));
        ticketInfoArrayList.add(new TicketInfo("Vị trí ghế",Home.currentTicket.getSeat()));
        ticketInfoArrayList.add(new TicketInfo("Tổng tiền","140000"));
        adapter.notifyDataSetChanged();
    }
}
