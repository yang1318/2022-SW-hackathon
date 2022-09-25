package com.example.Colorful_Daegu.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.Colorful_Daegu.MainActivity;
import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.Challenge;
import com.example.Colorful_Daegu.model.Post;
import com.example.Colorful_Daegu.model.Reply;
import com.example.Colorful_Daegu.model.Stamp;
import com.example.Colorful_Daegu.model.StampState;
import com.example.Colorful_Daegu.view.NfcAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NfcActivity extends AppCompatActivity {

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("Daegu/final");
    private String stampname;
    private ArrayList<Reply> replys = new ArrayList<>();
    private String stampdesc;
    private Challenge challenge;
    private Integer count;
    private CustomDialog customDialog;
    ArrayList<Reply> list;
    private String challenge_content;

    //사용할 컴포넌트 선언
    TextView spot_stamp;
    TextView spot_content;
    ListView listView;
    TextView stampCount;
    EditText comment_et;
    Button reg_button;
    ImageView challenge_detail;
    String sid;
    String tid;
    String uid;
    NfcAdapter nfcAdapter;
    ImageView reply_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nfc);

        Intent intent = getIntent();
        tid = intent.getStringExtra("tid");
        sid = intent.getStringExtra("sid");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }



        mDatabase.child("stampState").child(uid).child(tid).child(sid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    int check = task.getResult().getValue(Integer.class);
                    if(check == -1) {
                        Intent intent1 = new Intent(getApplicationContext(), CongrationActivity.class);
                        startActivity(intent1);
                    }
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("user/"+uid+"/stampCount/", ServerValue.increment(1));
                    updates.put("stampState/"+uid+"/"+tid+"/"+sid, 1);
                    mDatabase.updateChildren(updates);
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });

        ImageView glide = (ImageView)findViewById(R.id.c_glide);


        spot_stamp =(TextView)findViewById(R.id.spotname);
        spot_content= (TextView)findViewById(R.id.spotcontent);
        listView = findViewById(R.id.listview);
        comment_et =findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);
        stampCount = findViewById(R.id.stampCount);
        challenge_detail = findViewById(R.id.challenge_detail);
        reply_reload = findViewById(R.id.reply_reload);

        list = new ArrayList<>();

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        reply_reload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                refreshReply();
            }
        });
        challenge_detail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                customDialog = new CustomDialog(NfcActivity.this,challenge_content);
                customDialog.show();
            }
        });
        mDatabase.child("touristSpot")
                .child(tid)
                .child("stamps")
                .child(sid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.getResult().getValue() != null) {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            Stamp stamp = task.getResult().getValue(Stamp.class);
                            stampname = stamp.getName();
                            replys = stamp.getReplys();
                            stampdesc = stamp.getDescription();
                            challenge = stamp.getChallenge();
                            challenge_content = challenge.getDescription();
                            ArrayList<Post> posts = challenge.getPost();
                            Glide.with(getApplicationContext())
                                    .load(posts.get(0).getPictureUrl())
                                    .into(glide);
                            count = posts.get(0).getRecommendNum();

                            stampCount.setText(count.toString());
                            spot_stamp.setText(stampname);
                            spot_content.setText(stampdesc);
                            if(replys.size()!= 0) { //댓글이 있다면 뿌리기
                                ListView listView = findViewById(R.id.listview);
                                nfcAdapter= new NfcAdapter(replys);
                                listView.setAdapter(nfcAdapter);

                                listView = findViewById(R.id.listview);
                                nfcAdapter= new NfcAdapter(replys);
                                listView.setAdapter(nfcAdapter);
                            }
                        }
                        else {
                            System.out.println("@@@@@@@@@@실패 ㅠㅠㅠ ");
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    }
                });
        reg_button.setOnClickListener(new View.OnClickListener(){ //댓글 버튼 클릭시
            @Override
            public void onClick(View view){
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = dateFormat.format(date);
                Reply new_reply = new Reply(comment_et.getText().toString(),getTime);
                replys.add(new_reply);
                mDatabase.child("touristSpot").child(tid).child("stamps").child(sid).child("replys").setValue(replys);
                Toast toast = Toast.makeText(getApplicationContext(),"댓글 등록이 완료되었습니다..",Toast.LENGTH_SHORT);
                refreshReply();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),TouristSpotDetailActivity.class);
        intent.putExtra("tid", tid);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void refreshReply() { // 댓글 새로고침
        replys.clear();
        mDatabase.child("touristSpot").child(tid).child("stamps").child(sid).child("replys").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for(DataSnapshot snapshot : task.getResult().getChildren()) {
                        replys.add(snapshot.getValue(Reply.class));
                    }
                    nfcAdapter.updateReply(replys);
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });
    }
}

