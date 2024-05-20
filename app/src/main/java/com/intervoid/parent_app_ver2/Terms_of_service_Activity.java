package com.intervoid.parent_app_ver2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Terms_of_service_Activity extends AppCompatActivity {

    private WebView webView;
    private Button CheckButton;

    @Override
    protected void onCreate(Bundle saveInstaceState){
        super.onCreate(saveInstaceState);
        setContentView(R.layout.activity_terms_of_service);

        webView = (WebView) findViewById(R.id.terms_of_Service_webview);
        CheckButton =(Button) findViewById(R.id.terms_of_Service_checkbutton);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://policies.google.com/terms?hl=ko");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());

        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if((keycode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return  super.onKeyDown(keycode, event);
    }

    private class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

}
