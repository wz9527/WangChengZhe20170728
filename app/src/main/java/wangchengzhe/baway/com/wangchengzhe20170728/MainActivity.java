package wangchengzhe.baway.com.wangchengzhe20170728;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wangchengzhe.baway.com.wangchengzhe20170728.adapter.MyAdapter;
import wangchengzhe.baway.com.wangchengzhe20170728.bean.NewsBean;


public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private Button mButton;
    private Button jiazai;
    private MyAdapter mAdapter;
    private List<NewsBean.DataBean>  mList=new ArrayList<>();
    private OkHttpClient mOkHttpClient;
    private String a;

    private  Button mm;

    private   int NUM=1;
    private String url="http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page="+NUM;


        private  Handler mHandler=new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    if (msg.what==1){
                        Gson gson =new Gson();

                        String str = (String) msg.obj;
                        Log.e("----", "handleMessage: "+str);
                        NewsBean newsBean = gson.fromJson(str, NewsBean.class);


                        List<NewsBean.DataBean> data = newsBean.getData();
                        mList.addAll(data);
                        mAdapter.setData(mList);
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Logger.i("哈哈");


      /*  if (NetworkUtils.isWifi(this)){
            Logger.i("哈哈");
            finish();
        }*/
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(this);
//        mRecyclerView.setItemAnimator(new );
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"item"+position,Toast.LENGTH_LONG).show();
            }
        });

        initData();


        jiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUM++;
                getAsynHttp();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mm.setText("");
            }
        });


    }

    private void initData() {
        getAsynHttp();

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mButton=(Button)findViewById(R.id.button);
        jiazai=(Button)findViewById(R.id.jiazai);
    }

    private void getAsynHttp() {
        mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", "onFailure: ."+e.getMessage());

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.e("onResponse", "onResponse: "+ response.networkResponse().toString());
                Message message = new Message();
                message.what=1;
                message.obj=response.body().string();
                mHandler.sendMessage(message);

            }
        });


    }
}


