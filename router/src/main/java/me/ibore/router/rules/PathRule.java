package me.ibore.router.rules;

import android.net.Uri;

@SuppressWarnings("unused")
public class PathRule extends UriRule {
    PathRule() {
    }

    @Override
    public boolean verify(Uri uri) {
        if (uri.isOpaque()) {
            return false;
        }
        return verify(uri.getPath());
    }

    @Override
    public String toString() {
        return "(Path " + super.toString();
    }
}
