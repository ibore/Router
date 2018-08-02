package me.ibore.router;

public interface Interceptor {
    RouterResult intercept(Dispatcher dispatcher);

    interface Dispatcher {
        RouterResult dispatch(Action action);

        Action action();
    }
}
