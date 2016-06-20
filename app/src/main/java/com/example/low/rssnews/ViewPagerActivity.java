package com.example.low.rssnews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Low on 2016/6/15.
 */
public class ViewPagerActivity extends AppCompatActivity {

    //url数组
    private String[] urlString = { AllURL.SPORT_URL,
            AllURL.WORLD_URL, AllURL.FINANCE_URL,AllURL.EDU_URL,AllURL.GAME_URL};
    //标题集合
    private List<String> nameDatas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        init();
    }

    //初始化控件
    private void init() {
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager);

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tablayout);

        nameDatas.add("体育");
        //nameDatas.add("新闻");
        nameDatas.add("国际");
        nameDatas.add("经济");
        nameDatas.add("教育");
        nameDatas.add("游戏");

        MViewPager mViewPager=new MViewPager(getSupportFragmentManager(),urlString);

        viewPager.setAdapter(mViewPager);

        //ViewPager和TabLayout关联
        tabLayout.setupWithViewPager(viewPager);
    }

    //viewpager适配器
    class MViewPager extends FragmentStatePagerAdapter {

        private final String[] datas;

        public MViewPager(FragmentManager fm, String[] urlString) {
            super(fm);
            this.datas=urlString;
        }

        @Override
        public Fragment getItem(int position) {
            return VPFragment.getInstance(datas[position]);
        }

        @Override
        public int getCount() {
            return datas.length;
        }

        /**
         * 该方法返回item所对应的标题名称
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return nameDatas.get(position).toString();
        }
    }
}
