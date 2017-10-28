package me.imhere.inshorts;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebPage extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        WebView mWebview  = new WebView(this);
        String url =getIntent().getStringExtra("url");

        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse)
            {

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
               Toast.makeText(getApplicationContext(),"HTTP ERROR",Toast.LENGTH_LONG).show();
            }
        });

        mWebview.loadUrl(url);
        setContentView(mWebview);
    }

}