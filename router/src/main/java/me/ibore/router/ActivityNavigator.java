package me.ibore.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ActivityNavigator implements Navigator {
    @Override
    public RouterResult perform(Action action) {
        Object target = action.getTarget();
        if (target == null) {
            return RouterResult.notFound(action.getOriginUrl());
        }
        if (action.isJustObtain()) {
            return RouterResult.success(target);
        }

        Object from = action.getFrom();
        if (from == null) {
            return RouterResult.error("The \"from\" can not be null");
        }
        final int requestCode = action.getRequestCode();
        final Intent intent = (Intent) action.getTarget();

        Activity activity = null;
        
        if (requestCode > 0) {
            if (from instanceof Activity) {
                ((Activity) from).startActivityForResult(intent, requestCode);
            } else if (from instanceof Fragment) {
                ((Fragment) from).startActivityForResult(intent, requestCode);
            } else if (from instanceof android.app.Fragment) {
                ((android.app.Fragment) from).startActivityForResult(intent, requestCode);
            } else if (from instanceof Context) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Context) from).startActivity(intent);
            } else {
                return RouterResult.error("Invalid from.");
            }
        } else {
            if (from instanceof Activity) {
                activity = (Activity) from;
            } else if (from instanceof Fragment) {
                activity = ((Fragment) from).getActivity();
            } else if (from instanceof android.app.Fragment) {
                activity = ((android.app.Fragment) from).getActivity();
            }
            if (activity != null) {
                activity.startActivity((Intent) action.getTarget());
            } else if (from instanceof Context) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Context) from).startActivity(intent);
            } else {
                return RouterResult.error("Invalid from.");
            }
        }

        return RouterResult.success();
    }
}
