package me.dmdevgo.rxpm_sample.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * @author Dmitriy Gorbunov
 */
public class UiUtils {

    public static Drawable tintListDrawable(Context context, Drawable drawable, @ColorRes int colorStateList) {
        drawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(context, colorStateList));
        return drawable;
    }

    public static Drawable getTintListDrawable(Context context, @DrawableRes int resource, @ColorRes int color) {
        return tintListDrawable(context, ContextCompat.getDrawable(context, resource), color);
    }

}
