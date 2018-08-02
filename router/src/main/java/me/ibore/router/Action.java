package me.ibore.router;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"WeakerAccess", "SameParameterValue", "unused"})
public final class Action {

    /* *****************************
     *   Set before dispatch
     *******************************/

    private Object mFrom;
    /**
     * Origin url witch the caller use.
     */
    private String mOriginUrl;
    /**
     * extras
     */
    private Bundle mExtras;
    /**
     * intent flags
     */
    private int mIntentFlags;
    /**
     * transition animations
     */
    private int[] mTransitionAnimations;
    /**
     * Whether ignore custom interceptors.
     * If false, all interceptors added to Rabbit and Navigation would not be
     * executed.
     */
    private boolean mIgnoreInterceptors;
    /**
     * Whether ignore fallback navigator.
     * If false and no page matched, fallback navigator would not be executed and a result with
     * status {@link RouterResult#STATUS_NOT_FOUND} will be returned.
     */
    private boolean mIgnoreFallback;
    /**
     * Indicate just obtain the target or really perform a navigation. The target might be an
     * Intent instance for Activity or Corresponding instance for Fragment.
     */
    private boolean mJustObtain;
    /**
     * If set, {@link android.app.Activity#startActivityForResult} will be invoked rather than
     * {@link android.content.Context#startActivity}.
     */
    private int mRequestCode;
    /**
     * finish current parent after navigation
     */
    private boolean mRedirect;

    /**
     * Interceptors set by caller.
     */
    private List<Interceptor> mInterceptors;

    /* *****************************
     *   Generate during dispatch
     *******************************/

    /**
     * Uri used by Rabbit to find the target page.
     */
    private Uri mUri;
    /**
     * The pattern witch matches the uri.
     */
    private String mTargetPattern;
    /**
     * Flags of the target page.
     */
    private int mTargetFlags;
    /**
     * Type of the target page.
     */
    private int mTargetType;
    /**
     * Class of the target page.
     */
    private Class<?> mTargetClass;
    /**
     * Real navigation target, maybe an Intent or a Fragment instance.
     */
    private Object mTarget;

    /**
     * Discard the target page.
     * Invoke this method will lead current navigation to Fallback
     * (If {@link Navigation#ignoreFallback()}) has not been called and can not revert.
     * Probably used when Rabbit used as bridge between web pages and native pages.
     */
    public void discard() {
        setTarget(null);
        setTargetType(TargetInfo.TYPE_NONE);
        setTargetFlags(0);
    }

    /**
     * Used internally when navigate with an {@link Action} instance. This probably happens
     * in {@link Interceptor} or custom {@link Navigator} where redirecting from current mAction is needed.
     *
     * @param action origin mAction
     */
    void setAction(Action action) {
        this.setExtras(action.getExtras());
        this.setIntentFlags(action.getIntentFlags());
        this.setTransitionAnimations(action.getTransitionAnimations());
        this.setIgnoreInterceptors(action.isIgnoreInterceptors());
        this.setIgnoreFallback(action.isIgnoreFallback());
        this.setJustObtain(action.isJustObtain());
        this.setRequestCode(action.getRequestCode());
        this.setRedirect(action.isRedirect());
    }

    /**
     * Create uri from url and param. This will parse all param to String and append
     * them to the uri.
     *
     * @return Uri
     */
    public Uri createUri() {
        Uri uri = Uri.parse(getOriginUrl());
        Uri.Builder builder = uri.buildUpon();
        Bundle extras = getExtras();
        if (extras != null) {
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                if (TextUtils.equals(key, XRouter.KEY_ORIGIN_URL) || TextUtils.equals(key, XRouter.KEY_PATTERN)) {
                    continue;
                }
                Object value = extras.get(key);
                if (value == null) {
                    continue;
                }
                builder.appendQueryParameter(key, URLEncodeUtils.encode(value.toString()));
            }
        }
        uri = builder.build();
        return uri;
    }

    public Object getFrom() {
        return mFrom;
    }

    void setFrom(Object from) {
        mFrom = from;
    }

    @Nullable
    public Object getTarget() {
        return mTarget;
    }

    void setTarget(Object target) {
        mTarget = target;
    }

    public String getOriginUrl() {
        return mOriginUrl;
    }

    void setOriginUrl(String originUrl) {
        mOriginUrl = originUrl;
    }

    public Uri getUri() {
        return mUri;
    }

    void setUri(Uri uri) {
        mUri = uri;
    }

    public boolean isRedirect() {
        return mRedirect;
    }

    public void setRedirect(boolean redirect) {
        mRedirect = redirect;
    }

    public Bundle getExtras() {
        return mExtras;
    }

    void setExtras(Bundle extras) {
        mExtras = extras;
    }

    public int getIntentFlags() {
        return mIntentFlags;
    }

    public void setIntentFlags(int intentFlags) {
        mIntentFlags = intentFlags;
    }

    public int[] getTransitionAnimations() {
        return mTransitionAnimations;
    }

    public void setTransitionAnimations(int[] transitionAnimations) {
        mTransitionAnimations = transitionAnimations;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        mRequestCode = requestCode;
    }

    public boolean isIgnoreInterceptors() {
        return mIgnoreInterceptors;
    }

    public void setIgnoreInterceptors(boolean ignoreInterceptors) {
        mIgnoreInterceptors = ignoreInterceptors;
    }

    public boolean isIgnoreFallback() {
        return mIgnoreFallback;
    }

    public void setIgnoreFallback(boolean ignoreFallback) {
        mIgnoreFallback = ignoreFallback;
    }

    public boolean isJustObtain() {
        return mJustObtain;
    }

    public void setJustObtain(boolean justObtain) {
        mJustObtain = justObtain;
    }

    public int getTargetFlags() {
        return mTargetFlags;
    }

    void setTargetFlags(int targetFlags) {
        mTargetFlags = targetFlags;
    }

    public int getTargetType() {
        return mTargetType;
    }

    void setTargetType(int targetType) {
        mTargetType = targetType;
    }

    public Class<?> getTargetClass() {
        return mTargetClass;
    }

    void setTargetClass(Class<?> targetClass) {
        mTargetClass = targetClass;
    }

    public String getTargetPattern() {
        return mTargetPattern;
    }

    public void setTargetPattern(String targetPattern) {
        mTargetPattern = targetPattern;
    }

    List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    void setInterceptors(List<Interceptor> interceptors) {
        mInterceptors = interceptors;
    }

    @Override
    public String toString() {
        return "Action{" +
                "mFrom=" + mFrom +
                ", mOriginUrl='" + mOriginUrl + '\'' +
                ", mExtras=" + mExtras +
                ", mIntentFlags=" + mIntentFlags +
                ", mTransitionAnimations=" + Arrays.toString(mTransitionAnimations) +
                ", mIgnoreInterceptors=" + mIgnoreInterceptors +
                ", mIgnoreFallback=" + mIgnoreFallback +
                ", mJustObtain=" + mJustObtain +
                ", mRequestCode=" + mRequestCode +
                ", mRedirect=" + mRedirect +
                ", mUri=" + mUri +
                ", mTargetPattern='" + mTargetPattern + '\'' +
                ", mTargetFlags=" + mTargetFlags +
                ", mTargetType=" + mTargetType +
                ", mTargetClass=" + mTargetClass +
                ", mTarget=" + mTarget +
                '}';
    }
}
