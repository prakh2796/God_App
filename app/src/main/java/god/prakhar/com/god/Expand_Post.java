package god.prakhar.com.god;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class Expand_Post extends AppCompatActivity {

    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://hindi.pythonanywhere.com/expand/";
    String propic = "https://upload.wikimedia.org/wikipedia/en/7/70/Shawn_Tok_Profile.jpg";
    private String urlJsonObj = "http://hindi.pythonanywhere.com/add_comment/";
    private  String id;
    private  TextView item1;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);

        getClickedPost();
        addcomment();
        makeJsonObjectRequest();

//        listAdapter.notifyDataSetChanged();
    }


    private void getClickedPost()
    {
        FeedItem item = new FeedItem();
        intent = getIntent();

        item.setProfilePic(propic);
        String name = intent.getExtras().getString("username");
        item.setName(name);
        String date = intent.getExtras().getString("date");
        item.setDate(date);
        String title = intent.getExtras().getString("title");
        item.setTitle(title);
        String content = intent.getExtras().getString("content");
        item.setContent(content);
        String comments = "-99";
        item.setComments(comments);
        id = intent.getExtras().getString("id");
        URL_FEED = URL_FEED + id;
        item.setType(0);

        feedItems.add(item);
    }

    private void addcomment()
    {
        FeedItem item = new FeedItem();
        item.setType(2);
        feedItems.add(item);
        item1 = (TextView) findViewById(R.id.cmmt_text);
        Button button = (Button) findViewById(R.id.submit_comment);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                makeJsonObjectRequest1();
//            }
//        });
    }


    private void makeJsonObjectRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FEED,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    System.out.println(response);

                    JSONObject obj1 = new JSONObject(response);
                    String status = obj1.getString("status");

                    if (status.equals("success")) {
                        JSONArray unames = new JSONArray(obj1.getString("user_name"));
                        JSONArray comments = new JSONArray(obj1.getString("comment"));
                        int no_of_posts = Integer.parseInt(obj1.getString("count"));

                        for (int i = 0; i < no_of_posts; i++) {
                            FeedItem item = new FeedItem();

                            String name = unames.get(i).toString();
                            item.setName(name);

                            JSONObject obj2 = new JSONObject(comments.get(i).toString());
//                            String date = obj2.getString("date");
//                            item.setDate(date);
//                        Toast.makeText(getApplication(),date,Toast.LENGTH_LONG).show();

                            String content = obj2.getString("content");
                            item.setContent(content);

                            item.setType(1);

                            feedItems.add(item);
                        }
                        // notify data changes to list adapater
                        listAdapter.notifyDataSetChanged();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(getApplication(), "Something went wrong. Please try again :/", Toast.LENGTH_SHORT).show();
                    System.out.println(e);
//                    hidepDialog();
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error.printStackTrace();
                System.out.println(error);
                Toast.makeText(getApplication(),"Server is not working :(",LENGTH_SHORT).show();
                finish();

            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void makeJsonObjectRequest1() {

        StringRequest sr = new StringRequest(Request.Method.POST, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    if (status.equals("success"))
                    {
                        Toast.makeText(getApplication(),"Comment Added",Toast.LENGTH_SHORT).show();
                        makeJsonObjectRequest();

                    }
                }catch (Exception e)
                {
                    Toast.makeText(getApplication(), "Server Error :(", Toast.LENGTH_SHORT).show();
                }
                //mPostCommentResponse.requestCompleted();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),"No internet connection",LENGTH_SHORT).show();
                //mPostCommentResponse.requestEndedWithError(error);
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("comment",item1.getText().toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
