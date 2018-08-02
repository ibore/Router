package me.ibore.router.rules;

import java.util.List;

import me.ibore.router.Action;

public class RuleSet implements Rule {
    public enum Relation {
        AND, OR
    }

    private List<Rule> mRules;
    private Relation mRelation;

    RuleSet(List<Rule> rules, Relation relation) {
        mRules = rules;
        mRelation = relation;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean verify(Action action) {
        if (mRelation == Relation.AND) {
            for (Rule rule : mRules) {
                if (!rule.verify(action)) {
                    return false;
                }
            }
            return true;
        } else if (mRelation == Relation.OR) {
            for (Rule rule : mRules) {
                if (rule.verify(action)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Rule rule : mRules) {
            sb.append(rule.toString()).append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
