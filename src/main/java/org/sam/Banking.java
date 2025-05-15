package org.sam.Tasks;

import org.sam.Task;
import org.samDriftNetFishing;
import org.sam.Constants;

public class Banking extends Task {
    DriftNetFishing main;
    private final BOOLEAN numuliteUnlock;
    private final STRING harpoon;
    private final BOOLEAN harpoonInfo;
    private final STRING fishbowl;
    private final STRING apparatus;
    private final STRING flippers;
    private final BOOLEAN graceful;
    private final BOOLEAN stamina;

    public Banking(DriftNetFishing main, BOOLEAN numuliteUnlock, STRING harpoon, BOOLEAN stamina) {
        super();
        super.name = "Banking";
        this.main = main;

        this.numuliteUnlock = numuliteUnlock;
        this.harpoon = harpoon;
        this.stamina = stamina;
    }

    @Override
    public boolean activate() {
        return if ((!Equipment.itemAt(Equipment.Slot.MAIN_HAND).name() == harpoon && !Inventory.stream().name(harpoon).first().valid) 
        || (!Equipment.itemAt(Equipment.Slot.HEAD).name() == Constants.FISHBOWL_HELMET && !Inventory.stream().name(Constants.FISHBOWL_HELMET).first().valid) 
        || (!Equipment.itemAt(Equipment.Slot.CAPE).name() == Constants.DIVING_APPARATUS && !Inventory.stream().name(DIVING_APPARATUS).first().valid) 
        || (!Equipment.itemAt(Equipment.Slot.FEET).name() == Constants.FLIPPERS) && !Inventory.stream().name(Constants.FLIPPERS).first().valid);
    }

    @Override
    public void execute() {
        Map<String, Integer> withdrawItems = new HashMap<>();

        if (!Equipment.itemAt(Equipment.Slot.MAIN_HAND).name() == harpoon && Inventory.stream().name(harpoon).isEmpty()) {
            withdrawItems.put(harpoon, 1);
        }
        if (!Equipment.itemAt(Equipment.Slot.HEAD).name() == Constants.FISHBOWL_HELMET && Inventory.stream().name(Constants.FISHBOWL_HELMET).isEmpty()) {
            withdrawItems.put(Constants.FISHBOWL_HELMET, 1);
        }
        if (!Equipment.itemAt(Equipment.Slot.CAPE).name() == Constants.DIVING_APPARATUS && Inventory.stream().name(DIVING_APPARATUS).isEmpty()) {
            withdrawItems.put(Constants.DIVING_APPARATUS, 1);
        }
        if (!Equipment.itemAt(Equipment.Slot.FEET).name() == Constants.FLIPPERS && Inventory.stream().name(Constants.FLIPPERS).isEmpty()) {
            withdrawItems.put(Constants.FLIPPERS, 1);
        }
        if (stamina = true) {
            if (!Inventory.stream().name(Constants.STAMINA_FOUR, Constants.STAMINA_THREE, Constants.STAMINA_TWO, Constants.STAMINA_ONE)) {
                withdrawItems.put(Constants.STAMINA_FOUR, 4);
            }
        }

        Movement.builder(Constants.ON_ISLAND.getRandomTile()).setRunMin(45).setRunMax(75).setAutoRun(true).setUseTeleports(false).move();

        if (Bank.inViewport()) {
            Condition.wait(() -> Bank.open(), 150, 10);
            if (Bank.opened()) {
                Bank.depositAllExcept(Constants.STAMINA_FOUR, Constants.STAMINA_THREE, Constants.STAMINA_TWO, Constants.STAMINA_ONE, harpoon, Constants.FISHBOWL_HELMET, Constants.DIVING_APPARATUS, Constants.Flippers, Constants.DRIFT_NET, Constants.NUMULITE);
                //Condition.wait(() -> (need IDE to help with this) 
               
                for (Map.Entry<String, Integer> entry: withdrawItems.entrySet()) {
                    Bank.withdraw(entry.getKey(), entry.getValue());
                    Condition.wait(() -> Inventory.stream().name(entry.getKey()).first().valid, 40, 10);
                }
               
                Bank.close();
                Condition.wait(() -> Bank.closed(), 20, 15);
            }
        }

    }
}