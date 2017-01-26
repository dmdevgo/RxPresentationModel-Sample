package me.dmdevgo.rxpm_sample.model;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * @author Dmitriy Gorbunov
 */

public class Country implements Comparable<Country> {

    public static final String UNKNOWN_REGION = "ZZ";
    public static final int INVALID_COUNTRY_CODE = 0;

    public static final Country UNKNOWN = new Country(UNKNOWN_REGION, INVALID_COUNTRY_CODE) {
        @Override
        public String getDisplayName() {
            return "Unknown country";
        }
    };

    private final int countryCode;
    private final Locale locale;
    private final String region;

    public Country(String region, int code) {
        this.region = region;
        countryCode = code;
        locale = new Locale("en", region);
    }

    public int getCountryCallingCode() {
        return countryCode;
    }

    public String getDisplayName() {
        return locale.getDisplayCountry(Locale.ENGLISH);
    }

    public String getRegion() {
        return region;
    }

    @Override
    public int compareTo(@NonNull Country country) {
        return getDisplayName().compareTo(country.getDisplayName());
    }
}
