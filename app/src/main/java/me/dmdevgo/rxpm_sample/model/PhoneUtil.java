package me.dmdevgo.rxpm_sample.model;

import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitriy Gorbunov
 */
public class PhoneUtil {

    private Map<String, Country> countriesMap = new HashMap<>();
    private ArrayList<Country> countries = new ArrayList<>();

    private PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public PhoneUtil() {

        for (String region : phoneNumberUtil.getSupportedRegions()) {
            Country country = new Country(region, phoneNumberUtil.getCountryCodeForRegion(region));
            countriesMap.put(region, country);
            countries.add(country);
        }

        countries.sort(Country::compareTo);
    }

    public Phonenumber.PhoneNumber parsePhone(String phone) throws NumberParseException {
        return phoneNumberUtil.parse(phone, PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY);
    }

    public String formatPhone(String phone) {
        try {
            Phonenumber.PhoneNumber phoneNumber = parsePhone("+" + phone.replaceAll("\\D", ""));
            return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            return phone;
        }
    }

    public String formatPhoneNumber(Country country, String phoneNumber) {

        phoneNumber = phoneNumber.replaceAll("\\D", "");

        if (country == Country.UNKNOWN) return phoneNumber;

        String formattedPhone = phoneNumber;
        String code = "+" + country.getCountryCallingCode();

        AsYouTypeFormatter asYouTypeFormatter = phoneNumberUtil.getAsYouTypeFormatter(country.getRegion());
        asYouTypeFormatter.clear();

        for (char ch : (code + phoneNumber).toCharArray()) {
            formattedPhone = asYouTypeFormatter.inputDigit(ch);
        }

        return formattedPhone.replace(code, "").trim();

    }

    public boolean isValidPhone(Country country, String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("\\D", "");
        Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber();
        number.setCountryCode(country.getCountryCallingCode());
        try {
            number.setNationalNumber(Long.parseLong(phoneNumber));
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Country> getCountries() {
        return countries;
    }


    public Country getCountryForCountryCode(int code) {
        String regionCode = phoneNumberUtil.getRegionCodeForCountryCode(code);
        if (countriesMap.containsKey(regionCode)) {
            return countriesMap.get(regionCode);
        } else {
            return Country.UNKNOWN;
        }
    }

    public Country getCountryForRegionCode(String region) {
        if (countriesMap.containsKey(region)) {
            return countriesMap.get(region);
        } else {
            return Country.UNKNOWN;
        }
    }
}
