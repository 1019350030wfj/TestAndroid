package com.jayden.testandroid.customview.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.InputStream;

/**
 * GifView<br>
 * 本类可以显示一个gif动画，其使用方法和android的其它view（如imageview)一样。<br>
 * 如果要显示的gif太大，会出现OOM的问题。
 * 
 * @author liao
 * 
 */
public class GifView extends View implements GifAction {

	/** gif解码器 */
	private GifDecoder gifDecoder = null;
	/** 当前要画的帧的图 */
	private Bitmap currentImage = null;

	private boolean pause = false;

	private int showWidth = -1;
	private int showHeight = -1;
	private Rect rect = null;

	private GifImageType animationType = GifImageType.SYNC_DECODER;
	
	private OnGifPlayEndListener onGifPlayEndListener;
	private AnimatableImpl animatableImpl;

	/**
	 * gif播放结束的监听
	 * 
	 * @author Xue Wenchao
	 *
	 */
	public interface OnGifPlayEndListener {
		public void onGifPlayEnd();
	}

	/**
	 * 解码过程中，Gif动画显示的方式<br>
	 * 如果图片较大，那么解码过程会比较长，这个解码过程中，gif如何显示
	 * 
	 * @author liao
	 * 
	 */
	public enum GifImageType {
		/**
		 * 在解码过程中，不显示图片，直到解码全部成功后，再显示
		 */
		WAIT_FINISH(0),
		/**
		 * 和解码过程同步，解码进行到哪里，图片显示到哪里
		 */
		SYNC_DECODER(1),
		/**
		 * 在解码过程中，只显示第一帧图片
		 */
		COVER(2);

		GifImageType(int i) {
			nativeInt = i;
		}

		final int nativeInt;
	}

	public GifView(Context context) {
		super(context);
		init();
	}

	public GifView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init();
	}

	public GifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		animatableImpl = new AnimatableImpl();
	}

	/**
	 * 设置gif播放结束监听
	 * @param onGifPlayEndListener
	 */
	public void setOnGifPlayEndListener(OnGifPlayEndListener onGifPlayEndListener) {
		this.onGifPlayEndListener = onGifPlayEndListener;
	}
	
	private void start() {
		AnimationThread.getInstance().addAnimatable(animatableImpl);
	}

	private void stop() {
		AnimationThread.getInstance().removeAnimatable(animatableImpl);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stop();
		if (gifDecoder != null) {
			gifDecoder.free();
		}
	}

	/**
	 * 设置图片，并开始解码
	 * 
	 * @param gif
	 *            要设置的图片
	 */
	private void setGifDecoderImage(byte[] gif, PlayMode playMode) {
		if (gifDecoder != null) {
			gifDecoder.free();
			gifDecoder = null;
		}
		gifDecoder = new GifDecoder(gif, this);
		gifDecoder.setPlayMode(playMode);
		gifDecoder.start();
	}

	/**
	 * 设置图片，开始解码
	 * 
	 * @param is
	 *            要设置的图片
	 */
	private void setGifDecoderImage(InputStream is, PlayMode playMode) {
		if (gifDecoder != null) {
			gifDecoder.free();
			gifDecoder = null;
		}
		gifDecoder = new GifDecoder(is, this);
		gifDecoder.setPlayMode(playMode);
		gifDecoder.start();
	}

	/**
	 * 以字节数据形式设置gif图片
	 * 
	 * @param gif
	 *            图片
	 */
	public void setGifImage(byte[] gif, PlayMode playMode) {
		setGifDecoderImage(gif, playMode);
	}

	/**
	 * 以字节流形式设置gif图片
	 * 
	 * @param is
	 *            图片
	 */
	public void setGifImage(InputStream is, PlayMode playMode) {
		setGifDecoderImage(is, playMode);
	}

	/**
	 * 以资源形式设置gif图片
	 * 
	 * @param resId
	 *            gif图片的资源ID
	 */
	public void setGifImage(int resId, PlayMode playMode) {
		Resources r = this.getResources();
		InputStream is = r.openRawResource(resId);
		setGifDecoderImage(is, playMode);
	}

	public void setGifImage(int resId) {
		setGifImage(resId, PlayMode.CONTINUOUS);
	}

	//当前祯开始时间
	private long currentFrameStartTime = 0;

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (gifDecoder == null) {
			return;
		}

		GifFrame frame = gifDecoder.getCurrentFrame();
		if (frame == null) {
			return;
		}
		long now = System.currentTimeMillis();
		if (now - currentFrameStartTime >= frame.delay) {
			frame = gifDecoder.next();
			currentFrameStartTime = now;
		}

		currentImage = frame.image;
		if (currentImage == null) {
			currentImage = gifDecoder.getImage();
		}
		if (currentImage == null) {
			return;
		}
		int saveCount = canvas.getSaveCount();
		canvas.save();
		canvas.translate(getPaddingLeft(), getPaddingTop());
		if (showWidth == -1) {
			canvas.drawBitmap(currentImage, 0, 0, null);
		}
		else {
			canvas.drawBitmap(currentImage, null, rect, null);
		}
		canvas.restoreToCount(saveCount);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int pleft = getPaddingLeft();
		int pright = getPaddingRight();
		int ptop = getPaddingTop();
		int pbottom = getPaddingBottom();

		int widthSize;
		int heightSize;

		int w;
		int h;

		if (gifDecoder == null) {
			w = 1;
			h = 1;
		}
		else {
			w = gifDecoder.width;
			h = gifDecoder.height;
		}

		w += pleft + pright;
		h += ptop + pbottom;

		w = Math.max(w, getSuggestedMinimumWidth());
		h = Math.max(h, getSuggestedMinimumHeight());

		widthSize = resolveSize(w, widthMeasureSpec);
		heightSize = resolveSize(h, heightMeasureSpec);

		//判断是否横向铺满,如果是,设置图片的绘制区域,等比放缩
		int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthSpecMode != MeasureSpec.AT_MOST && gifDecoder != null && gifDecoder.width > 0) {
			int showWidthSize = widthSize - pleft - pright;
			int showHeightSize = gifDecoder.height * widthSize / gifDecoder.width;
			setShowDimension(showWidthSize, showHeightSize);

			heightSize = showHeightSize + ptop + pbottom;
		}

		setMeasuredDimension(widthSize, heightSize);
	}

	/**
	 * 只显示第一帧图片<br>
	 * 调用本方法后，gif不会显示动画，只会显示gif的第一帧图
	 */
	public void showCover() {
		if (gifDecoder == null)
			return;
		pause = true;
		currentImage = gifDecoder.getImage();
		invalidate();
	}

	/**
	 * 继续显示动画<br>
	 * 本方法在调用showCover后，会让动画继续显示，如果没有调用showCover方法，则没有任何效果
	 */
	public void showAnimation() {
		if (pause) {
			pause = false;
		}
	}

	/**
	 * 设置gif在解码过程中的显示方式<br>
	 * <strong>本方法只能在setGifImage方法之前设置，否则设置无效</strong>
	 * 
	 * @param type
	 *            显示方式
	 */
	public void setGifImageType(GifImageType type) {
		if (gifDecoder == null)
			animationType = type;
	}

	/**
	 * 设置要显示的图片的大小<br>
	 * 当设置了图片大小 之后，会按照设置的大小来显示gif（按设置后的大小来进行拉伸或压缩）
	 * 
	 * @param width
	 *            要显示的图片宽
	 * @param height
	 *            要显示的图片高
	 */
	public void setShowDimension(int width, int height) {
		if (width > 0 && height > 0) {
			showWidth = width;
			showHeight = height;
			rect = new Rect();
			rect.left = 0;
			rect.top = 0;
			rect.right = width;
			rect.bottom = height;
		}
	}

	public void parseOk(boolean parseStatus, int frameIndex) {
		if (parseStatus) {
			if (gifDecoder != null) {
				switch (animationType) {
					case WAIT_FINISH:
						if (frameIndex == -1) {
							if (gifDecoder.getFrameCount() > 1) { // 当帧数大于1时，启动动画线程
								start();
							}
							else {
								reDraw();
							}
						}
						break;
					case COVER:
						if (frameIndex == 1) {
							currentImage = gifDecoder.getImage();
							reDraw();
						}
						else if (frameIndex == -1) {
							if (gifDecoder.getFrameCount() > 1) {
								start();
							}
							else {
								reDraw();
							}
						}
						break;
					case SYNC_DECODER:
						if (frameIndex == 1) {
							currentImage = gifDecoder.getImage();
							reDraw();
						}
						else if (frameIndex == -1) {
							reDraw();
						}
						else {
							start();
						}
						break;
				}

			}
			else {
				Log.e("gif", "parse error");
			}

		}
	}

	@Override
	public void OnGifEnd() {
		stop();
		if (onGifPlayEndListener != null) {
			onGifPlayEndListener.onGifPlayEnd();
		}
	}

	private void reDraw() {
		postInvalidate();
	}

	private class AnimatableImpl implements Animatable {
		@Override
		public void animate() {
			if (!pause) {
				postInvalidate();
			}
		}
	}

}
