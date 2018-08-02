package me.ibore.router.rules;

import java.util.Arrays;

public final class Rules {

    public static Element scheme() {
        return new SchemeRule();
    }

    public static Element domain() {
        return new DomainRule();
    }

    public static Element path() {
        return new PathRule();
    }

    public static Element query(String key) {
        return new QueryRule(key);
    }

    public static TargetFlagsRule flags() {
        return new TargetFlagsRule();
    }

    public static NotRule not(Rule rule) {
        return new NotRule(rule);
    }

    public static Rule set(RuleSet.Relation relation, Rule... rules) {
        return new RuleSet(Arrays.asList(rules), relation);
    }
}
