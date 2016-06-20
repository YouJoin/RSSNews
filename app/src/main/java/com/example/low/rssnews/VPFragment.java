package com.example.low.rssnews;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.low.rssnews.Rss.RssFeed;
import com.example.low.rssnews.Rss.RssItem;
import com.example.low.rssnews.Rss.RssReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Low on 2016/6/15.
 */
public class VPFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private ArrayList<RssItem> list;
    /**
     * 给ListView添加适配器
     */
    private RSSAdapter adapter;
    private String urlstr;
    /**
     * 给ListView添加下拉刷新
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    public static VPFragment getInstance(String url) {
        VPFragment fragment = new VPFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                ArrayList<RssItem> rssItems = (ArrayList<RssItem>) msg.obj;
                Log.e("123", "handleMessage: " + rssItems.toString());

                //更新数据集合
                list.addAll(rssItems);
                //通知适配器更新视图
                adapter.notifyDataSetChanged();
               // adapter.setDatas(rssItems);

            }
        }
    };


    @Override
    protected int getViewResid() {
        return R.layout.fragment_listview;
    }

    @Override
    protected void init(View view) {
        super.init(view);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        // 顶部刷新的样式
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listView = (ListView) view.findViewById(R.id.listView_fragment);
        list = new ArrayList<>();
        adapter = new RSSAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itemintent = new Intent(getActivity(), RSSActivity.class);

                Bundle b = new Bundle();
                b.putString("link", list.get(position).getLink());
                itemintent.putExtra("android.intent.extra.INTENT", b);
                startActivity(itemintent);

                getActivity().overridePendingTransition(R.anim.default_anim_in,R.anim.default_anim_out);

            }
        });
    }

    @Override
    protected void loadDatas() {
        Bundle bundle = getArguments();
        urlstr = bundle.getString("url");
        new Thread(run).start();

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL(urlstr);
                Log.d("url", "runnable: " + url.toString());
                RssFeed feed = RssReader.read(url);
                ArrayList<RssItem> rssItems = feed.getRssItems();

                Message message =handler.obtainMessage();
                message.what = 123;
                message.obj = rssItems;

                handler.sendMessage(message);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        }, 2000);

    }
}
