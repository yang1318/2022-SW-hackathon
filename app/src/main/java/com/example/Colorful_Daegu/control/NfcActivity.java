package com.example.Colorful_Daegu.control;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.bumptech.glide.Glide;
import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.Challenge;
import com.example.Colorful_Daegu.model.CommentItem;
import com.example.Colorful_Daegu.model.Post;
import com.example.Colorful_Daegu.model.Reply;
import com.example.Colorful_Daegu.model.Stamp;
import com.example.Colorful_Daegu.model.TouristSpot;
import com.example.Colorful_Daegu.model.User;
import com.example.Colorful_Daegu.view.NfcAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NfcActivity extends AppCompatActivity {

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("Daegu/5");
    private String stampname;
    private ArrayList<Reply> replys;
    private String stampdesc;
    private Challenge challenge;
    ArrayList<CommentItem> list;
    //사용할 컴포넌트 선언
    TextView spot_stamp;
    TextView spot_content;
    ListView listView;
    EditText comment_et;
    Button reg_button;
    String sid;
    String tid;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        Intent intent = getIntent();
        tid = intent.getStringExtra("tid");
        sid = intent.getStringExtra("sid");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

        Map<String, Object> updates = new HashMap<>();

        updates.put("user/"+uid+"/stampCount/", ServerValue.increment(1));
        updates.put("stampState/"+uid+"/"+tid+"/"+sid, 1);
        mDatabase.updateChildren(updates);

        ImageView glide = (ImageView)findViewById(R.id.c_glide);


        spot_stamp =(TextView)findViewById(R.id.spotname);
        spot_content= (TextView)findViewById(R.id.spotcontent);
        listView = findViewById(R.id.listview);
        comment_et =findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);

        list = new ArrayList<>();

        // 댓글 가져오기
        mDatabase.child("touristSpot")
                .child(tid)
                .child("stamps")
                .child(sid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.getResult().getValue() != null) {
                            Stamp stamp = task.getResult().getValue(Stamp.class);

                            // 여기부터는 댓글 생김
                            stampname = stamp.getName();
                            replys = stamp.getReplys();
                            stampdesc = stamp.getDescription();
                            challenge = stamp.getChallenge();
                            ArrayList<Post> posts = challenge.getPost();
                            Glide.with(getApplicationContext()).load(posts.get(0).getPictureUrl()).into(glide);
                            spot_stamp.setText(stampname);
                            spot_content.setText(stampdesc);
                            if(replys.size()!= 0) { //댓글이 있다면 뿌리기

                                for(int i =0;i<replys.size();i++){
                                    CommentItem item = new CommentItem(replys.get(i).getContents(),replys.get(i).getTime());
                                    list.add(item);
                                }
                                ListView listView = findViewById(R.id.listview);
                                NfcAdapter nfcAdapter= new NfcAdapter(list);
                                listView.setAdapter(nfcAdapter);
                            }
                        }
                        else {
                            System.out.println("@@@@@@@@@@실패 ㅠㅠㅠ ");
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    }
                });
        // TODO : 핸드폰으로 검사하기
        reg_button.setOnClickListener(new View.OnClickListener(){ //댓글 버튼 클릭시
            @Override
            public void onClick(View view){
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = dateFormat.format(date);
                Reply new_reply = new Reply(comment_et.getText().toString(),getTime);
                mDatabase.child("touristSpot").child(tid).child("stamps").child(sid).child("replys").push().setValue(new_reply);
                Toast toast = Toast.makeText(getApplicationContext(),"댓글 등록이 완료되었습니다..",Toast.LENGTH_SHORT);

                // TODO : 댓글 목록 새로고침
            }
        });
    }

}
