package com.jayden.testandroid.fenghuangchuangyi;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jayden on 2017/3/2.
 */
public class Onesoft3DNavigationLayout extends ViewGroup {

    public static final int LANGUAGE_CHINESE = 0;
    public static final int LANGUAGE_ENGLISH = 1;

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef({LANGUAGE_CHINESE, LANGUAGE_ENGLISH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LANGUAGE {
    }

    @LANGUAGE
    private int mLanguage = LANGUAGE_CHINESE;//默认是中文

    private int mItemWidth = 55;//110px ,55dp
    private int mPadding = 16;//每个ViewGroup的padding 为16px

    private TextView mTextView;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private TextView mTextView1;
    private Button mButton11;
    private Button mButton21;
    private Button mButton31;
    private Button mButton41;

    private TextView mTextView2;
    private Button mButton12;
    private Button mButton22;
    private Button mButton32;
    private Button mButton42;
    private Button mButton52;
    private Button mButton62;

    private boolean mButton12Pressed;
    private boolean mButton22Pressed;
    private boolean mButton32Pressed;
    private boolean mButton42Pressed;
    private boolean mButton52Pressed;
    private boolean mButton62Pressed;

    private int mWidthFour;//四个的宽度
    private int mWidthFive;//五个的宽度
    private int mWidthSeven;//7个的宽度
    private boolean mIsBigThanScreenWidth;//7个按钮的宽度是否大于屏幕的宽度

    private int mScreentWidth;
    private int mTotalHeight;
    private int mTotalRow = 3;//总共几行

    public Onesoft3DNavigationLayout(Context context) {
        this(context, null);
    }

    public Onesoft3DNavigationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Onesoft3DNavigationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        mScreentWidth = wm.getDefaultDisplay().getWidth();// 宽度
        mItemWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55,
                context.getResources().getDisplayMetrics());
        initView(context, attrs, defStyleAttr);
        initSize();
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mTextView = new TextView(context, attrs, defStyleAttr);
        mTextView1 = new TextView(context, attrs, defStyleAttr);
        mTextView2 = new TextView(context, attrs, defStyleAttr);

        mButton1 = new Button(context, attrs, defStyleAttr);
        mButton1.setClickable(true);
        mButton2 = new Button(context, attrs, defStyleAttr);
        mButton2.setClickable(true);
        mButton3 = new Button(context, attrs, defStyleAttr);
        mButton3.setClickable(true);

        mButton11 = new Button(context, attrs, defStyleAttr);
        mButton21 = new Button(context, attrs, defStyleAttr);
        mButton31 = new Button(context, attrs, defStyleAttr);
        mButton41 = new Button(context, attrs, defStyleAttr);
        mButton11.setClickable(true);
        mButton21.setClickable(true);
        mButton31.setClickable(true);
        mButton41.setClickable(true);

        mButton12 = new Button(context, attrs, defStyleAttr);
        mButton22 = new Button(context, attrs, defStyleAttr);
        mButton32 = new Button(context, attrs, defStyleAttr);
        mButton42 = new Button(context, attrs, defStyleAttr);
        mButton52 = new Button(context, attrs, defStyleAttr);
        mButton62 = new Button(context, attrs, defStyleAttr);
        mButton12.setClickable(true);
        mButton22.setClickable(true);
        mButton32.setClickable(true);
        mButton42.setClickable(true);
        mButton52.setClickable(true);
        mButton62.setClickable(true);

        addView(mTextView);
        addView(mButton1);
        addView(mButton2);
        addView(mButton3);

        addView(mTextView1);
        addView(mButton11);
        addView(mButton21);
        addView(mButton31);
        addView(mButton41);

        addView(mTextView2);
        addView(mButton12);
        addView(mButton22);
        addView(mButton32);
        addView(mButton42);
        addView(mButton52);
        addView(mButton62);

        setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_onesoft3d_navigation));
        setViewBackground(LANGUAGE_CHINESE);

        mButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.walk_dimmed));
                    mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fly_normal));
                    mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.examine_normal));
                } else {
                    mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.walk_dimmed_e));
                    mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fly_normal_e));
                    mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.examine_normal_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(0, 0);
                }
            }
        });

        mButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.walk_normal));
                    mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fly_dimmed));
                    mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.examine_normal));
                } else {
                    mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.walk_normal_e));
                    mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fly_dimmed_e));
                    mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.examine_normal_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(1, 1);
                }
            }
        });
        mButton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.walk_normal));
                    mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fly_normal));
                    mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.examine_dimmed));
                } else {
                    mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.walk_normal_e));
                    mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.fly_dimmed_e));
                    mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.examine_dimmed_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(2, 2);
                }
            }
        });

        mButton11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_dimmed));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_normal));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_normal));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_normal));
                } else {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_dimmed_e));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_normal_e));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_normal_e));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_normal_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(3, 3);
                }
            }
        });
        mButton21.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_normal));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_dimmed));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_normal));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_normal));
                } else {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_normal_e));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_dimmed_e));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_normal_e));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_normal_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(4, 4);
                }
            }
        });
        mButton31.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_normal));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_normal));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_dimmed));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_normal));
                } else {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_normal_e));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_normal_e));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_dimmed_e));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_normal_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(5, 5);
                }
            }
        });
        mButton41.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage == LANGUAGE_CHINESE) {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_normal));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_normal));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_normal));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_dimmed));
                } else {
                    mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.pan_normal_e));
                    mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.plan_normal_e));
                    mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.roll_normal_e));
                    mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.turn_dimmed_e));
                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(6, 6);
                }
            }
        });

        mButton12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mButton12Pressed) {//选中
//                    mButton12Pressed = false;
//                    mButton12.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.previous_viewpoint_normal) : getResources().getDrawable(R.drawable.previous_viewpoint_normal_e));
//                } else {
//                    mButton12Pressed = true;
//                    mButton12.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.previous_viewpoint_dimmed) : getResources().getDrawable(R.drawable.previous_viewpoint_dimmed_e));
//                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(7, 7);
                }
            }
        });

        mButton22.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mButton22Pressed) {//选中
//                    mButton22Pressed = false;
//                    mButton22.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.next_viewpoint_normal) : getResources().getDrawable(R.drawable.next_viewpoint_normal_e));
//                } else {
//                    mButton22Pressed = true;
//                    mButton22.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.next_viewpoint_dimmed) : getResources().getDrawable(R.drawable.next_viewpoint_dimmed_e));
//                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(8, 8);
                }
            }
        });

        mButton32.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mButton32Pressed) {//选中
//                    mButton32Pressed = false;
//                    mButton32.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.seek_normal) : getResources().getDrawable(R.drawable.seek_normal_e));
//                } else {
//                    mButton32Pressed = true;
//                    mButton32.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.seek_dimmed) : getResources().getDrawable(R.drawable.seek_dimmed_e));
//                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(9, 9);
                }
            }
        });

        mButton42.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mButton42Pressed) {//选中
//                    mButton42Pressed = false;
//                    mButton42.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.fit_normal) : getResources().getDrawable(R.drawable.fit_normal_e));
//                } else {
//                    mButton42Pressed = true;
//                    mButton42.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.fit_dimmed) : getResources().getDrawable(R.drawable.fit_dimmed_e));
//                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(10, 10);
                }
            }
        });

        mButton52.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mButton52Pressed) {//选中
//                    mButton52Pressed = false;
//                    mButton52.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.straightup_normal) : getResources().getDrawable(R.drawable.straightup_normal_e));
//                } else {
//                    mButton52Pressed = true;
//                    mButton52.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.straightup_dimmed) : getResources().getDrawable(R.drawable.straightup_dimmed_e));
//                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(11, 11);
                }
            }
        });

        mButton62.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mButton62Pressed) {//选中
//                    mButton62Pressed = false;
//                    mButton62.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.restore_normal) : getResources().getDrawable(R.drawable.restore_normal_e));
//                } else {
//                    mButton62Pressed = true;
//                    mButton62.setBackgroundDrawable(mLanguage == LANGUAGE_CHINESE ? getResources().getDrawable(R.drawable.restore_dimmed) : getResources().getDrawable(R.drawable.restore_dimmed_e));
//                }
                if (mIOnesoft3dNav != null) {
                    mIOnesoft3dNav.onAction(12, 12);
                }
            }
        });
    }

    public void setLanguage(@LANGUAGE int language) {//设置语言
        mLanguage = language;
        setViewBackground(language);
    }

    private void setViewBackground(int language) {
        if (LANGUAGE_CHINESE == language) {
            mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_roam_dimmed));
            mTextView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_move_dimmed));
            mTextView2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_viewoperate_dimmed));
            mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_walk_dimmed));
            mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fly_dimmed));
            mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_examine_dimmed));

            mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pan_dimmed));
            mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_plan_dimmed));
            mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_roll_dimmed));
            mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_turm_dimmed));

            mButton12.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_previous_dimmed));
            mButton22.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_next_dimmed));
            mButton32.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_seek_dimmed));
            mButton42.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fit_dimmed));
            mButton52.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_straightup_dimmed));
            mButton62.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_restore_dimmed));
        } else {
            mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_roam_dimmed_e));
            mTextView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_move_dimmed_e));
            mTextView2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_viewoperate_dimmed_e));
            mButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_walk_dimmed_e));
            mButton2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fly_dimmed_e));
            mButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_examine_dimmed_e));

            mButton11.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pan_dimmed_e));
            mButton21.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_plan_dimmed_e));
            mButton31.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_roll_dimmed_e));
            mButton41.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_turm_dimmed_e));

            mButton12.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_previous_dimmed_e));
            mButton22.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_next_dimmed_e));
            mButton32.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_seek_dimmed_e));
            mButton42.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_fit_dimmed_e));
            mButton52.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_straightup_dimmed_e));
            mButton62.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_restore_dimmed_e));
        }
    }

    public void setItemWidth(int itemWidth) {//设置每个按钮的宽度
        mItemWidth = itemWidth;
        initSize();
        invalidate();
    }

    private void initSize() {
        mWidthFour = 4 * mItemWidth;
        mWidthFive = 5 * mItemWidth;
        mWidthSeven = 7 * mItemWidth;
        if (mWidthSeven + mPadding * 2 > mScreentWidth) {//如果7个按钮的宽度大于屏幕的宽度，就等于屏幕的宽度
            mIsBigThanScreenWidth = true;
            mWidthSeven = mScreentWidth;
            mItemWidth = (mScreentWidth - mPadding * 2) / 7; //每个item的宽度
            mWidthFour = 4 * mItemWidth;
            mWidthFive = 5 * mItemWidth;
        } else {
            mIsBigThanScreenWidth = false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mMeasureWidth = widthMeasureSpec;
        int mMeasureHeight = heightMeasureSpec;
        if (mIsBigThanScreenWidth) {//7个按钮的宽度和大于屏幕， 按钮分为3行
            mMeasureWidth = MeasureSpec.makeMeasureSpec(mWidthSeven + mPadding * 2, MeasureSpec.EXACTLY);
            mTotalHeight = mItemWidth * 3 + mPadding * 2;
            mMeasureHeight = MeasureSpec.makeMeasureSpec(mTotalHeight, MeasureSpec.EXACTLY);
            mTotalRow = 3;
        } else {//屏幕宽度大于7个按钮宽度之和， 说明7个按钮可以一行
            if ((mWidthFive + mWidthFour + mPadding * 2) > mScreentWidth) {//9个按钮的宽度和大于屏幕， 按钮分为3行
                mMeasureWidth = MeasureSpec.makeMeasureSpec(mWidthSeven + mPadding * 2, MeasureSpec.EXACTLY);
                mTotalHeight = mItemWidth * 3 + mPadding * 2;
                mMeasureHeight = MeasureSpec.makeMeasureSpec(mTotalHeight, MeasureSpec.EXACTLY);
                mTotalRow = 3;
            } else {//两行，或者1行
                if ((mWidthFive + mWidthFour + mWidthSeven + mPadding * 2) <= mScreentWidth) {//1行是否可以放得完
                    mMeasureWidth = MeasureSpec.makeMeasureSpec(mWidthFive + mWidthFour + mWidthSeven + mPadding * 2, MeasureSpec.EXACTLY);
                    mTotalHeight = mItemWidth + mPadding * 2;
                    mMeasureHeight = MeasureSpec.makeMeasureSpec(mTotalHeight, MeasureSpec.EXACTLY);
                    mTotalRow = 1;
                } else {//两行
                    mMeasureWidth = MeasureSpec.makeMeasureSpec(mWidthFive + mWidthFour + mPadding * 2, MeasureSpec.EXACTLY);
                    mTotalHeight = mItemWidth * 2 + mPadding * 2;
                    mMeasureHeight = MeasureSpec.makeMeasureSpec(mTotalHeight, MeasureSpec.EXACTLY);
                    mTotalRow = 2;
                }
            }
        }

        measureChildren(mMeasureWidth, mMeasureHeight);
        setMeasuredDimension(mMeasureWidth, mMeasureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        initLayoutParams();
        if (mTotalRow == 3) {//三行
            mTextView.layout(mPadding, mPadding, mPadding + mItemWidth, mPadding + mItemWidth);
            mButton1.layout(mPadding + mItemWidth, mPadding, mPadding + mItemWidth * 2, mPadding + mItemWidth);
            mButton2.layout(mPadding + mItemWidth * 2, mPadding, mPadding + mItemWidth * 3, mPadding + mItemWidth);
            mButton3.layout(mPadding + mItemWidth * 3, mPadding, mPadding + mItemWidth * 4, mPadding + mItemWidth);

            mTextView1.layout(mPadding, mPadding + mItemWidth, mPadding + mItemWidth, mPadding + mItemWidth * 2);
            mButton11.layout(mPadding + mItemWidth, mPadding + mItemWidth, mPadding + mItemWidth * 2, mPadding + mItemWidth * 2);
            mButton21.layout(mPadding + mItemWidth * 2, mPadding + mItemWidth, mPadding + mItemWidth * 3, mPadding + mItemWidth * 2);
            mButton31.layout(mPadding + mItemWidth * 3, mPadding + mItemWidth, mPadding + mItemWidth * 4, mPadding + mItemWidth * 2);
            mButton41.layout(mPadding + mItemWidth * 4, mPadding + mItemWidth, mPadding + mItemWidth * 5, mPadding + mItemWidth * 2);

            mTextView2.layout(mPadding, mPadding + mItemWidth * 2, mPadding + mItemWidth, mPadding + mItemWidth * 3);
            mButton12.layout(mPadding + mItemWidth, mPadding + mItemWidth * 2, mPadding + mItemWidth * 2, mPadding + mItemWidth * 3);
            mButton22.layout(mPadding + mItemWidth * 2, mPadding + mItemWidth * 2, mPadding + mItemWidth * 3, mPadding + mItemWidth * 3);
            mButton32.layout(mPadding + mItemWidth * 3, mPadding + mItemWidth * 2, mPadding + mItemWidth * 4, mPadding + mItemWidth * 3);
            mButton42.layout(mPadding + mItemWidth * 4, mPadding + mItemWidth * 2, mPadding + mItemWidth * 5, mPadding + mItemWidth * 3);
            mButton52.layout(mPadding + mItemWidth * 5, mPadding + mItemWidth * 2, mPadding + mItemWidth * 6, mPadding + mItemWidth * 3);
            mButton62.layout(mPadding + mItemWidth * 6, mPadding + mItemWidth * 2, mPadding + mItemWidth * 7, mPadding + mItemWidth * 3);

//            mViewGroup1.layout(0,0,mWidthFour,mItemWidth+mPadding*2);
//            mViewGroup2.layout(0,mItemWidth+mPadding*2,mWidthFive,mItemWidth*2+mPadding*4);
//            mViewGroup3.layout(0,mItemWidth*2+mPadding*4,mWidthSeven,mItemWidth*3+mPadding*6);
        } else if (mTotalRow == 2) {//两行
//            mViewGroup1.layout(0,0,mWidthFour,mItemWidth+mPadding*2);
//            mViewGroup2.layout(mWidthFour,0,mWidthFour+mWidthFive,mItemWidth+mPadding*2);
//            mViewGroup3.layout(0,mItemWidth+mPadding*2,mWidthSeven,mItemWidth*2+mPadding*4);
            mTextView.layout(mPadding, mPadding, mPadding + mItemWidth, mPadding + mItemWidth);
            mButton1.layout(mPadding + mItemWidth, mPadding, mPadding + mItemWidth * 2, mPadding + mItemWidth);
            mButton2.layout(mPadding + mItemWidth * 2, mPadding, mPadding + mItemWidth * 3, mPadding + mItemWidth);
            mButton3.layout(mPadding + mItemWidth * 3, mPadding, mPadding + mItemWidth * 4, mPadding + mItemWidth);

            mTextView1.layout(mPadding + mItemWidth * 4, mPadding, mPadding + mItemWidth * 5, mPadding + mItemWidth);
            mButton11.layout(mPadding + mItemWidth * 5, mPadding, mPadding + mItemWidth * 6, mPadding + mItemWidth);
            mButton21.layout(mPadding + mItemWidth * 6, mPadding, mPadding + mItemWidth * 7, mPadding + mItemWidth);
            mButton31.layout(mPadding + mItemWidth * 7, mPadding, mPadding + mItemWidth * 8, mPadding + mItemWidth);
            mButton41.layout(mPadding + mItemWidth * 8, mPadding, mPadding + mItemWidth * 9, mPadding + mItemWidth);

            mTextView2.layout(mPadding, mPadding + mItemWidth, mPadding + mItemWidth, mPadding + mItemWidth * 2);
            mButton12.layout(mPadding + mItemWidth, mPadding + mItemWidth, mPadding + mItemWidth * 2, mPadding + mItemWidth * 2);
            mButton22.layout(mPadding + mItemWidth * 2, mPadding + mItemWidth, mPadding + mItemWidth * 3, mPadding + mItemWidth * 2);
            mButton32.layout(mPadding + mItemWidth * 3, mPadding + mItemWidth, mPadding + mItemWidth * 4, mPadding + mItemWidth * 2);
            mButton42.layout(mPadding + mItemWidth * 4, mPadding + mItemWidth, mPadding + mItemWidth * 5, mPadding + mItemWidth * 2);
            mButton52.layout(mPadding + mItemWidth * 5, mPadding + mItemWidth, mPadding + mItemWidth * 6, mPadding + mItemWidth * 2);
            mButton62.layout(mPadding + mItemWidth * 6, mPadding + mItemWidth, mPadding + mItemWidth * 7, mPadding + mItemWidth * 2);

        } else if (mTotalRow == 1) {//一行
//            mViewGroup1.layout(0,0,mWidthFour,mItemWidth+mPadding*2);
//            mViewGroup2.layout(mWidthFour,0,mWidthFour+mWidthFive,mItemWidth+mPadding*2);
//            mViewGroup3.layout(mWidthFour+mWidthFive,0,mWidthFour+mWidthFive+mWidthSeven,mItemWidth+mPadding*2);
            mTextView.layout(mPadding, mPadding, mPadding + mItemWidth, mPadding + mItemWidth);
            mButton1.layout(mPadding + mItemWidth, mPadding, mPadding + mItemWidth * 2, mPadding + mItemWidth);
            mButton2.layout(mPadding + mItemWidth * 2, mPadding, mPadding + mItemWidth * 3, mPadding + mItemWidth);
            mButton3.layout(mPadding + mItemWidth * 3, mPadding, mPadding + mItemWidth * 4, mPadding + mItemWidth);

            mTextView1.layout(mPadding + mItemWidth * 4, mPadding, mPadding + mItemWidth * 5, mPadding + mItemWidth);
            mButton11.layout(mPadding + mItemWidth * 5, mPadding, mPadding + mItemWidth * 6, mPadding + mItemWidth);
            mButton21.layout(mPadding + mItemWidth * 6, mPadding, mPadding + mItemWidth * 7, mPadding + mItemWidth);
            mButton31.layout(mPadding + mItemWidth * 7, mPadding, mPadding + mItemWidth * 8, mPadding + mItemWidth);
            mButton41.layout(mPadding + mItemWidth * 8, mPadding, mPadding + mItemWidth * 9, mPadding + mItemWidth);

            mTextView2.layout(mPadding + mItemWidth * 9, mPadding, mPadding + mItemWidth * 10, mPadding + mItemWidth);
            mButton12.layout(mPadding + mItemWidth * 10, mPadding, mPadding + mItemWidth * 11, mPadding + mItemWidth);
            mButton22.layout(mPadding + mItemWidth * 11, mPadding, mPadding + mItemWidth * 12, mPadding + mItemWidth);
            mButton32.layout(mPadding + mItemWidth * 12, mPadding, mPadding + mItemWidth * 13, mPadding + mItemWidth);
            mButton42.layout(mPadding + mItemWidth * 13, mPadding, mPadding + mItemWidth * 14, mPadding + mItemWidth);
            mButton52.layout(mPadding + mItemWidth * 14, mPadding, mPadding + mItemWidth * 15, mPadding + mItemWidth);
            mButton62.layout(mPadding + mItemWidth * 15, mPadding, mPadding + mItemWidth * 16, mPadding + mItemWidth);

        }
    }

    private void initLayoutParams() {
        LayoutParams layoutParams0 = new LayoutParams(mItemWidth, mItemWidth);
        mTextView.setLayoutParams(layoutParams0);

        LayoutParams layoutParams1 = new LayoutParams(mItemWidth, mItemWidth);
        mTextView1.setLayoutParams(layoutParams1);

        LayoutParams layoutParams2 = new LayoutParams(mItemWidth, mItemWidth);
        mTextView2.setLayoutParams(layoutParams2);

        LayoutParams layoutParams3 = new LayoutParams(mItemWidth, mItemWidth);
        mButton1.setLayoutParams(layoutParams3);

        LayoutParams layoutParams4 = new LayoutParams(mItemWidth, mItemWidth);
        mButton2.setLayoutParams(layoutParams4);

        LayoutParams layoutParams5 = new LayoutParams(mItemWidth, mItemWidth);
        mButton3.setLayoutParams(layoutParams5);

        LayoutParams layoutParams6 = new LayoutParams(mItemWidth, mItemWidth);
        mButton11.setLayoutParams(layoutParams6);

        LayoutParams layoutParams7 = new LayoutParams(mItemWidth, mItemWidth);
        mButton21.setLayoutParams(layoutParams7);

        LayoutParams layoutParams8 = new LayoutParams(mItemWidth, mItemWidth);
        mButton31.setLayoutParams(layoutParams8);

        LayoutParams layoutParams9 = new LayoutParams(mItemWidth, mItemWidth);
        mButton41.setLayoutParams(layoutParams9);

        LayoutParams layoutParams10 = new LayoutParams(mItemWidth, mItemWidth);
        mButton12.setLayoutParams(layoutParams10);

        LayoutParams layoutParams11 = new LayoutParams(mItemWidth, mItemWidth);
        mButton22.setLayoutParams(layoutParams11);

        LayoutParams layoutParams12 = new LayoutParams(mItemWidth, mItemWidth);
        mButton32.setLayoutParams(layoutParams12);

        LayoutParams layoutParams13 = new LayoutParams(mItemWidth, mItemWidth);
        mButton42.setLayoutParams(layoutParams13);

        LayoutParams layoutParams14 = new LayoutParams(mItemWidth, mItemWidth);
        mButton52.setLayoutParams(layoutParams14);

        LayoutParams layoutParams15 = new LayoutParams(mItemWidth, mItemWidth);
        mButton62.setLayoutParams(layoutParams15);
    }

    private IOnesoft3dNav mIOnesoft3dNav;

    public void setIOnesoft3dNav(IOnesoft3dNav IOnesoft3dNav) {
        mIOnesoft3dNav = IOnesoft3dNav;
    }

    /**
     * 设置默认选中按钮
     * @param action
     * @param object
     */
    public void setAction(int action, Object object) {
        if (action == 0) {//表示第一个按钮默认选中, object为boolean类型值
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton1.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.walk_dimmed) : getResources().getDrawable(R.drawable.walk_normal));
            } else {
                mButton1.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.walk_dimmed_e) : getResources().getDrawable(R.drawable.walk_normal_e));
            }
        } else if (action == 1) {//表示第二个按钮默认选中
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton2.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.fly_dimmed) : getResources().getDrawable(R.drawable.fly_normal));
            } else {
                mButton2.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.fly_dimmed_e) : getResources().getDrawable(R.drawable.fly_normal_e));
            }
        } else if (action == 2) {//表示第三个按钮默认选中
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton3.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.examine_dimmed) : getResources().getDrawable(R.drawable.examine_normal));
            } else {
                mButton3.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.examine_dimmed_e) : getResources().getDrawable(R.drawable.examine_normal_e));
            }
        } else if (action == 3) {//表示第四个按钮默认选中
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton11.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.pan_dimmed) : getResources().getDrawable(R.drawable.pan_normal));
            } else {
                mButton11.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.pan_dimmed_e) : getResources().getDrawable(R.drawable.pan_normal_e));
            }
        } else if (action == 4) {//表示第五个按钮默认选中
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton21.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.plan_dimmed) : getResources().getDrawable(R.drawable.plan_normal));
            } else {
                mButton21.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.plan_dimmed_e) : getResources().getDrawable(R.drawable.plan_normal_e));
            }
        } else if (action == 5) {//表示第六个按钮默认选中
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton31.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.roll_dimmed) : getResources().getDrawable(R.drawable.roll_normal));
            } else {
                mButton31.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.roll_dimmed_e) : getResources().getDrawable(R.drawable.roll_normal_e));
            }
        } else if (action == 6) {//表示第七个按钮默认选中
            boolean isSelect = (boolean) object;
            if (LANGUAGE_CHINESE == mLanguage) {
                mButton41.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.turn_dimmed) : getResources().getDrawable(R.drawable.turn_normal));
            } else {
                mButton41.setBackgroundDrawable(isSelect ? getResources().getDrawable(R.drawable.turn_dimmed_e) : getResources().getDrawable(R.drawable.turn_normal_e));
            }
        }
    }

    public interface IOnesoft3dNav {
        void onAction(int action, Object object);
    }
}
