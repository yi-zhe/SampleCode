package com.liuyang.code.controllers;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.liuyang.code.R;

/**
 * @author Liuyang 2016/4/2.
 */
public class Animations extends BaseFragment implements View.OnClickListener {
    private LinearLayout vView;
    private View vTranslateAnimationXml;
    private View vTranslateAnimationCode;
    private View vScaleAnimationXml;
    private View vScaleAnimationCode;
    private View vRotateAnimationXml;
    private View vRotateAnimationCode;
    private View vAlphaAnimationXml;
    private View vAlphaAnimationCode;
    private View vPropertyAnimationXml;
    private View vPropertyAnimationCode;
    private View vValueAnimationCode;
    private View vFrameAnimation;

    @Override
    protected int layoutId() {
        return R.layout.animations;
    }

    @Override
    protected void init() {
        // code for LayoutAnimation
        vView = find(R.id.animations);
        Animation animation = AnimationUtils.loadAnimation(host, android.R.anim.slide_in_left);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        vView.setLayoutAnimation(controller);


        vTranslateAnimationXml = find(R.id.translate_animation_xml);
        vTranslateAnimationXml.setOnClickListener(this);
        vTranslateAnimationCode = find(R.id.translate_animation_code);
        vTranslateAnimationCode.setOnClickListener(this);
        vScaleAnimationXml = find(R.id.scale_animation_xml);
        vScaleAnimationXml.setOnClickListener(this);
        vScaleAnimationCode = find(R.id.scale_animation_code);
        vScaleAnimationCode.setOnClickListener(this);
        vRotateAnimationXml = find(R.id.rotate_animation_xml);
        vRotateAnimationXml.setOnClickListener(this);
        vRotateAnimationCode = find(R.id.rotate_animation_code);
        vRotateAnimationCode.setOnClickListener(this);
        vAlphaAnimationXml = find(R.id.alpha_animation_xml);
        vAlphaAnimationXml.setOnClickListener(this);
        vAlphaAnimationCode = find(R.id.alpha_animation_code);
        vAlphaAnimationCode.setOnClickListener(this);

        vPropertyAnimationXml = find(R.id.property_animation_xml);
        vPropertyAnimationXml.setOnClickListener(this);
        vPropertyAnimationCode = find(R.id.property_animation_code);
        vPropertyAnimationCode.setOnClickListener(this);
        vValueAnimationCode = find(R.id.value_animation_code);
        vValueAnimationCode.setOnClickListener(this);

        vFrameAnimation = find(R.id.frame_animation_code);
        vFrameAnimation.setOnClickListener(this);
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate_animation_xml:
                vTranslateAnimationXml.startAnimation(AnimationUtils.loadAnimation(host, R.anim.translate_animation));
                break;
            case R.id.translate_animation_code:
                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 30,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 30);
                translateAnimation.setDuration(1000);
                translateAnimation.setFillAfter(true);
                vTranslateAnimationCode.startAnimation(translateAnimation);
                break;
            case R.id.scale_animation_xml:
                vScaleAnimationXml.startAnimation(AnimationUtils.loadAnimation(host, R.anim.scale_animation));
                break;
            case R.id.scale_animation_code:
                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2);
                scaleAnimation.setDuration(1000);
                vScaleAnimationCode.startAnimation(scaleAnimation);
                break;
            case R.id.rotate_animation_xml:
                vRotateAnimationXml.startAnimation(AnimationUtils.loadAnimation(host, R.anim.rotate_animation));
                break;
            case R.id.rotate_animation_code:
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
                rotateAnimation.setDuration(1000);
                vRotateAnimationCode.startAnimation(rotateAnimation);
                break;
            case R.id.alpha_animation_xml:
                vAlphaAnimationXml.startAnimation(AnimationUtils.loadAnimation(host, R.anim.alpha_animation));
                break;
            case R.id.alpha_animation_code:
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.5f);
                alphaAnimation.setDuration(1000);
                vAlphaAnimationCode.startAnimation(alphaAnimation);
                break;
            case R.id.property_animation_xml:
                AnimatorSet setXml = (AnimatorSet) AnimatorInflater.loadAnimator(host, R.animator.property_animation);
                setXml.setTarget(vPropertyAnimationXml);
                setXml.start();
                break;
            case R.id.property_animation_code:
                AnimatorSet setCode = new AnimatorSet();
                setCode.playSequentially(ObjectAnimator.ofFloat(vPropertyAnimationCode, "alpha", 1, 0.5f),
                        ObjectAnimator.ofFloat(vPropertyAnimationCode, "alpha", 0.5f, 1));
                setCode.setDuration(2000).start();
                break;
            case R.id.value_animation_code:
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0.5f);
                valueAnimator.addUpdateListener(animation -> vValueAnimationCode.setAlpha((float) animation.getAnimatedValue()));
                valueAnimator.setDuration(2000);
                valueAnimator.start();
                break;
            case R.id.frame_animation_code:
                ((AnimationDrawable) vFrameAnimation.getBackground()).start();
                break;
            default:
                break;
        }
    }
}
