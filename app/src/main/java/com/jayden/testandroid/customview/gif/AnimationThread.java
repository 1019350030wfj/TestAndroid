package com.jayden.testandroid.customview.gif;

import java.util.ArrayList;

import android.os.SystemClock;

/**
 * 动画线程
 * 
 * @author Xue Wenchao
 *
 */
public class AnimationThread implements Runnable {
	
	public static final int INTERVAL = 20;
	private ArrayList<Animatable> animatableList = null;
	private Thread thread;
	private boolean isAlive = false;
	private static AnimationThread inst = new AnimationThread();

	private AnimationThread() {

	}

	public static AnimationThread getInstance() {
		return inst;
	}

	public void addAnimatable(Animatable animatable) {
		synchronized (this) {
			if (animatableList == null) {
				animatableList = new ArrayList<Animatable>();
			}
			if (!animatableList.contains(animatable)) {
				animatableList.add(animatable);
				start();
			}
		}
	}

	public void removeAnimatable(Animatable animatable) {
		synchronized (this) {
			if (animatableList == null) {
				return;
			}
			animatableList.remove(animatable);
		}
	}

	private void start() {
		synchronized (this) {
			if (!isAlive && thread == null) {
				isAlive = true;
				thread = new Thread(this);
				thread.start();
			}
		}
	}

	private void stop() {
		isAlive = false;
		thread = null;
	}

	@Override
	public void run() {
		long start, end, consume;
		while (isAlive) {
			try {
				synchronized (this) {
					if (animatableList == null || animatableList.size() == 0) {
						stop();
						break;
					}
				}
				start = System.currentTimeMillis();
				for (int i = 0; i < animatableList.size(); i++) {
					animatableList.get(i).animate();
				}
				end = System.currentTimeMillis();
				consume = end - start;
				if (consume < INTERVAL) {
					SystemClock.sleep(INTERVAL - consume);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
