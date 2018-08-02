package me.ibore.router;

import android.util.SparseArray;

class NavigatorInterceptor implements InternalInterceptor {

    private SparseArray<Navigator> mNavigators;

    NavigatorInterceptor(SparseArray<Navigator> navigators) {
        mNavigators = navigators;
    }

    @Override
    public RouterResult intercept(Dispatcher dispatcher) {
        Logger.i("[!] Navigating...");
        if (mNavigators == null) {
            throw new NullPointerException("No verify navigator");
        }
        Action action = dispatcher.action();

        // Just return the target when "justObtain" was specific.
        if (action.isJustObtain() && action.getTarget() != null) {
            return RouterResult.success(action.getTarget());
        }

        boolean notFound = false;
        if (action.getTarget() == null || action.getTargetType() == TargetInfo.TYPE_NONE) {
            notFound = true;
            if (action.isIgnoreFallback()) {
                return RouterResult.notFound(action.getOriginUrl());
            }
        }

        // Normal navigation or fallback navigation all handled here.
        Navigator navigator = mNavigators.get(action.getTargetType());
        if (navigator == null) {
            if (notFound) {
                // need to be handled by fallback.
                // fallback handler isn't set.
                return RouterResult.notFound(action.getOriginUrl());
            } else {
                return RouterResult.error("Navigator not found for type " + action.getTargetType());
            }
        }
        return navigator.perform(action);
    }
}
