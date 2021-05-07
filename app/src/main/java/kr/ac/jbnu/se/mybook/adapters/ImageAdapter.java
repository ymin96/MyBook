package kr.ac.jbnu.se.mybook.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.jbnu.se.mybook.R;
import kr.ac.jbnu.se.mybook.models.Image;

public class ImageAdapter extends ArrayAdapter<Image> {
    private static class ViewHolder {
        public ImageView ivImageCover;
        }

    public ImageAdapter(Context context, ArrayList<Image> aImages) {
        super(context, 0, aImages);
    }

    // Translates a particular `Image` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Image image = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ImageAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ImageAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_image, parent, false);
            viewHolder.ivImageCover = (ImageView)convertView.findViewById(R.id.ivBookCover);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImageAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        Picasso.with(getContext()).load(Uri.parse(image.getImage_URL())).error(R.drawable.ic_nocover).into(viewHolder.ivImageCover);
        // Return the completed view to render on screen
        return convertView;
    }
}
