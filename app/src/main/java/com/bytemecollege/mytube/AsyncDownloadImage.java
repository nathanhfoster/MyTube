package com.bytemecollege.mytube;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by natei on 12/6/2017.
 */

public class AsyncDownloadImage extends AsyncTask<Object, Object, Object> {
    ImageView iv;
    private HttpURLConnection connection;
    private InputStream is;
    private Bitmap bitmap;

    public AsyncDownloadImage(ImageView mImageView) {
        iv = mImageView;
    }

    @Override
    protected Object doInBackground(Object... params) {
        URL url;
        try {
            url = new URL((String) params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (null != result) {
            iv.setImageBitmap((Bitmap) result);
        } else {
//            iv.setBackgroundResource(R.drawable.ic_launcher);
        }
    }
}
