package net.nadschlaeger;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public class Clause {

    private String expression; // JavaScript

    private Object parameter;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return expression;
    }
}
