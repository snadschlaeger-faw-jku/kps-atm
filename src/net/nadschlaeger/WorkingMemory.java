package net.nadschlaeger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public class WorkingMemory {

    private List<Fact> globalFacts;

    private List<Fact> inferredFacts = new ArrayList<>();

    private KnowledgeBase kb;

    private List<FactEventHandler> factHandlers = new ArrayList<>();

    public WorkingMemory(KnowledgeBase kb) {
        this.globalFacts = kb.getFacts();
        this.kb = kb;
    }

    public List<Fact> clear() {
        List<Fact> ifCopy = new ArrayList<>();
        ifCopy.addAll(this.inferredFacts);
        this.inferredFacts.clear();
        return ifCopy;
    }

    public void assertFact(Fact fact) {
        this.inferredFacts.add(fact);
        notifyFactEventHandlers();
    }

    private void notifyFactEventHandlers() {
        for (FactEventHandler handler : factHandlers) {
            handler.factAdded();
        }
    }

    public List<Fact> getGlobalFacts() {
        return globalFacts;
    }

    public void setGlobalFacts(List<Fact> globalFacts) {
        this.globalFacts = globalFacts;
    }

    public KnowledgeBase getKb() {
        return kb;
    }

    public void setKb(KnowledgeBase kb) {
        this.kb = kb;
    }

    public List<Fact> getInferredFacts() {
        return inferredFacts;
    }

    public void setInferredFacts(List<Fact> inferredFacts) {
        this.inferredFacts = inferredFacts;
    }

    public void addFactEventHandler(FactEventHandler handler) {
        factHandlers.add(handler);
    }
}
