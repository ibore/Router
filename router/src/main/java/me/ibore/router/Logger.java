package me.ibore.router;

import android.util.Log;

class Logger {
    private static final String TAG = "Rabbits";

    static void d(String message) {
        if (!XRouter.sDebug) {
            return;
        }
        Log.d(TAG, message);
    }

    static void v(String message) {
        if (!XRouter.sDebug) {
            return;
        }
        Log.v(TAG, message);
    }

    static void i(String message) {
        if (!XRouter.sDebug) {
            return;
        }
        Log.i(TAG, message);
    }

    static void w(String message) {
        if (!XRouter.sDebug) {
            return;
        }
        Log.w(TAG, message);
    }

    static void e(String message) {
        if (!XRouter.sDebug) {
            return;
        }
        Log.e(TAG, message);
    }
}
