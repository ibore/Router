package me.ibore.router;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class CleanUpInterceptor implements InternalInterceptor {
    @Override
    public RouterResult intercept(Dispatcher dispatcher) {
        Action action = dispatcher.action();
        RouterResult result = dispatcher.dispatch(action);
        if (result.isSuccess()) {
            if (action.getTargetType() == TargetInfo.TYPE_ACTIVITY) {

                Activity act = null;
                if (action.getFrom() instanceof Activity) {
                    act = (Activity) action.getFrom();
                } else if (action.getFrom() instanceof Fragment) {
                    act = ((Fragment) action.getFrom()).getActivity();
                } else if (action.getFrom() instanceof android.app.Fragment) {
                    act = ((android.app.Fragment) action.getFrom()).getActivity();
                }

                if (action.isRedirect()) {
                    if (act != null) {
                        act.finish();
                    }
                }

                int[] animations = action.getTransitionAnimations();

                if (act != null
                        && animations != null
                        && animations.length == 2) {
                    act.overridePendingTransition(animations[0], animations[1]);
                }
            }
        }
        return result;
    }
}
