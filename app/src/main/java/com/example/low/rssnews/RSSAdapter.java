package com.example.low.rssnews;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.low.rssnews.Rss.RssItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Low on 2016/6/13.
 */
public class RSSAdapter extends BaseAdapter {

    private ArrayList<RssItem> datas;
    private final Context context;

    public RSSAdapter(Context context, ArrayList<RssItem> list) {
        this.context=context;
        //datas=new ArrayList<>();
        this.datas = list;
    }

    public void setDatas(ArrayList<RssItem> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public void addDatas(ArrayList<RssItem> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //convertView：如果加载item的时候recycle中没有预先加载，就是null的，需要自己加载布局,将该找的控件找出来存入ViewHolder,在把viewHolder对象存入convertview,以备复用
        //如果recycle中预先加载过了，系统就会把对应的position的子视图对象传回给convertView参数，利用convertView获得控件对象即可而不需要重新实例化布局和找控件
        //通过布局加载器，将子视图对象实例化
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.top_pager_1_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.top_news_item_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.top_news_item_description);
            viewHolder.source = (TextView) convertView.findViewById(R.id.top_news_item_source);
            viewHolder.time = (TextView) convertView.findViewById(R.id.top_news_item_time);
            //将viewholder存入convertView，如果复用的时候convertview会被系统传回，我们利用convertview就可以得到viewholder
            convertView.setTag(viewHolder);
            Log.e("RSSAdapter", "新建 item， getView...." + position);
        }else {
            //复用item的时候就要从convertView中获得viewholder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(datas.get(position).getTitle());


        //判断荣内是否为空
        if (datas.get(position).getDescription()==null){
            //教育板块的内容是空的
            viewHolder.description.setText(datas.get(position).getDescription());
        }else{
            String substring = datas.get(position).getDescription().substring(6);
            viewHolder.description.setText(substring);
        }

        viewHolder.time.setText(datas.get(position).getPubDate()+"");
        viewHolder.source.setText(datas.get(position).getAuthor());




        return convertView;
    }

    class ViewHolder{
        TextView title,time,source,description;
    }

}
