package me.dmdevgo.rxpm_sample.pm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * @author Dmitriy Gorbunov
 */
public class PmDelegate<PM extends PresentationModel> {

    private static final String STATE_PM_TAG = "STATE_PM_TAG";

    private final PmStorage pmStorage;
    private final PmView<PM> pmView;
    private String pmTag;
    private PM pm;

    public PmDelegate(@NonNull PmView<PM> pmView) {
        pmStorage = PmStorage.getInstance();
        this.pmView = pmView;
    }

    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle bundle) {
        pmTag = obtainPresentationModelTag(bundle);
        if (pmStorage.contains(pmTag)) {
            pm = (PM) pmStorage.get(pmTag);
        } else {
            pm = pmView.providePresentationModel(bundle);
            pmStorage.put(pmTag, pm);
            pm.onCreate();
        }
    }

    private String obtainPresentationModelTag(@Nullable Bundle bundle) {
        String tag;
        if (bundle != null) {
            tag = bundle.getString(STATE_PM_TAG);
        } else {
            tag = pmView.providePresentationModelTag();
            if (tag == null) {
                tag = pmView.getClass().getName() + "@" + UUID.randomUUID();
            }
        }
        return tag;
    }

    public void onBind() {
        pm.onBind();
        pmView.onBindPresentationModel(pm);
    }

    public void onUnbind() {
        pmView.onUnbindPresentationModel(pm);
        pm.onUnbind();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PM_TAG, pmTag);
    }

    public void onDestroy() {
        pm.onDestroy();
        pmStorage.remove(pmView.providePresentationModelTag());
    }

    public PM getPresentationModel() {
        return pm;
    }
}
