package com.jayden.testandroid.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.jayden.testandroid.R;

public class HTMLActivity extends Activity {

	private WebView myWebView = null;

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html_main);

		myWebView = (WebView) findViewById(R.id.myWebView);

		WebSettings webSettings = myWebView.getSettings();

		webSettings.setJavaScriptEnabled(true);

		// Sets the chrome handler. This is an implementation of WebChromeClient
		// for use in handling JavaScript dialogs, favicons, titles, and the
		// progress. This will replace the current handler.
		myWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

		});

		myWebView.addJavascriptInterface(new WebAppInterface(this),
				"myInterfaceName");

		myWebView.loadUrl("file:///android_asset/callapp.html");

	}

	public class WebAppInterface {
		Context mContext;

		/** Instantiate the interface and set the context */
		WebAppInterface(Context c) {
			mContext = c;
		}

		/** Show a toast from the web page */
		@JavascriptInterface
		public void showToast(String toast) {
			// Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
			Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
		}
	}

}