package net.nadschlaeger;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public abstract class InferenceEngine implements FactEventHandler {

    private WorkingMemory wm;

    protected static KnowledgeBase kb;

    public InferenceEngine() {
        // Nothing to do here!
    }

    public InferenceEngine(WorkingMemory wm, KnowledgeBase kb) {
        this.wm = wm;
        this.wm.addFactEventHandler(this);
        this.kb = kb;
    }

    public void trigger(Fact fact) {
        wm.assertFact(fact);
    }

    public static KnowledgeBase getKb() {
        return kb;
    }

    public void setKb(KnowledgeBase kb) {
        this.kb = kb;
    }

    public WorkingMemory getWm() {
        return wm;
    }

    public void setWm(WorkingMemory wm) {
        this.wm = wm;
    }

    @Override
    public void factAdded()  {
        checkRules();
    }

    public abstract void checkRules();
}
