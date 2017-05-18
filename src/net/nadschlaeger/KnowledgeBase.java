package net.nadschlaeger;

import java.util.List;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public class KnowledgeBase {

    private List<Fact> facts;

    private List<Rule> rules;

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
