package com.jayden.testandroid.customview.textview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/8/9.
 */

public class HtmlTextActivity extends AppCompatActivity {

    private String content =
            "<table width='100%'>" +
            "<tbody>" +
            "<tr>" +
            "<td style='border: 2px solid #000;' width='20%'>我<br></td>" +
            "<td style='border: 2px solid #000;' width='20%'>的<br></td>" +
            "<td style='border: 2px solid #000;' width='20%'>表<br></td>" +
            "</tr><tr><td style='border: 2px solid #000;' width='20%'>格<br></td>" +
            "<td style='border: 2px solid #000;' width='20%'>测<br></td>" +
            "<td style='border: 2px solid #000;' width='20%'>试<br></td>" +
            "</tr>" +
            "<tr>" +
            "<td style='border: 2px solid #000;' width='20%'>数<br></td>" +
            "<td style='border: 2px solid #000;' width='20%'>据<br></td>" +
            "<td style='border: 2px solid #000;' width='20%'><img width='100%' src=\\\"http://222.47.48.38/digital_wuhancaijin/uploads/44371470643990.jpg\\\" ><br></td>" +
            "</tr>" +
            "<tr>" +
            "<td style='border: 2px solid #000;' width='20%'><br></td>" +
            "<td style='border: 2px solid #000;' width='20%'><br></td>" +
            "<td style='border: 2px solid #000;' width='20%'><br></td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
                    "<br>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_html);
//        TextView textView = (TextView) findViewById(R.id.content);
//        textView.setText((new HtmlSpanner()).fromHtml(content));
        setContentView(R.layout.activity_webview);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.loadData(content, "text/html", "utf-8");
        webView.loadDataWithBaseURL(null, content, "text/html","UTF-8", null);
    }
}
