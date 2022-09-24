package com.example.Colorful_Daegu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.CommentItem;

import java.util.ArrayList;
import java.util.List;

public class NfcAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CommentItem> sreplys;

    public NfcAdapter(ArrayList<CommentItem> replys) {
        sreplys = replys;
    }

    @Override
    public int getCount() {
        return sreplys.size();
    }

    @Override
    public Object getItem(int position) {
        return sreplys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        context = parent.getContext();
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_custom_comment, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView commentTextView = (TextView) convertView.findViewById(R.id.cmt_content_tv) ;
        TextView timeTextView = (TextView) convertView.findViewById(R.id.cmt_date_tv) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CommentItem listViewItem = sreplys.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        commentTextView.setText(listViewItem.getComment());
        timeTextView.setText(listViewItem.getTime().toString());

        return convertView;
    }
    public void addItem(String comment,String time){
        CommentItem item = new CommentItem();
        item.setComment(comment);
        item.setTime(time);
        sreplys.add(item);
    }
}
