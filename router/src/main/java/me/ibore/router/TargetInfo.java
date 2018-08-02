package me.ibore.router;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class TargetInfo {
    static final int TYPE_NONE = 0;
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_FRAGMENT = 2;
    public static final int TYPE_FRAGMENT_V4 = 3;

    String pattern;
    Class<?> target;
    int type;
    int flags;
    //params from REST url.
    Map<String, Object> params;

    public TargetInfo(String pattern, Class<?> target, int type, int flags) {
        this.pattern = pattern;
        this.target = target;
        this.type = type;
        this.flags = flags;
    }

    TargetInfo(TargetInfo info) {
        this.pattern = info.pattern;
        this.target = info.target;
        this.type = info.type;
        this.flags = info.flags;
    }

    @Override
    public String toString() {
        return target.getCanonicalName();
    }
}
