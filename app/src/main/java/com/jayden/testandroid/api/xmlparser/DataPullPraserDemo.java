package com.jayden.testandroid.api.xmlparser;

import android.app.ListActivity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jayden.testandroid.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPullPraserDemo extends ListActivity {

    private List<Map<String, String>> views;
    //
    private Map<String,String> idValues = new HashMap<>();//
    private Map<String,String> idNames = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        views = getView();
        SimpleAdapter adapter = new SimpleAdapter(this,views ,
                R.layout.list, new String[]{"id", "name"}, new int[]{
                R.id.textId, R.id.textName});
        setListAdapter(adapter);



        getData();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ParserResultActivity.startResultActivity(this,views,idNames,idValues);
    }

    private void getData() {
        XmlResourceParser xrp = getResources().getXml(R.xml.data);
        try {
            // 直到文档的结尾处
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 如果遇到了开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();// 获取标签的名字
                    if (tagName.equals("Element")) {
                        String id = xrp.getAttributeValue(null, "InputID");// 通过属性名来获取属性值
                        String ColumnName = xrp.getAttributeValue(null, "ColumnName");// 通过属性名来获取属性值
                        idNames.put(id, ColumnName);
                        idValues.put(id, xrp.nextText());
                    }
                }
                xrp.next();// 获取解析下一个事件
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Map<String, String>> getView() {
        List<Map<String, String>> list = new ArrayList<>();
        XmlResourceParser xrp = getResources().getXml(R.xml.view);

        try {
            // 直到文档的结尾处
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                // 如果遇到了开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();// 获取标签的名字
                    if (tagName.equals("TextEdit")) {
                        Map<String, String> map = new HashMap<String, String>();
                        String id = xrp.getAttributeValue(null, "InputID");// 通过属性名来获取属性值
                        map.put("id", id);
                        String X = xrp.getAttributeValue(0);// 通过属性索引来获取属性值
                        map.put("X", X);
                        String Y = xrp.getAttributeValue(1);// 通过属性索引来获取属性值
                        map.put("Y", Y);
                        String width = xrp.getAttributeValue(2);// 通过属性索引来获取属性值
                        map.put("width", width);
                        String height = xrp.getAttributeValue(3);// 通过属性索引来获取属性值
                        map.put("height", height);

                        String text = xrp.getAttributeValue(null, "Text");// 通过属性名来获取属性值
                        map.put("text", text);

                        String TextColor = xrp.getAttributeValue(null, "TextColor");// 通过属性名来获取属性值
                        map.put("TextColor", TextColor);

                        String TextSize = xrp.getAttributeValue(null, "TextSize");// 通过属性名来获取属性值
                        map.put("name", TextSize);
                        list.add(map);
                    }
                }
                xrp.next();// 获取解析下一个事件
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
