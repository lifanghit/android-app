package com.example.helloworld;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HelloWorldActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HelloWorldActivity";
    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("HelloWorldActivity", "onCreate execute");
        Log.i(TAG, "onCreate: ");
        Button btn1 = findViewById(R.id.button_1);
        btn1.setOnClickListener(this);

        Button btn_alert = findViewById(R.id.alert_button);
        btn_alert.setOnClickListener(this);

        Button btn_progress = findViewById(R.id.progress_button);
        btn_progress.setOnClickListener(this);

        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                String inputText = editText.getText().toString();
                Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.img_2);
                if (progressBar.getVisibility() == View.GONE)
                    progressBar.setVisibility(View.VISIBLE);
                else {
                    progressBar.setVisibility(View.GONE);
                    int progress = progressBar.getProgress();
                    progress += 10;
                    progressBar.setProgress(progress);
                }
                break;
            case R.id.alert_button:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HelloWorldActivity.this);
                alertDialog.setTitle("This is a dialog");
                alertDialog.setMessage("something important");
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                break;
            case R.id.progress_button:
                ProgressDialog progressDialog = new ProgressDialog(HelloWorldActivity.this);
                progressDialog.setTitle("This is a progressDialog");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                break;
            default:
                 break;
        }
    }
}
