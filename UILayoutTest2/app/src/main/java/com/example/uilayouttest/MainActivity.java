package com.example.uilayouttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jumpToRelativeActivity = findViewById(R.id.jump_relative_activity);
        jumpToRelativeActivity.setOnClickListener(this);

        Button jumpToFrameActivity = findViewById(R.id.jump_frame_activity);
        jumpToFrameActivity.setOnClickListener(this);

//        Button jumpToPercentFrameActivity = findViewById(R.id.jump_percent_frame_activity);
//        jumpToPercentFrameActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_relative_activity:
                Intent Rintent = new Intent(MainActivity.this, RelativeActivity.class);
                startActivity(Rintent);
                break;
            case R.id.jump_frame_activity:
                Intent Fintent = new Intent(MainActivity.this, FrameActivity.class);
                startActivity(Fintent);
                break;
//            case R.id.jump_percent_frame_activity:
//                Intent pFintent = new Intent(MainActivity.this, PercentFrameActivity.class);
//                startActivity(pFintent);
            default:
                break;
        }
    }
}
