package me.dmdevgo.rxpm_sample.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @author Dmitriy Gorbunov
 */

public class MessageDialog extends DialogFragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_MESSAGE = "arg_message";

    public static MessageDialog newInstance(String title, String message) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        MessageDialog fragment = new MessageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public String getTitle() {
        return getArguments().getString(ARG_TITLE);
    }

    public String getMessage() {
        return getArguments().getString(ARG_MESSAGE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(getTitle())
                .setMessage(getMessage())
                .setPositiveButton("ok", null)
                .create();
    }
}
