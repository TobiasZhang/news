package com.example.administrator.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tangcco.fushihao.dao.NewsUserDao;
import com.tangcco.fushihao.pojo.News;
import com.tangcco.fushihao.pojo.NewsUser;

public class UserLogin extends AppCompatActivity{
    private EditText name , pwd;
    private LinearLayout lin;
    private long exitTime = 0;
    private String context = "MainActivity";
    private Class mClass;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        name = (EditText) findViewById(R.id.login_user_name);
        pwd = (EditText) findViewById(R.id.login_user_pwd);
        lin = (LinearLayout) findViewById(R.id.login_lin);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.news_content_listview);
        lin.startAnimation(animation);
        animation.setFillAfter(true);

        Intent i = getIntent();
        context = i.getStringExtra("context");
        final News news = (News) i.getSerializableExtra("news");

        if("MainActivity".equals(context)){
            mClass = MainActivity.class;
        }else if("NewsContent".equals(context)){
            mClass = NewsContent.class ;
        }else if("SaveNews".equals(context)){
            mClass = SaveNews.class ;
        }

        lin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserLogin.this);
                builder.setTitle("请选择您要进行的操作");
                builder .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //登录
                        NewsUserDao nuDao = new NewsUserDao(UserLogin.this);
                        if(name.getText().toString().equals("") || pwd.getText().toString().equals("")){
                            Toast.makeText(UserLogin.this,"用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final NewsUser nuser = nuDao.getNewsUserByNameAndPwd(name.getText().toString(), pwd.getText().toString());
                        if (nuser.getUserName() == null) {
                            Toast.makeText(UserLogin.this, "用户名或密码错误!!!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(UserLogin.this,"登录成功！！！",Toast.LENGTH_SHORT).show();
                            Animation an = AnimationUtils.loadAnimation(UserLogin.this,R.anim.main_activity_listview);
                            lin.startAnimation(an);
                            an.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    if(mClass == null){
                                        mClass = MainActivity.class;
                                    }
                                    Intent in = new Intent(UserLogin.this, mClass);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("user", nuser);

                                    if(news != null) {
                                        bundle.putSerializable("news", news);
                                    }
                                    in.putExtras(bundle);                                    startActivity(in);
                                    finish();
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }
                }).setNegativeButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Animation an = AnimationUtils.loadAnimation(UserLogin.this,R.anim.main_activity_listview);
                        lin.startAnimation(an);
                        an.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Intent intent = new Intent(UserLogin.this,UserRegister.class);
                                intent.putExtra("context",context);
                                Bundle bundle = new Bundle();
                                if(news != null) {
                                    bundle.putSerializable("news", news);
                                    intent.putExtras(bundle);
                                }
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                }).create().show();
                return false;
            }
        });
    }
}