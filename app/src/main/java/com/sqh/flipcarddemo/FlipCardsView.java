package com.sqh.flipcarddemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/26.
 */

public class FlipCardsView extends FrameLayout {

    Context mContext;
    LinearLayout mFlCardBack;
    LinearLayout mFlCardFront;
    FrameLayout mFlContainer;
    TextView tv;
    AnimatorSet mRightOutSet; // 右出动画
    AnimatorSet mLeftInSet; // 左入动画

    public FlipCardsView(Context context,AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
        //加载布局
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_flipcardsview, this);
        mFlContainer = (FrameLayout) findViewById(R.id.main_fl_container);
        mFlCardBack = (LinearLayout) findViewById(R.id.main_fl_card_back);
        mFlCardFront = (LinearLayout) findViewById(R.id.main_fl_card_front);
        tv = (TextView) findViewById(R.id.tv);
        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离

    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }

    // 设置动画
    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.anim_out);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFlContainer.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFlContainer.setClickable(true);
            }
        });
    }

    /**
     *可以自行设置需要修改的数据属性，外放就行
     */


    //设置反面文字(金币)
    public void setText(String context) {

        tv.setText(context);

    }

    //设置反面背景
    public void setCardBackground(int color) {

        mFlCardBack.setBackgroundColor(color);
    }

    //启动动画
    public void start() {
        mRightOutSet.setTarget(mFlCardFront);
        mLeftInSet.setTarget(mFlCardBack);
        mRightOutSet.start();
        mLeftInSet.start();
    }
}
