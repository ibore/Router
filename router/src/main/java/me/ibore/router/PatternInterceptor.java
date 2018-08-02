package me.ibore.router;

import me.ibore.router.rules.Rule;

final class PatternInterceptor implements Interceptor {

    private Interceptor mInterceptor;
    private Rule mRule;

    PatternInterceptor(Interceptor interceptor, Rule rule) {
        mInterceptor = interceptor;
        this.mRule = rule;
    }

    @Override
    public RouterResult intercept(Dispatcher dispatcher) {
        final Action action = dispatcher.action();
        if (mRule != null && mRule.verify(action)) {
            return mInterceptor.intercept(dispatcher);
        }
        return dispatcher.dispatch(dispatcher.action());
    }

    @Override
    public String toString() {
        return "Rule: " + mRule.toString() + " Interceptor: " + mInterceptor.toString();
    }
}
