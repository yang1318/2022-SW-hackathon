package com.example.Colorful_Daegu.control;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.RankItem;
import com.example.Colorful_Daegu.view.RankAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RankActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<RankItem> ranks;
    private RankAdapter rankAdapter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Daegu/3/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ranks = new ArrayList<>();

        ref.child("user").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(DataSnapshot snapshot : task.getResult().getChildren()){
                    ranks.add(snapshot.getValue(RankItem.class));
                }
                System.out.println(ranks);
            }
        });

        rankAdapter = new RankAdapter(ranks);
        recyclerView.setAdapter(rankAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
