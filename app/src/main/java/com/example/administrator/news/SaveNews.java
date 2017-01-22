package com.example.administrator.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tangcco.fushihao.dao.NewsDao;
import com.tangcco.fushihao.dao.NewsImageDao;
import com.tangcco.fushihao.dao.NewsTypeDao;
import com.tangcco.fushihao.pojo.News;
import com.tangcco.fushihao.pojo.NewsImage;
import com.tangcco.fushihao.pojo.NewsType;
import com.tangcco.fushihao.pojo.NewsUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveNews extends AppCompatActivity {
    private EditText newsTitle,newsAbstract,newsContent;
    private NewsUser newsUser = null;
    private TextView hoder;
    private RelativeLayout rel,headRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_news);
        newsTitle = (EditText) findViewById(R.id.news_title);
        newsAbstract = (EditText) findViewById(R.id.news_abstract);
        newsContent  = (EditText) findViewById(R.id.news_content);
        hoder = (TextView) findViewById(R.id.save_news_hoder);
        rel = (RelativeLayout) findViewById(R.id.rel);
        headRes = (RelativeLayout) findViewById(R.id.relative);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.news_content_listview);
        rel.startAnimation(animation);
        animation.setFillAfter(true);

        //接受user对象
        Intent in = this.getIntent();
        newsUser = (NewsUser) in.getSerializableExtra("user");
        if(newsUser!= null) {
            hoder.setText("欢迎");
            showLights(hoder,newsUser.getUserNickName(),"回来");

            headRes.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SaveNews.this);
                    builder.setTitle("请选择操作")
                            .setNegativeButton("注销登录", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(SaveNews.this,UserLogin.class);
                                    it.putExtra("context","SaveNews");
                                    startActivity(it);
                                    finish();
                                }
                            }).create().show();
                    return false;
                }
            });
        }else{
            headRes.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(SaveNews.this,"您还未登录，不能进行此操作",Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            hoder.setText("您还未");
            showLight(hoder,"登录");
        }

        hoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation ans = AnimationUtils.loadAnimation(SaveNews.this,R.anim.main_activity_listview);
                rel.startAnimation(ans);
                ans.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent gotologin = new Intent(SaveNews.this,UserLogin.class);
                        gotologin.putExtra("context","SaveNews");
                        startActivity(gotologin);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        rel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SaveNews.this);
                builder.setTitle("请选择您要进行的操作").setNegativeButton("添加新闻", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if("".equals(newsContent.getText().toString()) || "".equals(newsAbstract.getText().toString()) || "".equals(newsTitle.getText().toString())){
                            Toast.makeText(SaveNews.this,"内容不能为空",Toast.LENGTH_LONG).show();
                            return;
                        }
                        Animation an = AnimationUtils.loadAnimation(SaveNews.this,R.anim.main_activity_listview);
                        rel.startAnimation(an);
                        NewsDao ndao = new NewsDao(SaveNews.this);
                        NewsImageDao nidao = new NewsImageDao(SaveNews.this);
                        NewsTypeDao ntdao = new NewsTypeDao (SaveNews.this);
                        NewsImage img = new NewsImage();
                        NewsType type = new NewsType();
                        img.setImagePath(R.mipmap.girl_2);
                        type.setTypeName("新闻");
                        nidao.saveImage(img);
                        ntdao.saveType(type);

                        Intent in = SaveNews.this.getIntent();
                        newsUser = (NewsUser) in.getSerializableExtra("user");
                        News news = new News();
                        news.setNewsAbstract(newsAbstract.getText().toString());
                        news.setNews_state(1);
                        news.setNewsImage(img);
                        news.setNewsCount(0);
                        news.setNewsContent(newsContent.getText().toString());
                        news.setNewsTitle(newsTitle.getText().toString());
                        news.setNewsType(type);
                        news.setNewsUser(newsUser);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        String dates = new String(sdf.format(date));
                        news.setCreateDate(dates);
                        ndao.insertNews(news);
                        Toast.makeText(SaveNews.this, dates+":date", Toast.LENGTH_SHORT).show();
                        an.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Intent intent = new Intent(SaveNews.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user",newsUser);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
                return false;
            }
        });
    }

    private void showLight(TextView txt , String msg){
        SpannableStringBuilder ssb = new SpannableStringBuilder(txt.getText()+msg);
        ssb.setSpan(new ForegroundColorSpan(Color.RED),txt.getText().length(),txt.getText().length()+1+(msg).length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt.setText(ssb);
    }

    private void showLights(TextView txt, String msg , String end){
        SpannableStringBuilder ssb = new SpannableStringBuilder(txt.getText()+msg+end);
        ssb.setSpan(new ForegroundColorSpan(Color.RED),txt.getText().length(),txt.getText().length()+msg.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txt.setText(ssb);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Animation ans = AnimationUtils.loadAnimation(SaveNews.this,R.anim.main_activity_listview);
            rel.startAnimation(ans);
            ans.setFillAfter(true);
            ans.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(SaveNews.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",newsUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
