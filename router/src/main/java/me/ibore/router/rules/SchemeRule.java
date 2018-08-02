package me.ibore.router.rules;

import android.net.Uri;

public class SchemeRule extends UriRule {

    SchemeRule() {
    }

    @Override
    public boolean verify(Uri uri) {
        return verify(uri.getScheme());
    }

    @Override
    public String toString() {
        return "(Scheme " + super.toString();
    }
}
