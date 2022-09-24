package com.example.Colorful_Daegu.control;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.Challenge;
import com.example.Colorful_Daegu.model.Post;
import com.example.Colorful_Daegu.model.Reply;
import com.example.Colorful_Daegu.model.Stamp;
import com.example.Colorful_Daegu.model.TouristSpot;
import com.example.Colorful_Daegu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NfcActivity extends AppCompatActivity {

    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("Daegu/3");
    private String stampname;
    private ArrayList<Reply> replys;
    private String stampdesc;
    private Challenge challenge;

    //사용할 컴포넌트 선언
    TextView spot_stamp;
    TextView spot_content;
    ListView listView;
    EditText comment_et;
    Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        String sid = "0"; //stamp아이디
        Integer tid = 0; //관광지 아이디
        spot_stamp =(TextView)findViewById(R.id.spotname);
        spot_content= (TextView)findViewById(R.id.spotcontent);
        listView = findViewById(R.id.comment);
        comment_et =findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);


        mDatabase.child("touristSpot")
                .child(tid.toString())
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

                    spot_stamp.setText(stampname);
                    spot_content.setText(stampdesc);
                    if(!replys.isEmpty()) { //댓글이 있다면 뿌리기
                        ArrayList<String> content = new ArrayList<>();
                        ArrayList<Long> time = new ArrayList<>();
                        for(int i =0;i<replys.size();i++){
                            content.add(replys.get(i).getContents());
                            time.add(replys.get(i).getTime());
                        }

                        ArrayAdapter<String> adapter_content = new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                content);
                        ArrayAdapter<Long> adapter_time = new ArrayAdapter<Long>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                time);
                        listView.setAdapter(adapter_content);
                    }else{
                        System.out.println("데이터 읽기 실패 ");
                        Log.e("firebase", "Error getting data", task.getException());
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
                Reply new_reply = new Reply(comment_et.getText().toString(),now);
                mDatabase.child("touristSpot").child(tid.toString()).child("stamps").child(sid).setValue(new_reply);
                Intent intent = new Intent(getApplicationContext(), NfcActivity.class);
                startActivity(intent);
            }
        });
    }

}
