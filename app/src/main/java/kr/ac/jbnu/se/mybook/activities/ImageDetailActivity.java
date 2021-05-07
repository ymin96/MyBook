package kr.ac.jbnu.se.mybook.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.ac.jbnu.se.mybook.R;
import kr.ac.jbnu.se.mybook.models.Image;
import kr.ac.jbnu.se.mybook.net.ImageClient;

public class ImageDetailActivity extends ActionBarActivity {
    private ImageView ivImageCover;
    private TextView tvDisplaySiteName;
    private TextView tvImageUrl;
    private ImageClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ivImageCover = (ImageView) findViewById(R.id.ivImageCover);
        tvDisplaySiteName = (TextView) findViewById(R.id.tvDisplaySiteName);
        tvImageUrl = (TextView) findViewById(R.id.tvImageUrl);
        Image image = (Image) getIntent().getSerializableExtra(ImageListActivity.IMAGE_DETAIL_KEY);
        loadImage(image);
    }

    private void loadImage(Image image) {
        this.setTitle(image.getTitle());
        Picasso.with(this).load(Uri.parse(image.getImage_URL())).error(R.drawable.ic_nocover).into(ivImageCover);
        tvDisplaySiteName.setText(image.getDisplay_sitename());
        tvImageUrl.setText(image.getImage_URL());
        client = new ImageClient();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share2) {
            setShareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        ImageView ivImageCover = (ImageView) findViewById(R.id.ivImageCover);
        final TextView tvImageUrl = (TextView)findViewById(R.id.tvImageUrl);
        final TextView tvDisplaySiteName = (TextView)findViewById(R.id.tvDisplaySiteName);
        Uri bmpUri = getLocalBitmapUri(ivImageCover);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String)tvImageUrl.getText());
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String)tvDisplaySiteName.getText());
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
