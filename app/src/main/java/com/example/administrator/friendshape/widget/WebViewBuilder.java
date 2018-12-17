package com.example.administrator.friendshape.widget;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.administrator.friendshape.utils.LogUtil;

/**
 * 作者：真理 Created by Administrator on 2018/11/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WebViewBuilder {

    private String TAG = getClass().getSimpleName();

    public WebView web_view;
    public ProgressBar progressBar;

    public WebViewBuilder(WebView web_view, ProgressBar progressBar) {
        this.web_view = web_view;
        this.progressBar = progressBar;
    }

    public void initWebView() {
        web_view.setWebChromeClient(webChromeClient);
        web_view.setWebViewClient(webViewClient);
        web_view.getSettings().setJavaScriptEnabled(true);//设置js可用
        WebSettings webSettings = web_view.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();
            //注意:必须要这一句代码:result.confirm()表示: 处理结果为确定状态同时唤醒WebCore线程 否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar != null)
                progressBar.setProgress(newProgress);
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equals("http://www.google.com/")) {
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();  // 接受所有网站的证书
        }
    };

    public void loadWebView(String html, boolean status) {
        if (status) {
            web_view.loadUrl(html);
            LogUtil.e(TAG, "loadUrl");
        } else {
            web_view.loadDataWithBaseURL(null, "<html><body>" + html + "</body></html>", "text/html", "utf-8", null);
            LogUtil.e(TAG, "loadDataWithBaseURL : " + "<html><body>" + html + "</body></html>");
        }
    }

}
