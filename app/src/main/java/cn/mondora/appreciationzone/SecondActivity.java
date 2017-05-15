package cn.mondora.appreciationzone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.mondora.appreciationzone.utils.X5WebView;

/**
 * Created by edz on 2017/4/7.
 */

public class SecondActivity extends Activity implements View.OnClickListener{
    private X5WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        webView=(X5WebView)findViewById(R.id.tbs_webView);

        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
//                webView.loadUrl("http://101.201.64.119:8088/front/#/notice-center?fromto=android&version=3.7.3");
                webView.loadUrl("http://cn.bing.com/");
//                webView.loadUrl("javascript:location.hash = '#" + "finance-huo?fromto=android" + "';");
                break;
            case R.id.tab2:
                webView.loadUrl("http://cn.bing.com/");
//                webView.loadUrl("http://test.dlszkj.com:8000/front/#/finance?fromto=android&version=3.7.8");

                break;
            case R.id.tab3:
                webView.loadUrl("http://cn.bing.com/");
                break;
            case R.id.tab4:
                webView.loadUrl("http://cn.bing.com/");
                break;
        }
    }
}
