package net.nadschlaeger;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public class Slot {

    private String name;

    private String type;

    private Object value;

    private boolean mandatory;

    public Slot() {}

    public Slot(String name, String type, Object value, boolean mandatory) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.mandatory = mandatory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public String toString() {
        return name;
    }
}
