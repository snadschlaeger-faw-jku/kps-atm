package net.nadschlaeger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snadschlaeger on 28.12.2016.
 */
public class BruteForceIE extends InferenceEngine {

	public BruteForceIE(WorkingMemory wm, KnowledgeBase kb) {
		super(wm, kb);
	}

	@Override
	public void checkRules() {
		List<Rule> rules = getKb().getRules();
		for (Rule rule : rules) {
			if (checkRuleConditions(rule.getOperator(), rule.getConditions())) {
				executeConsequences(rule.getConsequences());
			}
		}
	}

	private void executeConsequences(List<Clause> consequences) {
		List<Fact> factHistory = getWm().clear();
		for (Clause consequence : consequences) {
			if (consequence.getExpression().equals(ClauseOperators.print.name())) {
				System.out.println(consequence.getParameter());
			} else if (consequence.getExpression().equals(ClauseOperators.assertFact.name())) {
				Fact fact = (Fact) consequence.getParameter();
				evaluateSlots(fact, factHistory);
				getWm().assertFact(fact);
			}
		}
	}

	private void evaluateSlots(Fact fact, List<Fact> factHistory) {
		for (Slot slot : fact.getSlots()) {
			if (slot instanceof QuestionSlot) {
				try {
					System.out.println("Please enter " + slot.getName() + ": ");
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					String s = br.readLine();
					Constructor<?> ctor = Class.forName(slot.getType()).getConstructor(String.class);
					slot.setValue(ctor.newInstance(s));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (slot instanceof TakeSlot) {
				Fact takenFact = factHistory.stream().filter(f -> f.getName().equals(slot.getType())).findFirst().orElse(null);
				if (takenFact != null) {
					Slot foundSlot = takenFact.getSlots().stream().filter(s -> s.getName().equals(slot.getName())).findFirst().orElse(null);
					if (foundSlot != null) {
						slot.setValue(foundSlot.getValue());
						slot.setType(foundSlot.getType());
					}
				}
			}
		}
	}

	private boolean checkRuleConditions(RuleOperator operator, List<Clause> conditions) {
		boolean conditionMet = true;
		List<Fact> retrievedFacts = new ArrayList<>();
		for (Clause clause : conditions) {
			conditionMet = conditionMet & evaluateClause(clause, retrievedFacts);
		}
		return conditionMet;
	}

	private boolean evaluateClause(Clause clause, List<Fact> retreivedFacts) {
		if (clause.getExpression().equals(ClauseOperators.exists.name())) {
			if (clause.getParameter() == null) {
				clause.setParameter(retreivedFacts.get(0));
			}
			return exists((Fact) clause.getParameter());
		} else if (clause.getExpression().equals(ClauseOperators.take.name())) {
			Fact fact = getFirstFact(getWm().getInferredFacts(), clause.getParameter().toString());
			if (fact != null) {
				retreivedFacts.add(fact);
				return true;
			}
		}

		return false;
	}

	private Fact getFirstFact(List<Fact> inferredFacts, String factName) {
		Fact fact = null;
		int i = 0;
		while (fact == null && i < inferredFacts.size()) {
			if (inferredFacts.get(i).getName().equals(factName)) {
				fact = inferredFacts.get(i);
			}
			i++;
		}
		return fact;
	}

	private boolean exists(Fact factToCheck) {
		List<Fact> facts = getKb().getFacts();
		for (Fact fact : facts) {
			boolean factExists = true;

			factExists = factExists && fact.getName().equals(factToCheck.getName());
			factExists = factExists && (fact.getSlots().size() == factToCheck.getSlots().size());

			for (int i = 0; i < fact.getSlots().size(); i++) {
				Slot slot = fact.getSlots().get(i);
				if (slot.isMandatory()) {
					Slot slotToCheck = factToCheck.getSlots().get(i);

					factExists = factExists && slot.getName().equals(slotToCheck.getName());
					factExists = factExists && slot.getType().equals(slotToCheck.getType());
					factExists = factExists && slot.getValue().equals(slotToCheck.getValue());
				}
			}

			if (factExists) {
				return true;
			}
		}

		return false;
	}

}
