package kr.ac.jbnu.se.mybook.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Image implements Serializable {
    private String openLibraryId;
    private String display_sitename;
    private String image_URL;
    private String title;

    public String getDisplay_sitename() {
        return display_sitename;
    }

    public String getImage_URL() {
        return image_URL;
    }

    public String getTitle() {
        return title;
    }

    // Returns a Book given the expected JSON
    public static Image fromJson(JSONObject jsonObject) {
        Image image = new Image();
        try {
            image.display_sitename = jsonObject.getString("display_sitename");
            image.image_URL = jsonObject.getString("image_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return image;
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<Image> fromJson(JSONArray jsonArray) {
        ArrayList<Image> images = new ArrayList<Image>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject imageJson = null;
            try {
                imageJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Image image = Image.fromJson(imageJson);
            if (image != null) {
                images.add(image);
            }
        }
        return images;
    }
}
