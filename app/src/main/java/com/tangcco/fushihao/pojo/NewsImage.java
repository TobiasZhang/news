package com.tangcco.fushihao.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsImage implements Serializable{

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private int imageId;
    private int imagePath;

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public NewsImage(){}

}
