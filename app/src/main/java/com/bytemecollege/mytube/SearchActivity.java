package com.bytemecollege.mytube;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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



public class SearchActivity extends Fragment implements View.OnClickListener {

    Youtube youtube = new Youtube();
    List<Youtube> youtubes = new ArrayList<Youtube>();

    String[] names = {"Nathan"};
    String[] description = {"Description"};
    int[] images = {1,1,1,1};




    private static final String TAG = "SearchFragment";
    private EditText searchText;
    private Button searchYouTube, favorite;
    private ListView videoListView;
    private ImageView videoImage;
    private TextView videoName;
    private TextView videoDesc;
    private ArrayList<String> row = new ArrayList<>();

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        searchText = view.findViewById(R.id.text_searchFavorites);

        videoListView = view.findViewById(R.id.searchRestultsListView);


        row.add("Testing");
        row.add("Testing2");
        adapter = new CustomAdapter(getActivity(), R.layout.row, youtubes);

        videoListView.setAdapter(adapter);

        CustomAdapter1 customAdapter = new CustomAdapter1();
//        videoListView.setAdapter(customAdapter);

        searchYouTube = view.findViewById(R.id.btn_Search_YouTube);
        searchYouTube.setOnClickListener(this);

//        favorite = view.findViewById(R.id.btnFav);
//        favorite.setOnClickListener(this);



//        searchYouTube.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JsonTask().execute(searchText.getText().toString());
//            }
//        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Search_YouTube:
//                Log.d("Search Button", searchText.getText().toString());
                youtubes.removeAll(youtubes);
                new JsonTask().execute(searchText.getText().toString());
//            case R.id.btnFav:
//                ListView lv = (ListView) v.findViewById(R.id.searchRestultsListView);
//                final int position = lv.getPositionForView((RelativeLayout)v.getParent());
//                favYoutubes.add(youtubes.get(position));
//                Log.d("",youtubes.get(position).getTitle());
            default:
                break;
        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

//            pd = new ProgressDialog(SearchActivity.this);
//            pd.setMessage("Please wait");
//            pd.setCancelable(false);
//            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader bufferedReader = null;
            JsonReader jsonReader = null;

            try {
                URL url = new URL("https://www.googleapis.com/youtube/v3/search?maxResults=20&part=snippet&q="+params[0]+"&type=video&key=AIzaSyAEBoKVGMwjEXMxkH8E4JIFZZBYEOzpT-A");
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

    class CustomAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.search_fragment, null);
            } else {
                itemView = convertView;
            }

//            ImageView image = (ImageView)itemView.findViewById(R.id.imageView2);
//            TextView name = (TextView)itemView.findViewById(R.id.video_Name);
//            TextView desc = (TextView)itemView.findViewById(R.id.date);
//
//            image.setImageResource(images[0]);
//            name.setText(names[0]);
//            desc.setText("2017");
            return itemView;
        }
    }
}
