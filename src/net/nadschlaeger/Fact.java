package net.nadschlaeger;

import java.util.List;

/**
 * Created by snadschlaeger on 27.12.2016.
 */
public class Fact {

    private String name;

    private List<Slot> slots;

    public Fact() {

    }

    public Fact(String factName, List<Slot> slots) {
        this.name = factName;
        this.slots = slots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return name;
    }
}
