package com.example.Colorful_Daegu.control;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.Colorful_Daegu.R;

import org.w3c.dom.Text;

public class CustomDialog extends Dialog {
    private TextView txt_contents;
    private Button downClick;
    @SuppressLint("WrongViewCast")
    public CustomDialog(@NonNull Context context, String contents) {
        super(context);
        setContentView(R.layout.activity_custom_dialog);

        txt_contents=findViewById(R.id.txt_contents);
        txt_contents.setText(contents);
        downClick = findViewById(R.id.btn_shutdown);
        downClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
