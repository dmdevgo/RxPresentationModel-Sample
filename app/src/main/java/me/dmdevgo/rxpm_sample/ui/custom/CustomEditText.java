package me.dmdevgo.rxpm_sample.ui.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * @author Dmitriy Gorbunov
 */
public class CustomEditText extends AppCompatEditText {

    private ArrayList<TextWatcher> mListeners = new ArrayList<>();
    private boolean blockNotification = true;

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (blockNotification) {
                    for (TextWatcher textWatcher : mListeners) {
                        textWatcher.beforeTextChanged(s, start, count, after);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (blockNotification) {
                    for (TextWatcher textWatcher : mListeners) {
                        textWatcher.onTextChanged(s, start, before, count);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (blockNotification) {
                    for (TextWatcher textWatcher : mListeners) {
                        textWatcher.afterTextChanged(s);
                    }
                }
            }
        });
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        mListeners.add(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        mListeners.remove(watcher);
    }

    public void setTextProgrammatically(CharSequence text) {
        blockNotification = false;
        setText(text);
        blockNotification = true;
    }

}
