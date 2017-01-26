package me.dmdevgo.rxpm_sample.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.rxpm_navigation.ScreenPm;
import me.dmdevgo.rxpm_sample.rxpm_navigation.ScreenPmFragment;
import timber.log.Timber;

/**
 * @author Dmitriy Gorbunov
 */
public abstract class BaseFragment<PM extends ScreenPm> extends ScreenPmFragment<PM> {

    @Nullable @BindView(R.id.toolbar) protected Toolbar toolbar;
    private Unbinder unbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (toolbar != null) {
            onInitToolbar(toolbar);
        }
    }

    @Override
    public void onBindPresentationModel(PM pm) {
        super.onBindPresentationModel(pm);
        if (toolbar != null) {
            subscribe(RxToolbar.navigationClicks(toolbar), pm.actionUp());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onError(Throwable throwable) {
        Timber.wtf("Error from pm: %s", throwable.getMessage());
    }

    protected void onInitToolbar(Toolbar toolbar) {}

}
