package com.abyxcz.mad_webviews

import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.abyxcz.mad_webviews.ui.theme.MADWebviewsTheme
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState


const val BASE_URL = "https://www.quadbuddy.com/privacy"
const val SERVICE_URL = "webviews/"
const val DEFAULT_TOKEN = "12345"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MADWebviewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    //AccompanistWebClient(url = "${BASE_URL}${SERVICE_URL}${DEFAULT_TOKEN}")
                    WebViewSample(url = "${BASE_URL}")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MADWebviewsTheme {
        Greeting("Android")
    }
}

@Composable
fun AccompanistWebClient(
    modifier: Modifier = Modifier,
    url: String,
) {

    val webViewState = rememberWebViewState(url = url)
    WebView(
        modifier = modifier,
        state = webViewState,
        captureBackPresses = true,
        onCreated = { it : WebView ->
            it.settings.javaScriptEnabled = true
        }
    )
}

@Composable
fun WebViewSample(
    url: String,
    webViewClient: WebViewClient = WebViewClient()
) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
                settings.javaScriptEnabled = true

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)

                addJavascriptInterface(object : Any() {
                    @JavascriptInterface
                    fun onButtonClick(buttonId: String) {
                        // Log the button click event
                        Log.d("WebViewConsole", "Btn Clicked: $buttonId")

                        post {
                            when (buttonId) {

                                "next-btn" -> {
                                    //add your action in here
                                    Log.d("WebViewConsole", "NEXT BUTTON CLICKED!")
                                }
                            }
                        }
                    }
                }, "Android")
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}