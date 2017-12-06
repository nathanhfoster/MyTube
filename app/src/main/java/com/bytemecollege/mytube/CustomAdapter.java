package com.bytemecollege.mytube;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<Youtube> {
    private final Context context;
    private final int resourceID;

    public static List<Youtube> getFavList() {
        return favList;
    }

    public void setFavList(List<Youtube> favList) {
        this.favList = favList;
    }

    private static List<Youtube> favList = new ArrayList<Youtube>();


    public CustomAdapter(Context context, int resource, List<Youtube> youtubes) {
        super(context, resource, youtubes);

        this.context = context;
        this.resourceID = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row, null);
        }

        final Youtube p = getItem(position);

        if (p != null) {
            ImageView tt1 =  v.findViewById(R.id.imageView2);
            TextView tt2 =  v.findViewById(R.id.video_Name);
            TextView tt3 =  v.findViewById(R.id.date);
            Button btn = v.findViewById(R.id.btnFav);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favList.add(p);
                }
            });

            tt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent move = new Intent(getContext(), VideoActivity.class);
                    move.putExtra("videoID", p.getVideoId());
                    context.startActivity(move);
                }
            });

            if (tt1 != null) {
                Bitmap bmp = null;
                try {
                    String UrlString = "https://i.ytimg.com/vi/"+p.getVideoId()+"/default.jpg";
//                    URL url = new URL(UrlString);
//                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    new AsyncDownloadImage(tt1).execute(UrlString);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                tt1.setImageBitmap(bmp);
            }

            if (tt2 != null) {
                tt2.setText(p.getTitle());
            }

            if (tt3 != null) {
                tt3.setText(p.getPublishedAt());
            }
        }

        return v;
    }
}