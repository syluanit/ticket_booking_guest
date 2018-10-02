package com.example.syluanit.test_layout;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListOnline extends AppCompatActivity {

    //irebase
    DatabaseReference onlineRef, currentUserRef, counterRef;
    FirebaseRecyclerAdapter<User, ListOnlineViewHolder> adapter;

    //View
    RecyclerView listOnline;
    RecyclerView.LayoutManager layoutManager;

    //Location
    private static final int MY_LOCATION_REQUEST_CODE = 7171;
    private static final int PLAY_SERVICE_RES_REQUEST = 7172;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_online);

        //Init View
        listOnline = (RecyclerView) findViewById(R.id.listOnline);
        listOnline.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listOnline.setLayoutManager(layoutManager);

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Android Presense System");
        setSupportActionBar(toolbar);

        //irebase
        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        counterRef = FirebaseDatabase.getInstance().getReference("lastOnline");
        currentUserRef = FirebaseDatabase.getInstance().getReference("lastOnline").
            child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        setupSystem();
        //ater setup system, we just load all user rom countRe and display on recyclerview
        //This is online list
        updateList();
    }

    private void updateList() {
        adapter = new FirebaseRecyclerAdapter<User, ListOnlineViewHolder>(
                User.class,
                R.layout.user_layout,
                ListOnlineViewHolder.class,
                counterRef
        ) {
            @Override
            protected void populateViewHolder(ListOnlineViewHolder viewHolder, User model, int position) {
                viewHolder.tv_Email.setText(model.getEmail());
            }
        };
        adapter.notifyDataSetChanged();
        listOnline.setAdapter(adapter);
    }

    private void setupSystem() {
        onlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class)){
                    currentUserRef.onDisconnect().removeValue();
                    //Set online user in list
                    counterRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(new User (FirebaseAuth.getInstance().getCurrentUser().getEmail(),"Online"));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    Log.d("LOG", "onDataChange: " + user.getEmail() + "is " + user.getStatus());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_join :
                counterRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(new User (FirebaseAuth.getInstance().getCurrentUser().getEmail(),"Online"));
//                adapter.notifyDataSetChanged();
                break;
            case R.id.action_logout:
                currentUserRef.removeValue();
//                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
