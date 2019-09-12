package com.example.websocket;

public class WebSocketManager {
    private static WebSocketManager mInstance;
    private WebSocketManager(){

    }

    public static WebSocketManager getInstance(){
        if(mInstance == null){
            synchronized (WebSocketManager.class){
                if (mInstance == null)
                    mInstance = new WebSocketManager();
            }
        }
        return mInstance;
    }
}
