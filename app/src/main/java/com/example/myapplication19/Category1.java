package com.example.myapplication19;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

import retrofit2.http.Field;

public class Category1 {


    private String id;

    private String name;

    private String image;
    public Category1() {

    }
    public Category1(String name, String image) {
        super();
        this.name = name;
        this.image = image;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setCategoryImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Category [name=" + name + ",id="+id+ ", image=" + image + "]";
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category1 other = (Category1) obj;
        return Objects.equals(id, other.id);
    }

}
