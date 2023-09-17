package com.example.complexjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ListView listView;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.detailsTitle);


        String url = "https://maruf5682.000webhostapp.com/apps/complex.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            String name = response.getString("name");
                            String phone = response.getString("phone");

                            textView.append(name);
                            textView.append("\n");
                            textView.append(phone);

                            JSONArray jsonArray = response.getJSONArray("video");

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String videoId = jsonObject.getString("videoId");


                                hashMap = new HashMap<>();
                                hashMap.put("title", title);
                                hashMap.put("videoId", videoId);
                                arrayList.add(hashMap);
                            }

                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
                            Log.d("errorCatch", String.valueOf(e));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("error", String.valueOf(error));
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);

    }


    private  class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.item,null);
            TextView title = myView.findViewById(R.id.title);
            ImageView imageView = myView.findViewById(R.id.thumble);

            HashMap<String,String> hashMap1 = arrayList.get(position);
            String title1 = hashMap1.get("title");
            String videoId1 = hashMap1.get("videoId");

            title.setText(title1);



            return myView;
        }
    }
}