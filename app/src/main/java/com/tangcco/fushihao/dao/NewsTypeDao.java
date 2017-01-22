package com.tangcco.fushihao.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tangcco.fushihao.pojo.NewsType;
import com.tangcco.fushihao.utils.DataBaseHelper;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsTypeDao {
    private Context context;
    private DataBaseHelper db = null;
    private SQLiteDatabase sd = null;

    public NewsTypeDao(Context context){
        this.context = context;
    }

    public NewsTypeDao(){}

    public NewsType findById(int id){
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        NewsType newsType = null;
        sd.beginTransaction();
        String sql = "select * from news_type where type_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        if(cursor.moveToNext()){
            newsType = new NewsType();
            newsType.setTypeId(cursor.getInt(0));
            newsType.setTypeName(cursor.getString(1));
        }

        sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();
        return newsType;
    }



    public NewsType findById(int id , SQLiteDatabase sd){
        db = new DataBaseHelper(context);
//        sd = db.getReadableDatabase();
        NewsType newsType = null;
//        sd.beginTransaction();
        String sql = "select * from news_type where type_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        if(cursor.moveToNext()){
            newsType = new NewsType();
            newsType.setTypeId(cursor.getInt(0));
            newsType.setTypeName(cursor.getString(1));
        }

        /*sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();*/
        return newsType;
    }


    public void saveType(NewsType type){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();

        try {
            sd.beginTransaction();
            String sql = "insert into news_type values(null,?)";
            sd.execSQL(sql,new Object[]{type.getTypeName()});
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
