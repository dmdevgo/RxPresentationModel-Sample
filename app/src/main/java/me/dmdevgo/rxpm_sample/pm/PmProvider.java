package me.dmdevgo.rxpm_sample.pm;

import android.os.Bundle;

/**
 * @author Dmitriy Gorbunov
 */

public interface PmProvider<T extends PresentationModel> {
    T providePresentationModel(Bundle bundle);
    String providePresentationModelTag();
}
