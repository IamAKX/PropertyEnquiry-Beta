package com.applications.akash.propertyenquiry;

/**
 * Created by Akash on 2/29/2016.
 */
public class PropertyCard
{
    String possession,name,builder,address,phone,cost;
    String img_url;
    public PropertyCard(String img_url, String possession, String name, String builder, String address, String phone, String cost) {
        this.img_url = img_url;
        this.possession = possession;
        this.name = name;
        this.builder = builder;
        this.address = address;
        this.phone = phone;
        this.cost = cost;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getPossession() {
        return possession;
    }

    public String getName() {
        return name;
    }

    public String getBuilder() {
        return builder;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getCost() {
        return cost;
    }
}
