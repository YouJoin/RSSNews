package com.example.low.rssnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.low.rssnews.R;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Low on 2016/6/13.
 */
public class RSSActivity extends AppCompatActivity implements OkHttpUtil.OnDownDataListener {

    private WebSettings settings;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        webView = (WebView) findViewById(R.id.webV);

        settings = webView.getSettings();
        //打开页面时， 自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        settings.setJavaScriptEnabled(true);

        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("android.intent.extra.INTENT");

        String link = bundle.getString("link");

        OkHttpUtil.downJSON(link,this);


        webView.loadUrl(link);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中

                }

            }
        });
        //缓存的使用
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Toast.makeText(this, webView.getUrl(),
            // Toast.LENGTH_SHORT).show();
            if (webView.canGoBack()) {
                // 返回上一页面

                webView.goBack();
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResponse(String url, String json) {
        if (json!=null){
            Log.d("0000", "onResponse: "+json.toString());

        }
    }

    @Override
    public void onFailure(String url, String error) {

    }
}
