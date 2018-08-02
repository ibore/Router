package me.ibore.router.rules;

import android.net.Uri;

public class DomainRule extends UriRule {
    DomainRule() {
    }

    @Override
    public boolean verify(Uri uri) {
        if (uri.isOpaque()) {
            return false;
        }
        return verify(uri.getAuthority());
    }

    @Override
    public String toString() {
        return "(Domain " + super.toString();
    }
}
