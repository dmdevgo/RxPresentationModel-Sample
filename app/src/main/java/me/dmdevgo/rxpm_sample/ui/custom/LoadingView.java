package me.dmdevgo.rxpm_sample.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import me.dmdevgo.rxpm_sample.R;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * @author Dmitriy Gorbunov
 */
public class LoadingView extends FrameLayout {

    private ProgressBar progressBar;
    private View foregroundView;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initLoadingView();
        showLoading(false);
    }

    private void initLoadingView() {
        foregroundView = new View(getContext());
        foregroundView.setClickable(true);
        foregroundView.setBackgroundResource(R.color.black_translucent_50);
        addView(foregroundView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(getContext()));
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(progressBar, lp);

    }

    public void showLoading(boolean loading) {
        if (loading) {
            bringChildToFront(foregroundView);
            bringChildToFront(progressBar);
            foregroundView.setVisibility(VISIBLE);
            progressBar.setVisibility(VISIBLE);
        } else {
            foregroundView.setVisibility(INVISIBLE);
            progressBar.setVisibility(INVISIBLE);
        }
    }
}
