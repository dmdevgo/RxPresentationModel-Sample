package me.dmdevgo.rxpm_sample.rxpm_navigation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import me.dmdevgo.rxpm_sample.pm.PmMessage;
import me.dmdevgo.rxpm_sample.pm.PmMessageReceiver;
import me.dmdevgo.rxpm_sample.rxpm.RxPmFragment;

/**
 * @author Dmitriy Gorbunov
 */
public abstract class ContainerActivity extends AppCompatActivity implements PmMessageReceiver {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContainerLayout());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            PmMessage message = getFirstMessage();
            if (message != null) {
                onReceive(message);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!handelBackPressedInFragments()) {
            super.onBackPressed();
        }
    }

    protected void onUpPressed() {
        if (!handleUpPressedInFragments()) {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    private boolean handelBackPressedInFragments() {
        Fragment fragment = getCurrentFragmentInContainer();
        if (fragment instanceof BackHandler) {
            return ((BackHandler) fragment).onBackPressed();
        }
        return false;
    }

    private boolean handleUpPressedInFragments() {
        Fragment fragment = getCurrentFragmentInContainer();
        if (fragment instanceof BackHandler) {
            return ((BackHandler) fragment).onUpPressed();
        }
        return false;
    }

    protected int showFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(getContainerId(), fragment);
        if (addToBackStack) ft.addToBackStack(null);
        if (addToBackStack) fragment.setTargetFragment(getCurrentFragmentInContainer(), 0);
        return ft.commit();
    }

    protected Fragment getCurrentFragmentInContainer() {
        return getSupportFragmentManager().findFragmentById(getContainerId());
    }

    protected void sendMessageToTargetPm(PmMessage message) {
        Fragment targetFragment = getCurrentFragmentInContainer().getTargetFragment();
        if (targetFragment != null && targetFragment instanceof RxPmFragment) {
            ((RxPmFragment) targetFragment)
                    .getPresentationModel().actionMessage().call(message);
        }
    }

    protected void clearFragmentBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate(fm.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onReceive(PmMessage message) {
        if (message == ScreenPm.BACK || message == ScreenPm.UP) {
            super.onBackPressed();
        }
    }

    protected abstract @IdRes int getContainerId();
    protected abstract @LayoutRes int getContainerLayout();
    protected abstract PmMessage getFirstMessage();
}
