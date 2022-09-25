package com.example.Colorful_Daegu.control;


import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.User;
import com.example.Colorful_Daegu.view.RankAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RankActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User> ranks;
    private RankAdapter rankAdapter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Daegu/final/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rank);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ranks = new ArrayList<User>();

        ref.child("user").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(DataSnapshot snapshot : task.getResult().getChildren()){
                    ranks.add(snapshot.getValue(User.class));
                }
                Collections.sort(ranks);

                System.out.println(ranks.get(0).getName()+"++"+ranks.get(1).getName()+"++"+ranks.get(2).getName());


                rankAdapter = new RankAdapter(ranks);
                recyclerView.setAdapter(rankAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

            }
        });




    }
}
