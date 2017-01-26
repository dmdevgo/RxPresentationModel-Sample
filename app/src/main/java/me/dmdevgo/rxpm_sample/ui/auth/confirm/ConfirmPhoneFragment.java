package me.dmdevgo.rxpm_sample.ui.auth.confirm;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxMenuItem;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.api.errors.WrongSmsCodeError;
import me.dmdevgo.rxpm_sample.ui.base.BaseFragment;
import me.dmdevgo.rxpm_sample.ui.base.MessageDialog;
import me.dmdevgo.rxpm_sample.ui.custom.CustomEditText;
import me.dmdevgo.rxpm_sample.ui.custom.LoadingView;
import me.dmdevgo.rxpm_sample.util.UiUtils;
import rx.Observable;

/**
 * @author Dmitriy Gorbunov
 */

public class ConfirmPhoneFragment extends BaseFragment<ConfirmPhonePm> {

    private static final String ARG_PHONE = "arg_phone_number";
    private static final String WRONG_SMS_CODE_DIALOG_TAG = "wrong_sms_code_dialog_tag";

    public static ConfirmPhoneFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(ARG_PHONE, phone);
        ConfirmPhoneFragment fragment = new ConfirmPhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.confirm_phone_text) TextView confirmPhoneText;
    @BindView(R.id.edit_sms_code) CustomEditText editSmsCode;
    @BindView(R.id.loading_view) LoadingView loadingView;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_confirm_phone;
    }

    public String getPhone() {
        return getArguments().getString(ARG_PHONE);
    }

    @Override
    public ConfirmPhonePm providePresentationModel(Bundle bundle) {
        return new ConfirmPhonePm(getPhone());
    }

    @Override
    protected void onInitToolbar(Toolbar toolbar) {
        super.onInitToolbar(toolbar);
        toolbar.inflateMenu(R.menu.done);
        toolbar.getMenu().findItem(R.id.action_done)
                .setIcon(UiUtils.getTintListDrawable(getContext(), R.drawable.ic_done_white_24dp, R.color.ic_light));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBindPresentationModel(ConfirmPhonePm pm) {
        super.onBindPresentationModel(pm);

        subscribe(pm.getFormattedPhone(),
                formattedPhone -> confirmPhoneText.setText(getString(R.string.confirm_phone_screen_text, formattedPhone)));
        subscribe(RxTextView.textChanges(editSmsCode).map(CharSequence::toString),
                pm.smsCodeChange());
        subscribe(pm.confirmPhoneProgress(), loadingView::showLoading);
        subscribe(pm.canSend(), RxMenuItem.enabled(toolbar.getMenu().findItem(R.id.action_done)));

        subscribe(
                Observable.merge(
                        RxMenuItem.clicks(toolbar.getMenu().findItem(R.id.action_done)),
                        RxTextView.editorActions(editSmsCode)
                                .filter(action -> action == EditorInfo.IME_ACTION_DONE)
                                .map(integer -> null)
                ),
                pm.doneAction());

    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof WrongSmsCodeError) {
            MessageDialog md = MessageDialog.newInstance(
                    getString(R.string.confirm_phone_wrong_sms_code_title), throwable.getMessage());
            md.show(getChildFragmentManager(), WRONG_SMS_CODE_DIALOG_TAG);
        } else {
            super.onError(throwable);
        }
    }
}
