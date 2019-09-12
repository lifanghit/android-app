package com.example.androidthreadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView textView;

    private static final int CHANGE_TEXT = 1;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE_TEXT:
                    textView.setText("Nice to meet you!");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeText = findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
        textView = findViewById(R.id.text_view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText("Nice to meet you!");
                        Message msg = new Message();
                        msg.what = CHANGE_TEXT;
                        handler.sendMessage(msg);
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
