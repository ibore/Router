package me.ibore.router.rules;

import me.ibore.router.Action;

public interface Rule {
    enum Operator {
        IS, STARTS_WITH, ENDS_WITH, IN, CONTAINS, EXISTS
    }

    boolean verify(Action action);
}
