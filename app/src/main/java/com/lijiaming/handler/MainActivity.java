package com.lijiaming.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    public TextView mTextView;
    public Handler mHandler;

    //step1: define a subclass Mhandler extends to Handler and override handleMessage method.
    class Mhandler extends Handler{

        @Override
        public void handleMessage(Message msg){
            //根据不同线程发送过来的信息，执行不同的UI操作
            //根据Message对象的what属性，标识不同的信息
            switch (msg.what){
                case 1:
                    mTextView.setText("执行了线程1的UI操作");
                    break;
                case 2:
                    mTextView.setText("执行了线程2的UI操作");
                    break;
            }
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.show);

        //step2: create handler instance in main thread.
        mHandler = new Mhandler();

        //采取继承实现多线程
        new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                //step3: create needed message object.
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "A";

                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Looper.loop();


            }
        }).start();
    }

}