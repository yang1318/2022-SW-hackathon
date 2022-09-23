package com.example.Colorful_Daegu.control;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.Colorful_Daegu.R;

public class NfcActivity extends Activity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private static String tagNum=null;
    private TextView tagDesc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        tagDesc= (TextView) findViewById(R.id.spotname);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //pendingIntent = pendingIntent.getActivity(this,0,intent(this,).addFlags,0);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getAction() == NfcAdapter.ACTION_NDEF_DISCOVERED){
            Tag tag =intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag != null) {
                byte[] tagId = tag.getId();
                tagDesc.setText("TagID: " + toHexString(tagId));
                System.out.println("@@@@@@@@"+toHexString(tagId));
                tagNum = toHexString(tagId);
            }
        }

    }
    public static final String CHARS = "0123456789ABCDEF";
    public static String toHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; ++i) {
            sb.append(CHARS.charAt((data[i] >> 4) & 0x0F)).append(
                    CHARS.charAt(data[i] & 0x0F)
            );
        }
        return sb.toString();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        if(nfcAdapter != null){
//            nfcAdapter
//                    .enableForegroundDispatch(this,pendingIntent,null,null);
//        }
    }

    @Override
    protected void onPause() {
        if(nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this);
        }
        super.onPause();
    }
}
