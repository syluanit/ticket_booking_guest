package com.example.syluanit.firebaseproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("Ho Ten").setValue("Nguyen Dang Sy Luan");
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");

        writeNewUser("user1","LuanSy","syluanit@gmail.com");
    }

    //    private void writeNewUser( String name, String email) {
//
//        String id =  mDatabase.push().getKey();
//        User user = new User( name, email, id);
//        mDatabase.child(id).setValue(user);
//        Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
//    }
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
