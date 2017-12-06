package com.bytemecollege.mytube;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;


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
            ImageView tt1 =  v.findViewById(R.id.favImage);
            TextView tt2 =  v.findViewById(R.id.video_Name);
            TextView tt3 =  v.findViewById(R.id.date);
            Button btnFav = v.findViewById(R.id.btnFav);

            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!favList.contains(p)){
                        favList.add(p);
//                        Log.d("FAVLIST", favList.toString());
                        Toast.makeText(context,"Added to favorites!",Toast.LENGTH_SHORT).show();
                        Intent favorite = new Intent(getContext(), VideoActivity.class);
//                        favorite.putExtra("FavList", (Serializable) favList);
//                        context.startActivity(favorite);
                    }
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