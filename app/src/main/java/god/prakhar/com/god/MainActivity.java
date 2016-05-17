package god.prakhar.com.god;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://hindi.pythonanywhere.com/home/";
    private String temp = URL_FEED;
    String propic = "https://upload.wikimedia.org/wikipedia/en/7/70/Shawn_Tok_Profile.jpg";
    ProgressDialog pDialog;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);
        makeJsonObjectRequest();
        URL_FEED = temp;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                intent = new Intent(MainActivity.this,Expand_Post.class);

//                FeedItem data = feedItems.get(position);

                String uname = feedItems.get(position).getName();
                String date = feedItems.get(position).getDate();
                String title = feedItems.get(position).getTitle();
                String content = feedItems.get(position).getContent();
                int type = feedItems.get(position).getType();

                intent.putExtra("username", uname);
                intent.putExtra("date", date);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("type", type);

//                startActivity(intent);
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void makeJsonObjectRequest() {

        showpDialog();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FEED,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
    //
                        Log.d("Prakhar",response);

                        JSONObject obj1 = new JSONObject(response);
    //                    int no_of_posts = Integer.parseInt(obj1.getString("count"));
                        JSONArray unames = new JSONArray(obj1.getString("admin_name"));
    //                    JSONArray post_dates = new JSONArray(obj1.getString("post_date"));
                        JSONArray posts = new JSONArray(obj1.getString("post"));
    //                    JSONArray reply_count = new JSONArray(obj1.getString("reply_count"));


                        for (int i = 0; i < 5 ; i++)
                        {
                            FeedItem item = new FeedItem();

                            item.setProfilePic(propic);
                            String name = unames.get(i).toString();
                            item.setName(name);
    //                        String date = post_dates.get(i).toString();
    //                        item.setDate(date);
                            JSONObject obj2 = new JSONObject(posts.get(i).toString());
                            String title = obj2.getString("title");
                            item.setTitle(title);
                            String content = obj2.getString("content");
                            item.setContent(content);
    //                        String comments = reply_count.get(i).toString();
    //                        item.setComments(comments);

                            item.setType(0);

                            feedItems.add(item);
                        }


                        // notify data changes to list adapater
                        listAdapter.notifyDataSetChanged();
                        hidepDialog();

                    }catch (Exception e)
                    {
                        Toast.makeText(getApplication(), "Something went wrong. Please try again :/", Toast.LENGTH_SHORT).show();
                        System.out.println(e);
                        hidepDialog();
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
            });
            // Add the request to the RequestQueue.
            AppController.getInstance().addToRequestQueue(stringRequest);
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
