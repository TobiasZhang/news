package com.tangcco.fushihao.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tangcco.fushihao.pojo.NewsImage;
import com.tangcco.fushihao.utils.DataBaseHelper;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsImageDao {
    private Context context;
    private DataBaseHelper db = null;
    private SQLiteDatabase sd = null;
    public NewsImageDao(Context context){
        this.context = context;
    }

    public NewsImageDao() {

    }

    public NewsImage findById(int id){

        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        sd.beginTransaction();
        NewsImage newsImage = new NewsImage();
        String sql = "select * from news_image where image_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        if(cursor.moveToNext()){
            newsImage.setImageId(cursor.getInt(0));
            newsImage.setImagePath(cursor.getInt(1));
        }
        sd.endTransaction();
        cursor.close();
        db.close();
        sd.close();
        return newsImage;
    }

    public NewsImage findById(int id,SQLiteDatabase sd){

        db = new DataBaseHelper(context);

        NewsImage newsImage = new NewsImage();
        String sql = "select * from news_image where image_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        if(cursor.moveToNext()){
            newsImage.setImageId(cursor.getInt(0));
            newsImage.setImagePath(cursor.getInt(1));
        }
        return newsImage;
    }

    public void saveImage(NewsImage img){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();

        try {
            sd.beginTransaction();
            String sql = "insert into news_image values(null,?)";
            sd.execSQL(sql,new Object[]{img.getImagePath()});
            sd.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sd.endTransaction();
            sd.close();
            db.close();
        }
    }
}
