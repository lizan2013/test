package cn.mondora.appreciationzone;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BridgeWebView webView;
    private String headUrl = "http://cn.bing.com/";
//    private X5WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        findViewById(R.id.tab3).setOnClickListener(this);
        findViewById(R.id.tab4).setOnClickListener(this);
        webView = (BridgeWebView) findViewById(R.id.webView);

        webView.setDefaultHandler(new DefaultHandler());
        webView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack("submitFromWeb exe, response data from Java");
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setRenderPriority(RenderPriority.HIGH);
        webView.setWebViewClient(new MyWebClient(webView, getApplicationContext()) {

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        webView.loadUrl("http://cn.bing.com/");
        //必须和js同名函数，注册具体执行函数，类似java实现类。
        webView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                String str = "这是html返回给java的数据:" + data;
                // 例如你可以对原始数据进行处理
                function.onCallBack(str + ",Java经过处理后截取了一部分：" + str.substring(0, 5));
            }

        });

        webView.callHandler("functionInJs", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
        SendData senddata = new SendData();
        senddata.setUserId("");
        senddata.setSessionid("");
        webView.send("hello");
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
