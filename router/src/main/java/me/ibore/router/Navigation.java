package me.ibore.router;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Map;

import me.ibore.router.rules.Rule;

@SuppressWarnings("unused")
public interface Navigation {
    Navigation addIntentFlags(int flags);

    Navigation setIntentFlags(int flags);

    Navigation newTask();

    Navigation clearTop();

    Navigation singleTop();

    Navigation putExtra(@NonNull String key, Object value);

    Navigation putExtras(Bundle bundle);

    Navigation putExtras(Map<String, Object> extras);

    Navigation addInterceptor(Interceptor interceptor);

    Navigation addInterceptor(Interceptor interceptor, Rule rule);

    Navigation justObtain();

    Navigation forResult(int requestCode);

    Navigation redirect();

    Navigation ignoreInterceptors();

    Navigation ignoreFallback();

    Navigation setTransitionAnimations(int[] transitionAnimations);

    Navigation action(Action action);

    @NonNull
    RouterResult start();

    @NonNull
    RouterResult startForResult(int requestCode);

    @NonNull
    RouterResult obtain();

    @NonNull
    Action action();
}
