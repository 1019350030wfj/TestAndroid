package com.jayden.testandroid.api.wechat;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * @文件名称: MyDialog.java
 * @功能描述: 自定义dialog
 * @版本信息: Copyright (c)2014
 * @开发人员: vincent
 * @版本日志: 1.0
 * @创建时间: 2014年3月18日 下午1:45:38
 */
public class MyDialog extends Dialog implements OnClickListener {
	private TextView leftTextView, rightTextView;
	private IDialogOnclickInterface dialogOnclickInterface;
	private Context context;

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dialog_weichat_del);

		leftTextView = (TextView) findViewById(R.id.textview_one);
		rightTextView = (TextView) findViewById(R.id.textview_two);
		leftTextView.setOnClickListener(this);
		rightTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dialogOnclickInterface = (IDialogOnclickInterface) context;
		switch (v.getId()) {
		case R.id.textview_one:
			dialogOnclickInterface.leftOnclick();
			break;
		case R.id.textview_two:
			dialogOnclickInterface.rightOnclick();
			break;
		default:
			break;
		}
	}

	public interface IDialogOnclickInterface {
		void leftOnclick();

		void rightOnclick();
	}
}
