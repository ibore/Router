package me.ibore.router;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public final class RouterConfig {
    private List<String> mSchemes = new ArrayList<>();
    private List<String> mDomains = new ArrayList<>();
    private boolean mDebug;

    public static RouterConfig get() {
        return new RouterConfig();
    }

    public RouterConfig schemes(String... schemes) {
        mSchemes.clear();
        if (schemes != null) {
            for (String scheme : schemes) {
                if (!mSchemes.contains(scheme)) {
                    mSchemes.add(scheme);
                }
            }
        }
        return this;
    }

    public RouterConfig domains(String... domains) {
        mDomains.clear();
        if (domains != null) {
            for (String domain : domains) {
                if (!mDomains.contains(domain)) {
                    mDomains.add(domain);
                }
            }
        }
        return this;
    }

    public RouterConfig debug(boolean debug) {
        mDebug = debug;
        return this;
    }

    public boolean valid() {
        return mSchemes != null && mSchemes.size() > 0 && mDomains != null && mDomains.size() > 0;
    }

    public List<String> getSchemes() {
        return mSchemes;
    }

    public List<String> getDomains() {
        return mDomains;
    }

    public boolean isDebug() {
        return mDebug;
    }
}
