package com.tangcco.fushihao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/18.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private final String CREATE_TABLE_NEWS = "create table news (news_id integer primary key autoincrement , news_title , news_content , news_typeId , news_userId , news_count , news_img , news_state , news_abstract , create_date )";
    private final String CREATE_TABLE_USER = "create table news_user(user_id integer primary key autoincrement , user_realName , user_nickName , user_name , pass_word , user_address , user_state )";
    private final String CREATE_TABLE_TYPE = "create table news_type(type_id integer primary key autoincrement , type_name )";
    private final String CREATE_TABLE_IMAGE = "create table news_image(image_id integer primary key autoincrement , image_path)";

    public DataBaseHelper(Context context) {
        super(context,"NewsFirst.db3" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IMAGE);
        db.execSQL(CREATE_TABLE_NEWS);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_USER);
        String sql1 = "insert into news values(null,'新闻','按时到路口附近就爱上当你把sdjfalfasdnfslsdafnmlasdn',1,1,0,1,1,'是V暗色调','2016-09-11 15:31:20')";
        String sql2 = "insert into news values(null,'导师礼','卡上的解放了看见爱上对方了四大家立方附近我去空间而不仅是打发了山大路附近看看女爱丽丝建设大路父控件',1,1,0,1,1,'爱上的加我','2016-09-12 11:21:36')";
        String sql3 = "insert into news values(null,'理财','刷卡了地方经济加速度我是ID附近的萨芬你爱上了对方就',1,1,0,1,1,'按时打卡了两口就丰富大厦','2016-09-11 08:21:22')";
        String sql4 = "insert into news values(null,'财经','是打发撒旦法拜访vfhghfsh的沙发沙发恩里克趣味覅偶见',1,1,0,1,1,'asdfjkbnvlkasj','2016-09-11 15:31:20')";
        String sql5 = "insert into news values(null,'不知道','我时间的浪费就女老师的打分 就发生的了的算法lksjaf撒旦法房间阿达撒旦法',1,1,0,1,1,'asdfjkbnvlkasj','2016-09-11 15:31:20')";
        String sql6 = "insert into news values(null,'知道吗','我是发四大家立方接口多少啦富家女女人法律是打飞机就水电费水电费sdfklj撒旦法',1,1,0,1,1,'asdfjkbnvlkasj','2016-09-11 15:31:20')";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
