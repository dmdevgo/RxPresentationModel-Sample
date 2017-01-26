package me.dmdevgo.rxpm_sample.pm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * @author Dmitriy Gorbunov
 */
public abstract class PmFragment<PM extends PresentationModel> extends Fragment implements PmView<PM> {

    private PmSupportFragmentDelegate<PM> pmDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pmDelegate = new PmSupportFragmentDelegate<>(this);
        pmDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        pmDelegate.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        pmDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pmDelegate.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        pmDelegate.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pmDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pmDelegate.onDestroy();
    }

    public PM getPresentationModel() {
        return pmDelegate.getPresentationModel();
    }


    @Override
    public String providePresentationModelTag() {
        return null;
    }
}
