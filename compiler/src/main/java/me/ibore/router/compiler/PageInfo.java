package me.ibore.router.compiler;

import com.squareup.javapoet.ClassName;

class PageInfo {
    String url;
    ClassName target;
    int type;
    int flag;
    String alias;
    boolean main;

    PageInfo(String url, ClassName target, int type, int flag, String alias, boolean main) {
        this.url = url;
        this.target = target;
        this.type = type;
        this.flag = flag;
        this.alias = alias;
        this.main = main;
    }
}
