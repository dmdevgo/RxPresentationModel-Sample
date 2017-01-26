package me.dmdevgo.rxpm_sample.ui.auth.phone;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxMenuItem;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import me.dmdevgo.rxpm_sample.R;
import me.dmdevgo.rxpm_sample.ui.base.BaseFragment;
import me.dmdevgo.rxpm_sample.ui.custom.CustomEditText;
import me.dmdevgo.rxpm_sample.ui.custom.LoadingView;
import me.dmdevgo.rxpm_sample.util.UiUtils;
import rx.Observable;

/**
 * @author Dmitriy Gorbunov
 */
public class EnterPhoneFragment extends BaseFragment<EnterPhonePm> {

    @BindView(R.id.country_name) TextView countryName;
    @BindView(R.id.edit_country_code) CustomEditText editCountryCode;
    @BindView(R.id.edit_phone_number) CustomEditText editPhoneNumber;
    @BindView(R.id.loading_view) LoadingView loadingFrame;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_enter_phone;
    }

    @Override
    public EnterPhonePm providePresentationModel(Bundle savedInstanceState) {
        return new EnterPhonePm();
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
    public void onBindPresentationModel(EnterPhonePm pm) {
        super.onBindPresentationModel(pm);

        subscribe(pm.sendingPhoneProgress(), loadingFrame::showLoading);
        subscribe(pm.canSendPhone(), RxMenuItem.enabled(toolbar.getMenu().findItem(R.id.action_done)));
        subscribe(pm.countryName(), RxTextView.text(countryName));
        subscribe(pm.countryCode(), s -> {
            editCountryCode.setTextProgrammatically(s);
            editCountryCode.setSelection(s.length());
        });
        subscribe(pm.phoneNumber(), s -> {
            editPhoneNumber.setTextProgrammatically(s);
            editPhoneNumber.setSelection(s.length());
            if (!s.isEmpty()) editPhoneNumber.requestFocus();
        });

        subscribe(RxView.clicks(countryName), pm.countryClick());

        subscribe(RxTextView.textChanges(editCountryCode)
                        .map(CharSequence::toString)
                        .skip(1),
                pm.countryCodeChange());
        subscribe(RxTextView.textChanges(editPhoneNumber)
                        .map(CharSequence::toString),
                pm.phoneNumberChange());

        subscribe(
                Observable.merge(

                        RxMenuItem.clicks(toolbar.getMenu().findItem(R.id.action_done)),
                        RxTextView.editorActions(editPhoneNumber)
                                .filter(action -> action == EditorInfo.IME_ACTION_DONE)
                        .map(integer -> null)
                ),
                pm.doneAction());

        }
}
