package com.example.administrator.news;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tangcco.fushihao.dao.NewsDao;
import com.tangcco.fushihao.pojo.News;
import com.tangcco.fushihao.pojo.NewsUser;

import java.util.ArrayList;
import java.util.List;

import widget.XListView;

public class MainActivity extends Activity implements View.OnClickListener{
    private XListView listView;
    private TextView hoder;
    private Handler handler;
    NewsUser newsUser = null;
    private Button gotoNews;
    private NewsDao newsDao = new NewsDao(this);
    private List<News> list = new ArrayList<News>();
    private RelativeLayout res = null;
    private RelativeLayout headRes;
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
        setContentView(R.layout.activity_main);
        listView = (XListView) findViewById(R.id.list);
        handler = new Handler();
        hoder = (TextView) findViewById(R.id.main_hoder);
        gotoNews = (Button) findViewById(R.id.main_goto_save_news);
        headRes = (RelativeLayout) findViewById(R.id.relative);
        res = (RelativeLayout) findViewById(R.id.res);


        //接受user对象
        Intent in = this.getIntent();
        newsUser = (NewsUser) in.getSerializableExtra("user");
        if(newsUser!= null) {
            gotoNews.setVisibility(View.VISIBLE);
            hoder.setText("欢迎");
            showLights(hoder,newsUser.getUserNickName(),"回来");

            //设置头部的长按事件
            headRes.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("请选择操作")
                            .setNegativeButton("注销登录", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent it = new Intent(MainActivity.this,UserLogin.class);
                                    it.putExtra("context","MainActivity");
                                    startActivity(it);
                                    finish();
                                }
                            }).create().show();
                    return false;
                }
            });
        }else{
            hoder.setText("您还未");
            showLight(hoder,"登录");
            gotoNews.setVisibility(View.GONE);
            hoder.setOnClickListener(this);
            headRes.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(MainActivity.this,"您还未登录，不能进行此操作",Toast.LENGTH_LONG).show();
                    return false;
                }
            });

        }
        list = newsDao.getAllNews();
        listView.setAdapter(new NewsUserAdapter(MainActivity.this,list));
        gotoNews.setOnClickListener(this);

        //下拉刷新
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list = newsDao.getAllNews();
                        listView.setAdapter(new NewsUserAdapter(MainActivity.this,list));
                        listView.stopRefresh();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                list = newsDao.getAllNews();
                listView.setAdapter(new NewsUserAdapter(MainActivity.this,list));
                listView.stopLoadMore();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_hoder:
                Animation ans = AnimationUtils.loadAnimation(this,R.anim.main_activity_listview);
                res.startAnimation(ans);
                ans.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent gotologin = new Intent(MainActivity.this,UserLogin.class);
                        gotologin.putExtra("context","MainActivity");
                        startActivity(gotologin);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.main_goto_save_news:
                Animation ani = AnimationUtils.loadAnimation(this,R.anim.main_activity_listview);
                res.startAnimation(ani);
                ani.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent gotoSaveNews = new Intent(MainActivity.this,SaveNews.class);
                        if(newsUser != null) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", newsUser);
                            gotoSaveNews.putExtras(bundle);
                        }
                        startActivity(gotoSaveNews);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
        }
    }
    class NewsUserAdapter extends BaseAdapter {
        private List<News> list = new ArrayList<News>();
        private Context context;

        public NewsUserAdapter(Context context , List<News> list){
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = new ViewHolder();
            if(null == view){
                view = LayoutInflater.from(context).inflate(R.layout.listview,null);
                holder = new ViewHolder();
                holder.listview_content = (TextView) view.findViewById(R.id.listview_content);
                holder.listview_user_name = (TextView) view.findViewById(R.id.listview_user_name);
                holder.listview_head_img  = (ImageView) view.findViewById(R.id.listview_head_img);
                holder.listview_img = (ImageView) view.findViewById(R.id.listview_img);
                holder.re = (RelativeLayout) view.findViewById(R.id.listview_relative);
                holder.listview_count = (TextView) view.findViewById(R.id.listview_count);
                holder.listview_date  = (TextView) view.findViewById(R.id.listview_create_date);
                view.setTag(holder);
            }else holder = (ViewHolder) view.getTag();

            holder.listview_content.setText(list.get(i).getNewsTitle());
            if(list.get(i).getNewsUser()!=null) {
                holder.listview_user_name.setText(list.get(i).getNewsUser().getUserName() + "");
            }
            holder.listview_head_img.setBackgroundResource(R.mipmap.ic_launcher);
            if(list.get(i).getNewsUser() != null) {
                holder.listview_user_name.setText(list.get(i).getNewsUser().getUserNickName());
            }
            holder.listview_img.setBackgroundResource(R.mipmap.news1);
            holder.listview_count.setText("共预览"+list.get(i).getNewsCount()+"次");
            holder.listview_date.setText(list.get(i).getCreateDate());

            holder.re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation an = AnimationUtils.loadAnimation(context,R.anim.main_activity_listview);
                    res.startAnimation(an);
                    an.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Intent intent = new Intent(context, NewsContent.class);
                            NewsDao ndao = new NewsDao(context);
                            News news = ndao.findById(list.get(i).getNewsId());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("news",news);
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
            });
            return view;
        }
        class ViewHolder{
            TextView listview_user_name,listview_content,listview_count,listview_date;
            ImageView listview_head_img,listview_img;
            RelativeLayout re;
        }
    }
}