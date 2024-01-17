package com.abyxcz.mad_webviews

import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient: WebViewClient(){
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if(url != null && url.startsWith("https://quadbuddy.com")){
            view?.loadUrl(url)
            return true
        }
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {

// Page has finished loading, execute JavaScript code to capture "back-btn" and "next-btn"

        view?.evaluateJavascript(
            """                   
    document.getElementById('next-btn').addEventListener('click', function() {
      Android.onButtonClick('next-btn');
    });
    """.trimIndent(), null
        )
    }
}