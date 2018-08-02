package me.ibore.router;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.ibore.router.rules.Rule;

@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public final class XRouter {
    private static final String TAG = XRouter.class.getSimpleName();
    private static final String PACKAGE = "me.ibore.router";
    private static final String ROUTER_CLASS_NAME = PACKAGE + ".Router";
    private static final String GENERATE_METHOD_NAME = "generate";
    public static final String KEY_ORIGIN_URL = "router_origin_url";
    public static final String KEY_PATTERN = "router_pattern";

    private List<String> mSchemes;
    private List<String> mDomains;
    private List<Interceptor> mInterceptors = new ArrayList<>();
    private SparseArray<Navigator> mNavigators = new SparseArray<>();

    private ActionParser mActionParser;
    private TargetAssembler mTargetAssembler;
    private CleanUpInterceptor mCleanUpInterceptor;

    private static XRouter sInstance;
    static boolean sDebug;

    private XRouter(RouterConfig config) {
        this.registerNavigator(TargetInfo.TYPE_ACTIVITY, new ActivityNavigator());
        this.mSchemes = config.getSchemes();
        this.mDomains = config.getDomains();

        try {
            Class<?> routerClass = Class.forName(ROUTER_CLASS_NAME);
            Method generateMethod = routerClass.getMethod(GENERATE_METHOD_NAME);
            generateMethod.invoke(routerClass);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new IllegalStateException("Generate route table failed.");
        }

    }

    public List<String> getDomains() {
        return mDomains;
    }

    public List<String> getSchemes() {
        return mSchemes;
    }

    public static XRouter get() {
        if (sInstance == null) {
            throw new IllegalStateException("Router has not been initialed.");
        }
        return sInstance;
    }

    public static synchronized XRouter init(RouterConfig config) {
        sDebug = config.isDebug();
        if (sInstance != null) {
            throw new IllegalStateException("Router has already initialed.");
        }
        if (!config.valid()) {
            throw new IllegalArgumentException("Config object not verify");
        }
        if (sInstance == null) {
            sInstance = new XRouter(config);
        }
        Logger.v("Router has been initialized successfully.");
        return sInstance;
    }

    public static String dump() {
        return RouteTable.dump();
    }

    /**
     * Create a rabbit who has ability to navigate through your pages.
     *
     * @param from Whether an Activity or a Fragment instance.
     * @return Rabbit instance.
     */
    public static Builder from(Object from) {
        if (get() == null) {
            throw new IllegalStateException("Router has not been initialized properly");
        }
        if (!(from instanceof Activity) && !(from instanceof Fragment || from instanceof android.app.Fragment) && !(from instanceof Context)) {
            throw new IllegalArgumentException("From object must be whether an Context or a Fragment instance.");
        }
        return new Builder(from);
    }

    /**
     * Add an interceptor used for this navigation. This is useful when you want to check whether a
     * uri matches a specific page using method.
     *
     * @param interceptor Interceptor instance.
     * @return Router instance.
     */
    public XRouter addInterceptor(Interceptor interceptor) {
        if (mInterceptors == null) {
            mInterceptors = new ArrayList<>();
        }
        mInterceptors.add(interceptor);
        return this;
    }

    public XRouter registerNavigator(int type, Navigator navigator) {
        mNavigators.put(type, navigator);
        return this;
    }

    public XRouter registerFallbackNavigator(Navigator navigator) {
        mNavigators.put(TargetInfo.TYPE_NONE, navigator);
        return this;
    }

    public XRouter addInterceptor(Interceptor interceptor, Rule rule) {
        mInterceptors.add(new PatternInterceptor(interceptor, rule));
        return this;
    }

    private ActionParser getActionParser() {
        if (mActionParser == null) {
            mActionParser = new ActionParser();
        }
        return mActionParser;
    }

    public TargetAssembler getTargetAssembler() {
        if (mTargetAssembler == null) {
            mTargetAssembler = new TargetAssembler();
        }
        return mTargetAssembler;
    }

    public CleanUpInterceptor getCleanUpInterceptor() {
        if (mCleanUpInterceptor == null) {
            mCleanUpInterceptor = new CleanUpInterceptor();
        }
        return mCleanUpInterceptor;
    }

    public NavigatorInterceptor getNavigatorInterceptor() {
        return new NavigatorInterceptor(mNavigators);
    }

    RouterResult dispatch(Navigation navigation) {
        Logger.i("[!] Dispatching...");
        final Action action = navigation.action();

        // interceptors
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(getActionParser());

        if (!action.isIgnoreInterceptors()) {
            // custom interceptors
            interceptors.addAll(mInterceptors);

            // mAction specific interceptors
            if (action.getInterceptors() != null) {
                interceptors.addAll(action.getInterceptors());
            }
        }

        interceptors.add(getTargetAssembler());
        interceptors.add(getCleanUpInterceptor());
        interceptors.add(getNavigatorInterceptor());

        RealDispatcher dispatcher = new RealDispatcher(action, interceptors, 0);

        RouterResult result = dispatcher.dispatch(action);
        if (result == null) {
            return RouterResult.notFinished();
        }
        Logger.i("[!] Result: " + result);
        return result;
    }

    /**
     * Used to create Navigation instance.
     */
    public static class Builder {
        private Action mAction;

        Builder(Object from) {
            mAction = new Action();
            mAction.setFrom(from);
        }

        public Navigation to(String url) {
            mAction.setOriginUrl(url);
            return new NavigationImpl(XRouter.get(), mAction);
        }
    }
}
