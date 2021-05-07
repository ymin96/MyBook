package kr.ac.jbnu.se.mybook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.ac.jbnu.se.mybook.R;
import kr.ac.jbnu.se.mybook.adapters.ImageAdapter;
import kr.ac.jbnu.se.mybook.models.Image;
import kr.ac.jbnu.se.mybook.net.ImageClient;

public class ImageListActivity extends ActionBarActivity {
    public static final String IMAGE_DETAIL_KEY = "image";
    private ListView lvImages;
    private ImageAdapter imageAdapter;
    private ImageClient client;
    private ProgressBar progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        lvImages = (ListView) findViewById(R.id.lvImages);
        ArrayList<Image> aImages = new ArrayList<Image>();
        imageAdapter = new ImageAdapter(this, aImages);
        lvImages.setAdapter(imageAdapter);
        progress = (ProgressBar) findViewById(R.id.progress);
        setupImageSelectedListener();
    }

    public void setupImageSelectedListener() {
        lvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ImageListActivity.this, ImageDetailActivity.class);
                intent.putExtra(IMAGE_DETAIL_KEY, imageAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void fetchImages(String query) {
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new ImageClient();
        client.getImages(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        Log.d("tag", "result:" + response.toString());
                        docs = response.getJSONArray("documents");
                        final ArrayList<Image> images = Image.fromJson(docs);
                        imageAdapter.clear();
                        for (Image image : images) {
                            imageAdapter.add(image);
                        }
                        imageAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.d("tag", "result:" + response.toString(), e);
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response)
            {
                Log.d("tag", "result:" + response.toString(), throwable);

                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchImages(query);
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                ImageListActivity.this.setTitle(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
