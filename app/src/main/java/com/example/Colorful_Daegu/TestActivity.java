package com.example.Colorful_Daegu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Colorful_Daegu.model.Challenge;
import com.example.Colorful_Daegu.model.Location;
import com.example.Colorful_Daegu.model.Post;
import com.example.Colorful_Daegu.model.Reply;
import com.example.Colorful_Daegu.model.Stamp;
import com.example.Colorful_Daegu.model.StampState;
import com.example.Colorful_Daegu.model.TouristSpot;
import com.example.Colorful_Daegu.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        User user = new User("박희민",10);

        HashMap<String,ArrayList<Integer>> test = new HashMap<String,ArrayList<Integer>>(){{
            put("선아",new ArrayList<Integer>(Arrays.asList(0,0,1)));
            put("다현",new ArrayList<Integer>(Arrays.asList(1,0,1)));
        }};

        Post post1 = new Post("c.jsp",10,"uid");
        Post post2 = new Post("aaa",1,"양선아");
        ArrayList<Post> post = new ArrayList<Post>(Arrays.asList(post1,post2));
        HashMap<String, HashMap <String,ArrayList<Integer>>> ttttt = new HashMap<>();
        ttttt.put("tid", new HashMap <String,ArrayList<Integer>>(test));
        StampState stampState = new StampState(ttttt);
        Reply reply = new Reply("ㅗㅑㅗㅑㅗㅑㅗ", 6);
        Stamp stamp = new Stamp("일청담", "asldf", 1, "b.jpg", "이쁘당", new ArrayList<Reply>(Arrays.asList(reply)),new Challenge("최고에ㅛㅇ", post));
        TouristSpot touristSpot = new TouristSpot("경북대", new Location(128,50), "a.jpg", "5", "몰라용",new ArrayList<Stamp>(){{add(stamp);}});


        mDatabase = FirebaseDatabase.getInstance().getReference("Daegu/0/");
        mDatabase.setValue(stampState);
        mDatabase.child("user").push().setValue(user);


//        mDatabase.child("stamp").setValue(stamp);
        mDatabase.child("touristSpot/tid").setValue(touristSpot);





    }
}
