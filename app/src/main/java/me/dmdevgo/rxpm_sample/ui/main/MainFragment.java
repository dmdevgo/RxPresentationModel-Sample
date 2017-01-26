package me.dmdevgo.rxpm_sample.ui.main;

import android.os.Bundle;

import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.ui.base.BaseFragment;

/**
 * @author Dmitriy Gorbunov
 */

public class MainFragment extends BaseFragment<MainPm> {

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public MainPm providePresentationModel(Bundle bundle) {
        return new MainPm();
    }
}
