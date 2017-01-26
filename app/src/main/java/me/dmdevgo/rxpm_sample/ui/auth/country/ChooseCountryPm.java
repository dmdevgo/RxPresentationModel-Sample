package me.dmdevgo.rxpm_sample.ui.auth.country;

import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.PublishRelay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.inject.Inject;

import me.dmdevgo.rxpm_sample.dagger.ComponentHolder;
import me.dmdevgo.rxpm_sample.model.Country;
import me.dmdevgo.rxpm_sample.model.PhoneUtil;
import me.dmdevgo.rxpm_sample.pm.PmMessage;
import me.dmdevgo.rxpm_sample.rxpm_navigation.ScreenPm;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Dmitriy Gorbunov
 */
public class ChooseCountryPm extends ScreenPm {

    @Inject
    PhoneUtil phoneUtil;

    private BehaviorRelay<List<Country>> countriesRelay = BehaviorRelay.create();

    private PublishRelay<String> searchQuery = PublishRelay.create();
    private PublishRelay<Country> countryClick = PublishRelay.create();

    public ChooseCountryPm() {
        ComponentHolder.getInstance().getAppComponent().inject(this);
    }

    public Observable<List<Country>> countries() {
        return countriesRelay.asObservable();
    }

    public Action1<String> searchQuery() {
        return searchQuery;
    }

    public Action1<Country> countryClick() {
        return countryClick;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        countriesRelay.call(phoneUtil.getCountries());

        countryClick.map(ChosenCountryMessage::new)
                .subscribe(messagesFromPm);

        searchQuery
                .debounce(50, TimeUnit.MILLISECONDS)
                .map(String::toLowerCase)
                .map(s -> {
                    Pattern pattern = Pattern.compile(s + ".*");

                    List<Country> result = new ArrayList<>();
                    for (Country country : phoneUtil.getCountries()) {
                        if (pattern.matcher(country.getDisplayName().toLowerCase()).matches()) {
                            result.add(country);
                        }
                    }
                    return result;
                })
                .subscribe(countriesRelay);
    }

    public static final class ChosenCountryMessage extends PmMessage {
        public final Country country;
        public ChosenCountryMessage(Country country) {
            this.country = country;
        }
    }
}
