package me.ibore.router.rules;

import me.ibore.router.Action;

public class NotRule implements Rule {

    private Rule mRule;

    NotRule(Rule rule) {
        mRule = rule;
    }

    @Override
    public boolean verify(Action action) {
        return !mRule.verify(action);
    }
}
