package com.example.Colorful_Daegu.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
    private DatabaseReference mDatabase;

    // FirebaseUI 활동 결과 계약의 콜백을 등록하는 ActivityResultLauncher
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            /*new AuthUI.IdpConfig.EmailBuilder().build(),*/
            new AuthUI.IdpConfig.GoogleBuilder().build());

    // Create and launch sign-in intent
    Intent signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            //.setLogo(R.drawable.img)      // Set logo drawable
            //.setTheme(R.style.themes)      // Set theme
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
                        User tuser = new User(user.getDisplayName(),0);
                        mDatabase.child("user").child(user.getUid()).setValue(tuser);
                        Toast toast = Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(), NfcActivity.class);
                        startActivity(intent);
                    }
                    else { /*   로그인    */
                        Toast toast = Toast.makeText(getApplicationContext(),"로그인이 완료되었습니다.",Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getApplicationContext(),NfcActivity.class);
                        startActivity(intent);
                    }
                }
            });

            //유저 이름, 유저아이디 디비에 넣기
            //databaseReference.child("user").child().setValue(user.getDisplayName(),user.getUid())


            // ...
        } else {/*  로그인 실패   */
            Toast toast = Toast.makeText(this.getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button btn1 = (Button)findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signInLauncher.launch(signInIntent);
            }
        });


    }
}
