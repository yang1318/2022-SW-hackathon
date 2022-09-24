package com.example.Colorful_Daegu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.TouristSpotDetailItem;

import java.util.ArrayList;

public class TouristSpotDetailAdapter extends RecyclerView.Adapter<TouristSpotDetailAdapter.BaseViewHolder> {

    private Context context;
    private ArrayList<TouristSpotDetailItem> myList;
    public TouristSpotDetailAdapter(ArrayList<TouristSpotDetailItem> list) {
        myList = list;
    }

    @NonNull
    @Override   // ViewHolder 객체를 생성하여 리턴
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == -1) { // 숨겨진 스탬프
            view = inflater.inflate(R.layout.item_secret_spot_detail, parent, false);
            return new secretDetailViewHolder(view);
        }
        else if (viewType == 1) { // 성공
            view = inflater.inflate(R.layout.item_success_tourist_spot_detail, parent, false);
            return new successDetailViewHolder(view);
        }
        else { // 성공전
            view = inflater.inflate(R.layout.item_tourist_spot_detail, parent, false);
            return new detailViewHolder(view);
        }
    }

    @Override   //ViewHolder 안의 내용을 position에 해당되는 데이터로 교체
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(myList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        TouristSpotDetailItem item = myList.get(position);
        return item.getSuccess();
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    protected abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(TouristSpotDetailItem item);
    }

    public class detailViewHolder extends BaseViewHolder {
        TextView textView;
        ImageView imageView;
        public detailViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stampName);
            imageView = itemView.findViewById(R.id.stampImage);
        }
        @Override
        void bind(TouristSpotDetailItem item) {
            textView.setText(item.getName());
            Glide.with(itemView)
                    .load(item.getPictureUrl())
                    .placeholder(R.drawable.knu_logo)
                    .error(R.drawable.knu_logo)
                    .into(imageView);
        }
    }

    public class successDetailViewHolder extends BaseViewHolder {
        TextView textView;
        ImageView imageView;
        public successDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stampName);
            imageView = itemView.findViewById(R.id.stampImage);
        }
        @Override
        void bind(TouristSpotDetailItem item) {
            textView.setText(item.getName());
            Glide.with(itemView)
                    .load(item.getPictureUrl())
                    .placeholder(R.drawable.knu_logo)
                    .error(R.drawable.knu_logo)
                    .into(imageView);
        }
    }

    public class secretDetailViewHolder extends BaseViewHolder {
        public secretDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        @Override
        void bind(TouristSpotDetailItem item) {
        }
    }


}
