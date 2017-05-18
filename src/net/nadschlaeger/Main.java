package net.nadschlaeger;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        KnowledgeBase kb = new KnowledgeBase();
        createFacts(kb);
        createRules(kb);

        WorkingMemory wm = new WorkingMemory(kb);

        InferenceEngine ie = new BruteForceIE(wm, kb);

        System.out.println("Starting IE ...");
        Fact startFact = createCardFact(123);
        ie.trigger(startFact);
        System.out.println("Stopped IE ...");
    }

    private static void createRules(KnowledgeBase kb) {
        Rule rule1 = createCardExistsRule();
        Rule rule2 = createPinValidRule();
        Rule rule3 = createAmountValidRule();

        kb.setRules(Arrays.asList(rule1, rule2, rule3));
    }

    private static Fact createSimpleFact(String factName, Slot... slots) {
        Fact fact = new Fact();
        fact.setName(factName);

        fact.setSlots(Arrays.asList(slots));

        return fact;
    }

    private static Rule createPinValidRule() {
        Rule rule = new Rule();
        rule.setName("check-pin");

        Clause conditionClause = new Clause();
        conditionClause.setExpression(ClauseOperators.take.name());
        conditionClause.setParameter("pin");

        Clause conditionClause2 = new Clause();
        conditionClause2.setExpression(ClauseOperators.exists.name());

        rule.setConditions(Arrays.asList(conditionClause, conditionClause2));

        Clause consequenceClause = new Clause();
        consequenceClause.setExpression(ClauseOperators.assertFact.name());
        consequenceClause.setParameter(createSimpleFact("account",
                new TakeSlot("cardNumber", "pin", null, true),
                new QuestionSlot("amount", "java.lang.Integer", null, true)));
        rule.setConsequences(Arrays.asList(consequenceClause));

        return rule;
    }

    private static Rule createAmountValidRule() {
        Rule rule = new Rule();
        rule.setName("check-amount");

        Clause conditionClause = new Clause();
        conditionClause.setExpression(ClauseOperators.take.name());
        conditionClause.setParameter("account");

        Clause conditionClause2 = new Clause();
        conditionClause2.setExpression(ClauseOperators.exists.name());

        rule.setConditions(Arrays.asList(conditionClause, conditionClause2));

        Clause consequenceClause = new Clause();
        consequenceClause.setExpression(ClauseOperators.print.name());
        consequenceClause.setParameter("Here is your money!");
        rule.setConsequences(Arrays.asList(consequenceClause));

        return rule;
    }

    private static Rule createCardExistsRule() {
        Rule rule = new Rule();
        rule.setName("check-card-rule");

        Clause conditionClause = new Clause();
        conditionClause.setExpression(ClauseOperators.take.name());
        conditionClause.setParameter("credit-card");

        Clause conditionClause2 = new Clause();
        conditionClause2.setExpression(ClauseOperators.exists.name());

        rule.setConditions(Arrays.asList(conditionClause, conditionClause2));

        Clause consequenceClause = new Clause();
        consequenceClause.setExpression(ClauseOperators.assertFact.name());
        consequenceClause.setParameter(createSimpleFact("pin",
                new TakeSlot("cardNumber", "credit-card", null, true),
                new QuestionSlot("pinNumber", "java.lang.Integer", null, true)));
        rule.setConsequences(Arrays.asList(consequenceClause));

        return rule;
    }

    private static void createFacts(KnowledgeBase kb) {
        Fact fact1 = createCardFact(123);
        Fact fact2 = createCardFact(234);
        Fact fact3 = createCardFact(345);

        Fact fact4 = createSimpleFact("pin",
                new Slot("cardNumber", "java.lang.Integer", 123, true),
                new Slot("pinNumber", "java.lang.Integer", 123, true));
        Fact fact5 = createSimpleFact("pin",
                new Slot("cardNumber", "java.lang.Integer", 234, true),
                new Slot("pinNumber", "java.lang.Integer", 234, true));
        Fact fact6 = createSimpleFact("pin",
                new Slot("cardNumber", "java.lang.Integer", 345, true),
                new Slot("pinNumber", "java.lang.Integer", 345, true));

        Fact fact7 = createSimpleFact("account",
                new Slot("cardNumber", "java.lang.Integer", 123, true),
                new Slot("balance", "java.lang.Integer", 100, false));
        Fact fact8 = createSimpleFact("account",
                new Slot("cardNumber", "java.lang.Integer", 234, true),
                new Slot("balance", "java.lang.Integer", 50, false));
        Fact fact9 = createSimpleFact("account",
                new Slot("cardNumber", "java.lang.Integer", 345, true),
                new Slot("balance", "java.lang.Integer", 320, false));

        kb.setFacts(Arrays.asList(fact1, fact2, fact3, fact4, fact5, fact6, fact7, fact8, fact9));
    }

    private static Fact createCardFact(Integer cardNumber) {
        Fact fact = new Fact();
        fact.setName("credit-card");

        Slot cardNumberSlot = new Slot();
        cardNumberSlot.setName("cardNumber");
        cardNumberSlot.setType("java.lang.Integer");
        cardNumberSlot.setValue(cardNumber);
        cardNumberSlot.setMandatory(true);

        fact.setSlots(Arrays.asList(cardNumberSlot));

        return fact;
    }
}
