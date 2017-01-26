package me.dmdevgo.rxpm_sample.pm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Gorbunov
 */
public class PmStorage {

    private static final PmStorage INSTANCE = new PmStorage();
    private Map<String, PresentationModel> map = new HashMap<>();

    public static PmStorage getInstance() {
        return INSTANCE;
    }

    private PmStorage() {}

    PresentationModel get(String tag) {
        return map.get(tag);
    }

    boolean contains(String tag) {
        return map.containsKey(tag);
    }

    void remove(String tag) {
        map.remove(tag);
    }

    public void put(String tag, PresentationModel pm) {
        map.put(tag, pm);
    }
}
