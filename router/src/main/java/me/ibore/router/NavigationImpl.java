package me.ibore.router;

import android.support.annotation.NonNull;

public class NavigationImpl extends AbstractNavigation {
    protected XRouter router;

    NavigationImpl(XRouter router, Action action) {
        super(action);
        this.router = router;
        Logger.d("Navigation created. FROM: " + action.getFrom().toString() + " URL: " + action.getOriginUrl());
    }

    @NonNull
    @Override
    public RouterResult start() {
        return router.dispatch(this);
    }

    @NonNull
    @Override
    public RouterResult startForResult(int requestCode) {
        forResult(requestCode);
        return start();
    }

    @NonNull
    @Override
    public RouterResult obtain() {
        justObtain();
        return router.dispatch(this);
    }
}
