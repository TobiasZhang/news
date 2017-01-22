package com.tangcco.fushihao.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.tangcco.fushihao.pojo.NewsUser;
import com.tangcco.fushihao.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class NewsUserDao {
    private Context context;
    public NewsUserDao(Context context){
        super();
        this.context = context;
    }
    DataBaseHelper db = null;
    SQLiteDatabase sd = null;
    public NewsUserDao(){}

    public List<NewsUser> getAllNewsUser(){
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        NewsUser newsUser = null;
        List<NewsUser> list = new ArrayList<NewsUser>();
        sd.beginTransaction();
        String sql = "select * from news_user";
        Cursor cursor = sd.rawQuery(sql,null);
        while(cursor.moveToNext()){
            newsUser = new NewsUser();
            newsUser.setUserId(cursor.getInt(0));
            newsUser.setUserRealName(cursor.getString(1));
            newsUser.setUserNickName(cursor.getString(2));
            newsUser.setUserName(cursor.getString(3));
            newsUser.setPassWrod(cursor.getString(4));
            newsUser.setUserAddress(cursor.getString(5));
            newsUser.setUserState(cursor.getInt(6));
            list.add(newsUser);
        }
        sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();
        return list;
    }

    public NewsUser findById(int id){
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        NewsUser newsUser = null;
        sd.beginTransaction();
        String sql = "select * from news_user where user_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        if(cursor.moveToNext()){
            newsUser = new NewsUser();
            newsUser.setUserId(cursor.getInt(0));
            newsUser.setUserRealName(cursor.getString(1));
            newsUser.setUserNickName(cursor.getString(2));
            newsUser.setUserName(cursor.getString(3));
            newsUser.setPassWrod(cursor.getString(4));
            newsUser.setUserAddress(cursor.getString(5));
            newsUser.setUserState(cursor.getInt(6));
        }
        sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();
        return newsUser;
    }

    public NewsUser findById(int id , SQLiteDatabase sd){
        db = new DataBaseHelper(context);
        NewsUser newsUser = null;
        String sql = "select * from news_user where user_id = "+id;
        Cursor cursor = sd.rawQuery(sql,null);
        if(cursor.moveToNext()){
            newsUser = new NewsUser();
            newsUser.setUserId(cursor.getInt(0));
            newsUser.setUserRealName(cursor.getString(1));
            newsUser.setUserNickName(cursor.getString(2));
            newsUser.setUserName(cursor.getString(3));
            newsUser.setPassWrod(cursor.getString(4));
            newsUser.setUserAddress(cursor.getString(5));
            newsUser.setUserState(cursor.getInt(6));
        }
        return newsUser;
    }

    public void saveNewsUser(NewsUser user){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();
        try {
            sd.beginTransaction();
            String sql = "insert into news_user values(null,?,?,?,?,?,?)";
            sd.execSQL(sql,new Object[]{user.getUserRealName(),user.getUserNickName(),user.getUserName(),user.getPassWrod(),user.getUserAddress(),user.getUserState()});
            sd.setTransactionSuccessful();
            Toast.makeText(context,"添加成功！",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(context,"添加失败！",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally {
            sd.endTransaction();
            sd.close();
            db.close();

        }
    }

    public void updateNewsUser(NewsUser user){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();
        try {
            sd = db.getWritableDatabase();
            sd.beginTransaction();
            String sql = "update news_user set user_realName = ? , user_nickName = ? , user_name = ? , pass_word = ? , user_address = ? , user_state = ? where user_id = ?";
            sd.execSQL(sql,new Object[]{user.getUserRealName(),user.getUserNickName(),user.getUserName(),user.getPassWrod(),user.getUserAddress(),user.getUserState(),user.getUserId()});
            sd.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sd.endTransaction();
        sd.close();
        db.close();
    }

    public void deleteNewsUser(int id){
        db = new DataBaseHelper(context);
        sd = db.getWritableDatabase();
        sd.beginTransaction();
        String sql = "delete from news_user where user_id = "+id;
        sd.execSQL(sql);


        sd.endTransaction();
        sd.close();
        db.close();

    }

    public NewsUser getNewsUserByNameAndPwd(String name , String pwd){
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        NewsUser newsUser = new NewsUser();
        sd.beginTransaction();
        String sql = "select * from news_user where user_name = ? and pass_word = ?";
        Cursor cursor = sd.rawQuery(sql,new String[]{name,pwd});
        if(cursor.moveToNext()){
            newsUser = new NewsUser();
            newsUser.setUserId(cursor.getInt(0));
            newsUser.setUserRealName(cursor.getString(1));
            newsUser.setUserNickName(cursor.getString(2));
            newsUser.setUserName(cursor.getString(3));
            newsUser.setPassWrod(cursor.getString(4));
            newsUser.setUserAddress(cursor.getString(5));
            newsUser.setUserState(cursor.getInt(6));
        }
        sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();
        return newsUser;
    }

    public boolean getNewsUserByName(String name){
        boolean flag = false;
        db = new DataBaseHelper(context);
        sd = db.getReadableDatabase();
        NewsUser newsUser = new NewsUser();
        sd.beginTransaction();
        String sql = "select * from news_user where user_name = ? ";
        Cursor cursor = sd.rawQuery(sql,new String[]{name});
        if(cursor.moveToNext()){
            flag = true;
        }else{
            flag = false;
        }
        sd.endTransaction();
        cursor.close();
        sd.close();
        db.close();
        return flag;
    }




}
