package com.jayden.testandroid.customview.gif;

/**
 * gif的播放模式定义
 * 
 * @author Xue Wenchao
 *
 */
public enum PlayMode {
	/**
	 * 循环播放
	 */
	CONTINUOUS(0),

	/**
	 * 只播放一次
	 */
	ONCE(1);

	PlayMode(int i) {
		nativeInt = i;
	}

	final int nativeInt;
}
