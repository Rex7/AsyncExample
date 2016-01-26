package com.example.regis.asyncexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class AsyncExample extends AppCompatActivity {
    String d="http://www.cdn.actiontrip.com/images/babes/midsize/botd_01202015.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_demo);
        MyAsync as = new MyAsync();
        as.execute(d);

    }
    public InputStream connection(String urls)
    {   InputStream inputStream=null;
        try {
            URL url= new URL(urls);
            URLConnection connection = url.openConnection();
            if(connection instanceof HttpURLConnection)
            {
                HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
                int res=-1;
                httpURLConnection.connect();
                res=httpURLConnection.getResponseCode();
                if(res==HttpURLConnection.HTTP_OK)
                {
                    inputStream=httpURLConnection.getInputStream();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;

    }
    public Bitmap input2Bitmap(InputStream inputStream)
    {
        Bitmap bitmap=null;
        bitmap= BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    public Drawable bitmap2Drawable(Bitmap ds)
    {
        Drawable drawable=null;
        drawable = new BitmapDrawable(getResources(),ds);
        return drawable;
    }
    public class MyAsync extends AsyncTask<String,Void,Drawable>
    {

        @Override
        protected Drawable doInBackground(String... strings) {
            String u=strings[0];
            InputStream ls = connection(u);
            Bitmap bitmap = input2Bitmap(ls);
            Drawable drawable = bitmap2Drawable(bitmap);
            return drawable;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            RelativeLayout ls = (RelativeLayout)findViewById(R.id.Ral);
            ls.setBackgroundDrawable(drawable);
        }
    }
}
