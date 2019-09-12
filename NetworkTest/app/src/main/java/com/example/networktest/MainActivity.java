package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequestViaHttpUrlConnection = findViewById(R.id.send_request_via_httpURLConnection);
        sendRequestViaHttpUrlConnection.setOnClickListener(this);
        Button sendRequestViaOkHttp = findViewById(R.id.send_request_via_okhttp);
        sendRequestViaOkHttp.setOnClickListener(this);
        responseText = findViewById(R.id.response_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_request_via_httpURLConnection:
                sendRequestWithHttpUrlConnection();
                Log.d(TAG, "onClick: " + view.getId());
                break;
            case R.id.send_request_via_okhttp:
                sendRequestWithOkHttp();
            default:
                break;
        }
    }

    private void sendRequestWithHttpUrlConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                BufferedReader bufferedReader = null;
                try {
//                    URL url = new URL("http://127.0.0.1/get_data.xml"); //本地地址
                    URL url = new URL("http://10.0.2.2/get_data.xml"); //模拟器地址
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);

                    //从服务器获取返回的数据流
                    InputStream inputStream = httpURLConnection.getInputStream();
                    //读取数据流
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
//                    showResponse(response.toString()); //显示返回结果
                    parseXmlWithPull(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null){
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }

            }
        }).start();
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url("http://127.0.0.1/get_data.xml")  //本地地址
                        .url("http://10.0.2.2/get_data.xml")  //模拟器地址
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
//                    showResponse(responseData);
                    parseXmlWithPull(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }

    private void parseXmlWithPull(String xmlData) {
        try {
            XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = pullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();  //得到解析事件
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.d(TAG, "id is: " + id);
                            Log.d(TAG, "name is: " + name);
                            Log.d(TAG, "version is: " + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
