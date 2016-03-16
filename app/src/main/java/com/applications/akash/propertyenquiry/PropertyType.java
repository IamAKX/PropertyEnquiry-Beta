package com.applications.akash.propertyenquiry;

/**
 * Created by Akash on 2/23/2016.
 */
public class PropertyType {

    private int image_id;
    private String image_name;

    public PropertyType(int image_id, String image_name) {
        this.image_id = image_id;
        this.image_name = image_name;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getImage_name() {
        return image_name;
    }
}
