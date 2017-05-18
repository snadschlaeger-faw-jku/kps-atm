package net.nadschlaeger;

import java.util.List;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public class Rule {

    private String name;

    private RuleOperator operator = RuleOperator.AND;

    private List<Clause> conditions;

    private List<Clause> consequences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RuleOperator getOperator() {
        return operator;
    }

    public void setOperator(RuleOperator operator) {
        this.operator = operator;
    }

    public List<Clause> getConditions() {
        return conditions;
    }

    public void setConditions(List<Clause> conditions) {
        this.conditions = conditions;
    }

    public List<Clause> getConsequences() {
        return consequences;
    }

    public void setConsequences(List<Clause> consequences) {
        this.consequences = consequences;
    }

    @Override
    public String toString() {
        return name;
    }
}
