package god.prakhar.com.god;

/**
 * Created by Pewds on 20-Nov-15.
 */

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class FeedListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    String likess;
    ImageButton ib1;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return feedItems.get(position).getType();
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        FeedItem item = feedItems.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        if (type == 0)
        {
            convertView = inflater.inflate(R.layout.feed_item, null);

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView title = (TextView) convertView.findViewById(R.id.txtTitle);
            TextView content = (TextView) convertView.findViewById(R.id.txtContent);
            TextView comments = (TextView) convertView.findViewById(R.id.comments);
            NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);

            name.setText(item.getName());
            date.setText(item.getDate());
            comments.setText(item.getComments());

            // Chcek for empty title
            if (!TextUtils.isEmpty(item.getTitle())) {
                title.setText(item.getTitle());
                title.setVisibility(View.VISIBLE);
            } else {
                // tile is empty, remove from view
                title.setVisibility(View.GONE);
            }

            // Chcek for empty content
            if (!TextUtils.isEmpty(item.getContent())) {
                content.setText(item.getContent());
                content.setVisibility(View.VISIBLE);
            } else {
                // content is empty, remove from view
                content.setVisibility(View.GONE);
            }

            // Chcek for empty date
            if (!TextUtils.isEmpty(item.getTitle())) {
                date.setText(item.getTitle());
                date.setVisibility(View.VISIBLE);
            } else {
                // tile is empty, remove from view
                date.setVisibility(View.GONE);
            }

            profilePic.setImageUrl(item.getProfilePic(), imageLoader);


        }  else if (type == 1)
        {

            convertView = inflater.inflate(R.layout.answers, null);

            TextView cname = (TextView) convertView.findViewById(R.id.cname);
            TextView cdate = (TextView) convertView.findViewById(R.id.cdate);
            TextView ccontent = (TextView) convertView.findViewById(R.id.ctxtContent);
            cname.setText(item.getName());
            cdate.setText(item.getDate());
            ccontent.setText(item.getContent());

            // Chcek for empty title
            if (!TextUtils.isEmpty(item.getName())) {
                cname.setText(item.getName());
                cname.setVisibility(View.VISIBLE);
            } else {
                // tile is empty, remove from view
                cname.setVisibility(View.GONE);
            }

            // Chcek for empty content
            if (!TextUtils.isEmpty(item.getContent())) {
                ccontent.setText(item.getContent());
                ccontent.setVisibility(View.VISIBLE);
            } else {
                // content is empty, remove from view
                ccontent.setVisibility(View.GONE);
            }

            // Chcek for empty date
            if (!TextUtils.isEmpty(item.getTitle())) {
                cdate.setText(item.getTitle());
                cdate.setVisibility(View.VISIBLE);
            } else {
                // tile is empty, remove from view
                cdate.setVisibility(View.GONE);
            }

        } else if (type == 2)
        {
            convertView = inflater.inflate(R.layout.add_comment, null);
        }


        return convertView;
    }
}
