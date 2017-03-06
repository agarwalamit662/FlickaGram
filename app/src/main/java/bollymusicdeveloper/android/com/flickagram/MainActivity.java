package bollymusicdeveloper.android.com.flickagram;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import bollymusicdeveloper.android.com.flickagram.Adapters.ImageDisplayAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView listImages;
    private ImageDisplayAdapter adapter;
    // Flickr API URL
    private String apiUrl = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=dbd209137cb9217c4bb2972595caa549&format=json&nojsoncallback=1&text=bmw&perPage=50&extras=url_m";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listImages=(ListView)findViewById(R.id.listView);
        adapter=new ImageDisplayAdapter(this, new ArrayList<String>());
        listImages.setAdapter(adapter);

        // Calling Async Task
        (new AsyncListViewLoaderTiled()).execute(apiUrl);

    }

    @Override
    public void onDestroy()
    {
        listImages.setAdapter(null);
        super.onDestroy();
    }

    private class AsyncListViewLoaderTiled extends AsyncTask<String, Void, ArrayList<String>> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        InputStreamReader inputStream = null;
        String result = "";


        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);


            if(result == null){
                Toast.makeText(MainActivity.this,"Something went wrong! Check your network connection or Try Later",Toast.LENGTH_LONG).show();
                adapter=new ImageDisplayAdapter(MainActivity.this, result);
                listImages.setAdapter(adapter);

            }
            else if (result != null && result.size() > 0 && MainActivity.this != null) {

                adapter=new ImageDisplayAdapter(MainActivity.this, result);
                listImages.setAdapter(adapter);

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {

            ArrayList<String> mReturn = new ArrayList<String>();

            URL u;
            try {
                u = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int code = urlConnection.getResponseCode();
                inputStream = new InputStreamReader(urlConnection.getInputStream());

                try {
                    BufferedReader bReader = new BufferedReader(inputStream);
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line + "\n");
                    }

                    inputStream.close();
                    result = sBuilder.toString();


                    String urlFromNetwork;
                    try {

                        JSONObject jObj = new JSONObject(result);
                        JSONObject jObjPhotos = jObj.getJSONObject("photos");
                        JSONArray jPhotoArray = jObjPhotos.getJSONArray("photo");
                        for (int j = 0; jPhotoArray != null && jPhotoArray.length() > 0 && j < jPhotoArray.length(); j++) {
                            JSONObject jObjectSong = jPhotoArray.getJSONObject(j);
                            if (!jObjectSong.isNull("url_m")){
                                urlFromNetwork = jObjectSong.getString("url_m");
                                mReturn.add(urlFromNetwork);
                            }
                        }

                    } catch (JSONException e) {
                        Log.e("JSONException", "Error: " + e.toString());
                        return null;
                    }


                } catch (Exception e) {
                    Log.e("StringBuilding ", "Error converting result " + e.toString());
                    return null;
                }

            } catch (MalformedURLException e) {
                Log.e("Malformed URL ", "Error converting result " + e.toString());
                return null;
            } catch (ProtocolException e) {
                Log.e("ProtocolExc", "Error converting result " + e.toString());
                return null;
            } catch (IOException e) {
                Log.e("IO EXCEPTION ", "Error converting result " + e.toString());
                return null;

            }


            return mReturn;
        }
    }
    

}
