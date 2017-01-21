package org.looa.stickyballtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import org.looa.stickyballview.widget.StickyBallView;

public class MainActivity extends Activity implements View.OnClickListener, Animator.AnimatorListener, StickyBallView.OnTranslationListener {

    private StickyBallView ballView;
    private int translationX = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ballView = new StickyBallView(this);
        setContentView(ballView);
        ballView.setOnClickListener(this);
        ballView.setOnTranslationListener(this);
    }


    private long time = 400;
    private AnimatorSet set;
    ObjectAnimator animator, animatorSource, animatorSourceScale;

    @Override
    public void onClick(View v) {
        if (set != null && set.isRunning()) return;
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(ballView, "targetTranslationX", 0, translationX);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(time);
            animatorSource = ObjectAnimator.ofFloat(ballView, "sourceTranslationX", 0, translationX);
            animatorSource.setInterpolator(new OvershootInterpolator(1.3f));
            animatorSource.setStartDelay((long) (time * 0.8f));
            animatorSource.setDuration(time);
            animatorSourceScale = ObjectAnimator.ofFloat(ballView, "sourceRadius", 10, 0);
            animatorSourceScale.setInterpolator(new DecelerateInterpolator());
            animatorSourceScale.setDuration(time + (long) (time * 0.8f));
        }
        set = new AnimatorSet();
        set.play(animator).with(animatorSource).with(animatorSourceScale);
        set.start();
        set.addListener(this);
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        ballView.updateSourceCache();
        ballView.updateTargetCache();
        ballView.setSourceRadius(10);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onSourceTranslation(float dX, float dY) {
        if (Math.abs(dX) >= Math.abs(translationX)) {
            ballView.setSourceRadius(10);
        }
    }

    @Override
    public void onTargetTranslation(float dX, float dY) {

    }
}
