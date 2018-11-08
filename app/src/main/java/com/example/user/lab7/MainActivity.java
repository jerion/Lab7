package com.example.user.lab7;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button start;
    protected SeekBar seek_r, seek_t;
    int rab = 0, tt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start_b);
        seek_r = (SeekBar) findViewById(R.id.seekBar_r);
        seek_t = (SeekBar) findViewById(R.id.seekBar_t);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "開始", Toast.LENGTH_LONG).show();
                runThread();
                runAsyncTask();
            }
        });
    }

    private void runThread(){
        rab = 0;
        new Thread(){
            public void run(){
                do{
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    rab += (int) (Math.random()*3);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }while(rab <= 100);
            }
        }.start();
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    seek_r.setProgress(rab);
                    break;
            }

            if (rab >= 100) {
                if (tt < 100) {
                    Toast.makeText(MainActivity.this, "兔子完成", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private void runAsyncTask()
    {
        new AsyncTask<Void, Integer, Boolean>()
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                tt = 0;
            }

            @Override
            protected Boolean doInBackground(Void... voids)
            {
                do
                {
                    try
                    {
                        Thread.sleep(100);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    tt += (int) (Math.random()*3);
                    publishProgress(tt);
                }while (tt <= 100);
                return  true;
            }

            @Override
            protected void onProgressUpdate(Integer... values)
            {
                super.onProgressUpdate(values);
                seek_t.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean status)
            {
                if (rab < 100)
                {
                    Toast.makeText(MainActivity.this, "烏龜獲勝", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}
