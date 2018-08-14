package com.android.weici.common.help;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Mouse on 2017/12/4.
 */

public class ViewHelper {

    public static void setClickEffection(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_SCROLL:
                        v.setAlpha(1.0f);
                        break;
                    default:
                        v.setAlpha(1.0f);
                        break;
                }

                return false;
            }
        });
    }

    public static void hiddenViews(View...views){
        for(View v:views){
            v.setVisibility(View.GONE);
        }
    }

    public static void showViews(View...views){
        for(View v:views){
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void showNetStateText(final String videoUrl, final TextView textView){
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                long size = (long) msg.obj;
                String m = "";
                if(size != 0){
                    m = String.format(new Locale(""), "%.2f", size/1024/1024 + size%(1024*1024)/1024/1000f) + "M";
                }
                textView.setText("当前为移动网络,播放将消耗" + m + "流量");
                return false;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(videoUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Accept-Encoding", "identity");
                    conn.connect();
                    long length = conn.getContentLength();
                    conn.disconnect();
                    Message message = new Message();
                    message.obj = length;

                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
