package com.tangcco.fushihao.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.tangcco.fushihao.pojo.News;
import com.tangcco.fushihao.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsDao {
    private Context context;
    private DataBaseHelper db = null;
    private SQLiteDatabase sd = null;
    public NewsDao(Context context){
        this.context = context;
    }
    private NewsTypeDao ntdao = null;
    private NewsUserDao nudao = null;
    private NewsImageDao nidao = null;

    public List<News> getAllNews(){
        ntdao = new NewsTypeDao(context);
        nudao = new NewsUserDao(context);
        nidao = new NewsImageDao(context);
        List<News> list = new ArrayList<News>();
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        sd.beginTransaction();
        String sql = "select * from news";
        Cursor cursor = sd.rawQuery(sql,null);
        while(cursor.moveToNext()){
            News news = new News();
            news.setNewsId(cursor.getInt(cursor.getColumnIndex("news_id")));
            news.setNewsTitle(cursor.getString(cursor.getColumnIndex("news_title")));
            news.setNewsContent(cursor.getString(cursor.getColumnIndex("news_content")));
            news.setNewsType(ntdao.findById(cursor.getInt(cursor.getColumnIndex("news_typeId")),sd));
            news.setNewsUser(nudao.findById(cursor.getInt(cursor.getColumnIndex("news_userId")),sd));
            news.setNewsCount(cursor.getInt(cursor.getColumnIndex("news_count")));
            news.setNewsImage(nidao.findById(cursor.getInt(cursor.getColumnIndex("news_img")),sd));
            news.setNews_state(cursor.getInt(cursor.getColumnIndex("news_state")));
            news.setNewsAbstract(cursor.getString(cursor.getColumnIndex("news_abstract")));
            news.setCreateDate(cursor.getString(cursor.getColumnIndex("create_date")));
            list.add(news);
        }
        sd.endTransaction();
        sd.close();
        db.close();
        cursor.close();
        return list;
    }

    public News findById(int id){
        ntdao = new NewsTypeDao(context);
        nudao = new NewsUserDao(context);
        nidao = new NewsImageDao(context);
        News news = null;
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        sd.beginTransaction();
        String sql = "select * from news where news_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        while(cursor.moveToNext()){
            news = new News();
            news.setNewsId(cursor.getInt(cursor.getColumnIndex("news_id")));
            news.setNewsTitle(cursor.getString(cursor.getColumnIndex("news_title")));
            news.setNewsContent(cursor.getString(cursor.getColumnIndex("news_content")));
            news.setNewsType(ntdao.findById(cursor.getInt(cursor.getColumnIndex("news_typeId")),sd));
            news.setNewsUser(nudao.findById(cursor.getInt(cursor.getColumnIndex("news_userId")),sd));
            news.setNewsCount(cursor.getInt(cursor.getColumnIndex("news_count")));
            news.setNewsImage(nidao.findById(cursor.getInt(cursor.getColumnIndex("news_img")),sd));
            news.setNews_state(cursor.getInt(cursor.getColumnIndex("news_state")));
            news.setNewsAbstract(cursor.getString(cursor.getColumnIndex("news_abstract")));
            news.setCreateDate(cursor.getString(cursor.getColumnIndex("create_date")));
        }
        sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();
        return news;
    }

    public void insertNews(News news){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();
        try {
            sd.beginTransaction();
            String sql = "insert into news values(null,?,?,?,?,?,?,?,?,?)";
            sd.execSQL(sql,new Object[]{news.getNewsTitle(),news.getNewsContent(),news.getNewsType().getTypeId(),news.getNewsUser().getUserId(),news.getNewsCount(),news.getNewsImage().getImageId(),news.getNews_state(),news.getNewsAbstract(),news.getCreateDate()});
            sd.setTransactionSuccessful();
            Toast.makeText(context,"添加新闻成功！！！",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sd.endTransaction();
            sd.close();
            db.close();
        }
    }

    public void updateNews(News news){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();
        try {
            sd.beginTransaction();
            String sql = "update news set news_title = ? , news_content = ? , news_typeId = ? , news_userId = ? , news_count = ? , news_img = ? , news_state = ? , news_abstract = ? , create_date = ? where news_id = ? ";
            sd.execSQL(sql,new Object[]{news.getNewsTitle(),news.getNewsContent(),news.getNewsType(),news.getNewsUser(),news.getNewsCount(),news.getNewsImage(),news.getNews_state(),news.getNewsAbstract(),news.getCreateDate(),news.getNewsId()});
            sd.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            sd.endTransaction();
            sd.close();
            db.close();
        }
    }
}