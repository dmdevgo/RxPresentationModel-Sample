package me.dmdevgo.rxpm_sample.pm;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Dmitriy Gorbunov
 */
public class PmSupportFragmentDelegate<PM extends PresentationModel> {

    private final PmDelegate<PM> pmPmDelegate;
    private final Fragment fragment;
    private boolean wasInstanceStateSaved;
    private boolean attached;

    @SuppressWarnings("unchecked")
    public PmSupportFragmentDelegate(Fragment fragment) {
        this.fragment = fragment;
        pmPmDelegate = new PmDelegate<>((PmView<PM>) fragment);
    }

    public void onCreate(Bundle bundle) {
        pmPmDelegate.onCreate(bundle);
    }

    public void onStart() {
        // reset if we back to screen after save and stop
        wasInstanceStateSaved = false;
    }

    public void onResume() {
        // reset if we resumed after save when pause
        wasInstanceStateSaved = false;
        tryBind();
    }

    public void onPause() {
        tryUnbind();
    }

    public void onStop() {

    }

    public void onDestroy() {

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (wasInstanceStateSaved) {
            wasInstanceStateSaved = false;
            return;
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        boolean anyParentIsRemoving = false;
        Fragment parent = fragment.getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (fragment.isRemoving() || anyParentIsRemoving || fragment.getActivity().isFinishing()) {
            pmPmDelegate.onDestroy();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        pmPmDelegate.onSaveInstanceState(outState);
        wasInstanceStateSaved = true;
        tryUnbind();
    }

    private void tryBind() {
        if (!attached) {
            pmPmDelegate.onBind();
            attached = true;
        }
    }

    private void tryUnbind() {
        if (attached) {
            pmPmDelegate.onUnbind();
            attached = false;
        }
    }

    public PM getPresentationModel() {
        return pmPmDelegate.getPresentationModel();
    }
}
