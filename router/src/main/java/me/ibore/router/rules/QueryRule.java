package me.ibore.router.rules;

import android.net.Uri;

public class QueryRule extends UriRule {

    private String mKey;

    QueryRule(String key) {
        mKey = key;
    }

    @Override
    public boolean verify(Uri uri) {
        if (uri.isOpaque()) {
            return false;
        }
        String q = uri.getQueryParameter(mKey);
        return super.verify(q);
    }

    @Override
    public String toString() {
        return "(q:" + mKey + " " + super.toString();
    }
}
