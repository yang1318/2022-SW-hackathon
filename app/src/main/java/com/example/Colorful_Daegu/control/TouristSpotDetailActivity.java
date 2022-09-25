package com.example.Colorful_Daegu.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.Stamp;
import com.example.Colorful_Daegu.model.StampState;
import com.example.Colorful_Daegu.model.TouristSpot;
import com.example.Colorful_Daegu.model.TouristSpotDetailItem;
import com.example.Colorful_Daegu.view.TouristSpotDetailAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class TouristSpotDetailActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String spotName;
    private float spotRating;
    private String spotDescription;
    ArrayList<Integer> stampState;
    private TextView tSpotName;
    private TextView tSpotDescription;
    private RatingBar rSpotRating;
    private TextView tAchievementRate;
    private String uid;
    int check;
    ArrayList<TouristSpotDetailItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tourist_spot_detail);
        Intent intent = getIntent();
        String spotNumber = intent.getStringExtra("tid"); // TODO : intent 로 받아온 Tourist spot 정보
        System.out.println("spot: "+spotNumber);
        tSpotName = findViewById(R.id.spotName);
        tSpotDescription = findViewById(R.id.description);
        rSpotRating = findViewById(R.id.rateBar);
        tAchievementRate = findViewById(R.id.achievementRate);
        list = new ArrayList<>();
        check = 0;
        // Tourist spot 정보 가져오기
        //        list.add(new TouristSpotDetailItem("data:image/jpeg;base64,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", "일청담", 1));
//        list.add(new TouristSpotDetailItem("data:image/jpeg;base64,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", "북문", 1));
//        list.add(new TouristSpotDetailItem("data:image/jpeg;base64,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", "센트럴파크", 1));
//        list.add(new TouristSpotDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrm4nPSxZIyAk1uuqTEONa-0u59oiFM2MG1g&usqp=CAU", "대운동장", 0));
//        list.add(new TouristSpotDetailItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRD6vcHKuBV0F2oiyO9M_8UoqnBlCIFkviLGQ&usqp=CAU", "본관", -1));
        mDatabase = FirebaseDatabase.getInstance().getReference("Daegu/final/");
        mDatabase.child("touristSpot").child(spotNumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    TouristSpot touristSpot = task.getResult().getValue(TouristSpot.class);
                    spotName = touristSpot.getName();
                    spotRating = touristSpot.getRating();
                    spotDescription = touristSpot.getDescription();

                    tSpotName.setText(spotName);
                    tSpotDescription.setText(spotDescription);
                    rSpotRating.setRating(spotRating);

                    for(Stamp stamp : touristSpot.getStamps()) {
                        TouristSpotDetailItem item = new TouristSpotDetailItem(stamp.getPictureUrl(), stamp.getName(), 0);
                        list.add(item);
                    }

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        uid = user.getUid();
                    }
                    mDatabase.child("stampState").child(uid).child(spotNumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                int i = 0;
                                int sum = 0;
                                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                                    list.get(i++).setSuccess(dataSnapshot.getValue(Integer.class));
                                    if (dataSnapshot.getValue(Integer.class) > 0) sum++;
                                }

                                StringBuilder sb = new StringBuilder(10);
                                sb.append("  ");
                                sb.append(String.valueOf(sum));
                                sb.append("/");
                                sb.append(String.valueOf(i));
                                sb.append("  ");
                                tAchievementRate.setText(sb);

                                RecyclerView recyclerView = findViewById(R.id.recycler);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(TouristSpotDetailActivity.this,3);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                TouristSpotDetailAdapter touristSpotDetailAdapter = new TouristSpotDetailAdapter(list);
                                recyclerView.setAdapter(touristSpotDetailAdapter);
                            }
                            else {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                        }
                    });

                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),TouristSpotActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}