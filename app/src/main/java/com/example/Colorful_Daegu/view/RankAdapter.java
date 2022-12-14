package com.example.Colorful_Daegu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView rankNum;
        TextView userName;
        TextView stampNum;
        TextView me;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            rankNum = (TextView) itemView.findViewById(R.id.rank_num);
            userName = (TextView) itemView.findViewById(R.id.rank_name);
            stampNum = (TextView) itemView.findViewById(R.id.collect_stamp);
            me = (TextView) itemView.findViewById(R.id.me);
        }
    }

    private ArrayList<User> ranks;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public RankAdapter(ArrayList<User> rankItems) {
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
        User item = ranks.get(position);

        if(user.getEmail().equals(item.getName())){
            holder.me.setVisibility(View.VISIBLE);
        }else{
            holder.me.setVisibility(View.GONE);
        }
        holder.rankNum.setText(String.valueOf(position+1));
        holder.userName.setText(item.getName());
        holder.stampNum.setText(String.valueOf(item.getStampCount()));
    }

    @Override
    public int getItemCount() {
        System.out.println("?????????; "+ranks.size());
        return ranks.size();
    }
}
