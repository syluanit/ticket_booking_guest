package com.example.syluanit.bookingticket_guest.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syluanit.bookingticket_guest.Adapter.Ticket_Info_Adapter;
import com.example.syluanit.bookingticket_guest.Model.TicketInfo;
import com.example.syluanit.bookingticket_guest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TicketBookingInfo extends AppCompatActivity {

    RecyclerView rv_ticket;
    Ticket_Info_Adapter adapter;
    ArrayList<TicketInfo> ticketInfoArrayList;
    Button book;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking_info);

        rv_ticket = (RecyclerView) findViewById(R.id.rv_ticketInfo);
        book = (Button) findViewById(R.id.btn_bookticket);
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

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TicketBookingInfo.this);
                dialog.setContentView(R.layout.dialog_no_seat_selected);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView content = (TextView) dialog.findViewById(R.id.tv_content);
                content.setText("Đặt vé thành công!");
                TextView OK = (TextView) dialog.findViewById(R.id.tv_OK);
                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TicketBookingInfo.this, Home.class);
                        startActivity(intent);
                    }
                });
                dialog.show();

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
        String seat1 = "";
        for (int i = 0; i < Home.currentTicket.getSeat().size(); i++) {
            if (i != Home.currentTicket.getSeat().size() - 1) {
                seat1 += (Home.currentTicket.getSeat().get(i).toString() + ", ");
            } else seat1 += (Home.currentTicket.getSeat().get(i).toString() + ".");
        }
        ticketInfoArrayList.add(new TicketInfo("Vị trí ghế",seat1));

        ticketInfoArrayList.add(new TicketInfo("Tổng tiền","140000"));
        adapter.notifyDataSetChanged();
    }
}
