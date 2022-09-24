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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        FirebaseUser userInformation = FirebaseAuth.getInstance().getCurrentUser();

        User user = new User("박희민",10);

        HashMap<String,ArrayList<Integer>> test = new HashMap<String,ArrayList<Integer>>(){{
            put("0",new ArrayList<Integer>(Arrays.asList(0,0,1,1,1)));
            put("1",new ArrayList<Integer>(Arrays.asList(1,0,1,2,3)));
            put("2",new ArrayList<Integer>(Arrays.asList(1,0,1,2,3)));
        }};

        Post post1 = new Post("c.jsp",10,"uid");
        Post post2 = new Post("aaa",1,"양선아");
        ArrayList<Post> post = new ArrayList<Post>(Arrays.asList(post1,post2));
        HashMap<String, HashMap <String,ArrayList<Integer>>> ttttt = new HashMap<>();
        StampState stampState = new StampState(new HashMap <String,ArrayList<Integer>>(test));
        Reply reply = new Reply("ㅗㅑㅗㅑㅗㅑㅗ", "2020-01-04");
        Stamp stamp = new Stamp("일청담", "asldf", 1, "b.jpg", "이쁘당", new ArrayList<Reply>(Arrays.asList(reply)),new Challenge("최고에ㅛㅇ", post));
        TouristSpot touristSpot = new TouristSpot("경북대", new Location(35.8899242,128.610697), "a.jpg", 4.0F, "경북대학교(한자: 慶北大學校,영어: Kyungpook National University)는 대구광역시와 경상북도에 소재한 국립대학이자, 의과대학, 치과대학, 경상대학, 수의과대학, 간호대학, 약학대학, 사범대학, IT대학, 농업생명과학대학, 법학전문대학원 등 단과대학과 전문대학원을 갖춘 대규모 종합대학이다.",new ArrayList<Stamp>(){{add(stamp);}});
        TouristSpot touristSpot1 = new TouristSpot("이월드", new Location(35.8540255,128.563403), "a.jpg", 3.0F, "영남권 최대 테마파크에서 낭만적인 시간 보내기 ‣ 놀이기구와 전시공간이 공존하는 유럽식 도시공원으로 남녀노소 누구나 즐길 수 있는 공간 ‣ 정문광장을 지나면 우산 빛 로드, 별빛계단, 로맨틱가든 등 83개의 포토존을 만난다. ‣ 83타워 전망대는 밤이 되면 오색찬란한 이월드 야경과 대구 시내 야경이 360도로 펼쳐진다. 별빛축제 기간에는 더욱 화려한 밤을 즐길 수 있다.",new ArrayList<Stamp>(){{add(stamp);}});
        TouristSpot touristSpot2 = new TouristSpot("동성로 스파크", new Location(35.8687847,128.598467), "a.jpg", 3.0F, "대구 랜드마크 신개념 테마파크 쇼핑몰 동성로 스파크에서 만나보실 수 있습니다. 도심 속 옥상에서 10기종의 놀이기구를 즐겨보세요",new ArrayList<Stamp>(){{add(stamp);}});


        mDatabase = FirebaseDatabase.getInstance().getReference("Daegu/4/");
        mDatabase.child("stampState").child(userInformation.getUid()).setValue(stampState); //todo: userId
        mDatabase.child("user").child(userInformation.getUid()).setValue(user);  //todo: userId


//        mDatabase.child("stamp").setValue(stamp);
        mDatabase.child("touristSpot/0").setValue(touristSpot);
        mDatabase.child("touristSpot/1").setValue(touristSpot1);
        mDatabase.child("touristSpot/2").setValue(touristSpot2);





    }
}