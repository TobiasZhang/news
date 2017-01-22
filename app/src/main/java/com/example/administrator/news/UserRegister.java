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

public class UserRegister extends AppCompatActivity {
    private EditText name , pwd , rpwd , real , nick , address;
    private LinearLayout lin;
    private String context = "MainActivity";
    private long exitTime = 0;
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
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.register_user_name);
        pwd = (EditText) findViewById(R.id.register_user_pwd);
        real = (EditText) findViewById(R.id.register_user_real);
        nick = (EditText) findViewById(R.id.register_user_nick);
        address = (EditText) findViewById(R.id.register_user_address);
        lin = (LinearLayout) findViewById(R.id.register_lin);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.news_content_listview);
        lin.startAnimation(animation);
        animation.setFillAfter(true);
        rpwd = (EditText) findViewById(R.id.register_user_rpwd);

        Intent i = getIntent();
        context = i.getStringExtra("context");
        final News news = (News) i.getSerializableExtra("news");

        lin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserRegister.this);
                builder.setTitle("请选择您要进行的操作")
                        .setNegativeButton("去登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Animation an = AnimationUtils.loadAnimation(UserRegister.this,R.anim.main_activity_listview);
                                lin.startAnimation(an);
                                an.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        Intent gotologin = new Intent(UserRegister.this,UserLogin.class);
                                        gotologin.putExtra("context",context);
                                        Bundle bundle = new Bundle();
                                        if(news != null) {
                                            bundle.putSerializable("news", news);
                                            gotologin.putExtras(bundle);
                                        }
                                        startActivity(gotologin);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                            }
                        }).setPositiveButton("注册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NewsUserDao newsUserDao = new NewsUserDao(UserRegister.this);
                                NewsUser user = new NewsUser();
                                boolean flag = newsUserDao.getNewsUserByName(name.getText().toString());
                                if(real.getText().toString().equals("") || nick.getText().toString().equals("") ||name.getText().toString().equals("") || pwd.getText().toString().equals("") || address.getText().toString().equals("") || rpwd.getText().toString().equals("")){
                                    Toast.makeText(UserRegister.this,"信息不能为空",Toast.LENGTH_LONG).show();
                                    return ;
                                }else if(!pwd.getText().toString().equals(rpwd.getText().toString())){
                                    Toast.makeText(UserRegister.this,"两次输入密码不一致",Toast.LENGTH_LONG).show();
                                    return;
                                }else if(flag == true){
                                    Toast.makeText(UserRegister.this,"账号已存在",Toast.LENGTH_LONG).show();
                                    return;
                                }else{
                                    user.setUserRealName(real.getText().toString());
                                    user.setUserNickName(nick.getText().toString());
                                    user.setUserName(name.getText().toString());
                                    user.setPassWrod(pwd.getText().toString());
                                    user.setUserState(1);
                                    user.setUserAddress(address.getText().toString());
                                    newsUserDao.saveNewsUser(user);
                                    Animation an = AnimationUtils.loadAnimation(UserRegister.this,R.anim.main_activity_listview);
                                    lin.startAnimation(an);
                                    an.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            Intent gotoRegister = new Intent(UserRegister.this,UserLogin.class);
                                            gotoRegister.putExtra("context",context);
                                            Bundle bundle = new Bundle();
                                            if(news != null) {
                                                bundle.putSerializable("news", news);
                                                gotoRegister.putExtras(bundle);
                                            }
                                            startActivity(gotoRegister);
                                            finish();
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                }
                            }
                        }).create().show();
                return false;
            }
        });
    }
}
