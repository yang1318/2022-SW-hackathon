package com.example.Colorful_Daegu.control;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Colorful_Daegu.MainActivity;
import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    private NfcAdapter nfcAdapter;
    private boolean nfcCheck;
    private String tid;
    private String sid;
    private Long pressTime = 0L;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build());
    Intent signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build();

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) { /*   로그인 성공    */
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보
            mDatabase =FirebaseDatabase.getInstance().getReference("Daegu/1");
            mDatabase.child("user").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.getResult().getValue() == null) { /* 회원가입 시  */
                        User tuser = new User(user.getEmail(),0);
                        mDatabase.child("user").child(user.getUid()).setValue(tuser);
                        Toast toast = Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else { /*   로그인    */
                        Toast toast = Toast.makeText(getApplicationContext(),"로그인이 완료되었습니다.",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
            if (nfcCheck) {
                Intent intent = new Intent(getApplicationContext(), NfcActivity.class);
                intent.putExtra("tid", tid);
                intent.putExtra("sid", sid);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getApplicationContext(), TouristSpotActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        } else {/*  로그인 실패   */
            Toast toast = Toast.makeText(this.getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        Button btn1 = (Button)findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signInLauncher.launch(signInIntent);
            }
        });
        performTagOperations(getIntent());
    }

    @Override
    public void onBackPressed() {
        if (pressTime == 0) {
            Toast.makeText(StartActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            pressTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressTime);

            if (seconds > 2000) {
                Toast.makeText(StartActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                pressTime = 0L;
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        performTagOperations(intent);
    }

    private void performTagOperations(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) { // NFC로 실행됨
            NdefMessage ndefMessage = null;
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                }
            }
            byte[] payload = ((NdefMessage)rawMessages[0]).getRecords()[0].getPayload();
            nfcCheck = true;
            tid = "" + (char)(payload[0]);
            sid = "" + (char)(payload[2]);
            Log.d("NFC", "onNewIntent 호출됨");
        }
    }
}
