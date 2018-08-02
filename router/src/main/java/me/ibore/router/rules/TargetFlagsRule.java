package me.ibore.router.rules;

import me.ibore.router.Action;

public class TargetFlagsRule implements Rule {

    private int mMask;

    public Rule has(int mask) {
        this.mMask = mask;
        return this;
    }

    @Override
    public boolean verify(Action action) {
        return (action.getTargetFlags() & mMask) > 0;
    }

    @Override
    public String toString() {
        return "(flags HAS " + Integer.toHexString(mMask) + ")";
    }
}
