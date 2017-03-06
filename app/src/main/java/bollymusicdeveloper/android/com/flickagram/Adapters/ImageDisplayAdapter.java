package bollymusicdeveloper.android.com.flickagram.Adapters;

/**
 * Created by amitagarwal3 on 3/6/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.ArrayList;

import bollymusicdeveloper.android.com.flickagram.Helper.ImageLoaderHelper;
import bollymusicdeveloper.android.com.flickagram.R;


public class ImageDisplayAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> urls;
    private static LayoutInflater inflater=null;
    public ImageLoaderHelper imageLoaderHelper;

    public ImageDisplayAdapter(Activity a, ArrayList<String> inputUrls) {
        activity = a;
        urls=inputUrls;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoaderHelper=new ImageLoaderHelper(activity.getApplicationContext());
    }

    public int getCount() {
        return null != urls && !urls.isEmpty() ? urls.size():0;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listview_image_item, null);

        ImageView image=(ImageView)vi.findViewById(R.id.imageview);
        imageLoaderHelper.showImage(urls.get(position), image);
        return vi;
    }
}
