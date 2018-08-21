package com.example.theshayds.mynewstest.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.theshayds.mynewstest.R;

public class ArticleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Get the url of an article
        Intent mIntent = getIntent();
        String mURL = mIntent.getStringExtra("URL");

        // Setup webView
        WebView mWebView = findViewById(R.id.webView);
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Load URL
        mWebView.loadUrl(mURL);
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
