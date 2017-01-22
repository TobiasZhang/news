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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tangcco.fushihao.pojo.News;
import com.tangcco.fushihao.pojo.NewsUser;

public class NewsContent extends AppCompatActivity {
    private TextView username , title , content, hoder , date;
    private ImageView img , image;
    NewsUser newsUser = null;
    private LinearLayout lin ;
    private EditText edit;
    private RelativeLayout headRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        lin = (LinearLayout) findViewById(R.id.lin);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.news_content_listview);
        lin.startAnimation(animation);
        animation.setFillAfter(true);
        username = (TextView) findViewById(R.id.news_content_username);
        title = (TextView) findViewById(R.id.news_content_title);
        content = (TextView) findViewById(R.id.news_content);
        img = (ImageView) findViewById(R.id.news_content_img);
        image = (ImageView) findViewById(R.id.news_content_image);
        hoder = (TextView) findViewById(R.id.news_content_hoder);
        date = (TextView) findViewById(R.id.news_content_create_date);
        headRes = (RelativeLayout) findViewById(R.id.relative);
        Intent intent = getIntent();
        final News news = (News) intent.getSerializableExtra("news");
        if(news != null) {
            username.setText(news.getNewsUser().getUserNickName());
        }
        title.setText(news.getNewsTitle());
        content.setText(news.getNewsContent());
        date.setText(news.getCreateDate());

        //接受user对象
        Intent in = this.getIntent();
        newsUser = (NewsUser) in.getSerializableExtra("user");
        if(newsUser!= null) {
            hoder.setText("欢迎");
            showLights(hoder,newsUser.getUserNickName(),"回来");

            headRes.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewsContent.this);
                    builder.setTitle("请选择操作")
                            .setNegativeButton("注销登录", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(NewsContent.this,UserLogin.class);
                                    it.putExtra("context","NewsContent");
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("news",news);
                                    it.putExtras(bundle);
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
                    Toast.makeText(NewsContent.this,"您还未登录，不能进行此操作",Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            hoder.setText("您还未");
            showLight(hoder,"登录");
        }

        hoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation ans = AnimationUtils.loadAnimation(NewsContent.this,R.anim.main_activity_listview);
                lin.startAnimation(ans);
                ans.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent gotologin = new Intent(NewsContent.this,UserLogin.class);
                        gotologin.putExtra("context","NewsContent");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("news",news);
                        gotologin.putExtras(bundle);
                        startActivity(gotologin);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
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
            Animation ans = AnimationUtils.loadAnimation(NewsContent.this,R.anim.main_activity_listview);
            lin.startAnimation(ans);
            ans.setFillAfter(true);
            ans.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(NewsContent.this,MainActivity.class);
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