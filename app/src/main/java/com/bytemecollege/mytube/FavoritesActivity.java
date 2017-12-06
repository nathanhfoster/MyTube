package com.bytemecollege.mytube;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends Fragment {

    Youtube youtube = new Youtube();
    List<Youtube> youtubes = new ArrayList<Youtube>();

    private List<Youtube> favList;



    private static final String TAG = "FavoriteFragment";

    FavAdapter adapt;


    private ListView videoListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_favorites, container, false);
//        new JsonTask().execute();

        videoListView = view.findViewById(R.id.favVideos);

        adapt = new FavAdapter(getActivity(), R.layout.favsrow, youtubes);

        videoListView.setAdapter(adapt);

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("DELETE", "DELETE");
//            }
//        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("DELETE", "DELETE");
//            }
//        });




        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new JsonTask().execute();
    }

    public List<Youtube> getFavList() {
        return favList;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.delFav:
//                Log.d("DELETE", "DELETE");
//
//            default:
//                break;
//        }
//    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            JsonReader jsonReader = null;

            try {
                URL url = new URL("https://www.googleapis.com/youtube/v3/search?maxResults=20&part=snippet&q=basketball&type=video&key=AIzaSyAEBoKVGMwjEXMxkH8E4JIFZZBYEOzpT-A");
                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();

                InputStream stream = new BufferedInputStream(connection.getInputStream());
//                ReadJsonStreamFromYoutube.abc(connection.getInputStream());
//                jsonReader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
//                InputStream stream = connection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                boolean flag = false;
                String key ="";

                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("id",1)){
//                        Log.d("line", line);
                        youtube = new Youtube();
                        flag = true;
                        continue;
                    }

                    if(flag){
                        if(line.startsWith("videoId",1)){
                            youtube.setVideoId(line);
                        }
                        if(line.startsWith("publishedAt",1)){
                            youtube.setPublishedAt(line);
                        }
                        if(line.startsWith("title",1)){
                            youtube.setTitle(line);
                        }
                        if(line.startsWith("description",1)){
                            youtube.setDescription(line);
                            flag = false;
                        }

                        if(!flag) youtubes.add(youtube);
                    }
//                    stringBuffer.append(line + "\n");
//                    Log.d("line:", line);   //here u ll get whole response...... :-)
                }

                for(int i =0; i<youtubes.size();i++){
                    Log.d("youtobe_video_id", youtubes.get(i).getVideoId());
                    Log.d("youtobe_video_thumbnail", "https://i.ytimg.com/vi/"+youtubes.get(i).getVideoId()+"/default.jpg");
                    Log.d("youtobe_video_title", youtubes.get(i).getTitle());
                    Log.d("youtobe_video_date", youtubes.get(i).getPublishedAt());
                    Log.d("youtobe_video_desc", youtubes.get(i).getDescription());
                }

                return stringBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
