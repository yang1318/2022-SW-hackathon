package com.example.Colorful_Daegu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.RankItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView rankNum;
        TextView userName;
        TextView stampNum;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            rankNum = (TextView) itemView.findViewById(R.id.rank_num);
            userName = (TextView) itemView.findViewById(R.id.name);
            stampNum = (TextView) itemView.findViewById(R.id.stampCount);
        }
    }

    private ArrayList<RankItem> ranks = null;


    public RankAdapter(ArrayList<RankItem> rankItems) {
        this.ranks = rankItems;
    }

    @NonNull
    @Override
    public RankAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_custom_rank,parent,false);
        return new RankAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.viewHolder holder, int position) {
        RankItem item = ranks.get(position);

        holder.rankNum.setText(String.valueOf(position));
        holder.userName.setText(item.getName());
        holder.stampNum.setText(String.valueOf(item.getStampNum()));
    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }
}
