package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.sam.DriftNetFishing;
import org.sam.Task;
import org.sam.Constants;

import java.util.HashMap;
import java.util.Map;

import static org.sam.Constants.*;

public class Banking extends Task {
    DriftNetFishing main;
    private final Boolean numuliteUnlock;
    private final String harpoon;
    private final Boolean stamina;


    public boolean hasItem(String name) {
        return Inventory.stream().name(name).isNotEmpty() ||
                Equipment.stream().name(name).isNotEmpty();
    }

    public Banking(DriftNetFishing main, Boolean numuliteUnlock, String harpoon, Boolean stamina) {
        super();
        super.name = "Banking";
        this.main = main;

        this.numuliteUnlock = numuliteUnlock;
        this.harpoon = harpoon;
        this.stamina = stamina;
    }

    @Override
    public boolean activate() {
        return (!hasItem(harpoon)) || (!hasItem(FISHBOWL_HELMET)) || (!hasItem(DIVING_APPARATUS)) || (!hasItem(FLIPPERS));
    }

    @Override
    public void execute() {
        Map<String, Integer> withdrawItems = new HashMap<>();

        if (!hasItem(harpoon)) {
            withdrawItems.put(harpoon, 1);
        }
        if (!hasItem(FISHBOWL_HELMET)) {
            withdrawItems.put(Constants.FISHBOWL_HELMET, 1);
        }
        if (!hasItem(DIVING_APPARATUS)) {
            withdrawItems.put(DIVING_APPARATUS, 1);
        }
        if (!hasItem(FLIPPERS)) {
            withdrawItems.put(Constants.FLIPPERS, 1);
        }
        if (stamina) {
            if (Inventory.stream().name(Constants.STAMINA_FOUR, Constants.STAMINA_THREE, Constants.STAMINA_TWO, Constants.STAMINA_ONE).isEmpty()) {
                withdrawItems.put(Constants.STAMINA_FOUR, 4);
            }
        if (!numuliteUnlock) {
                withdrawItems.put(NUMULITE, 200);
            }
        }

        // Movement to island
        Movement.builder(Constants.ON_ISLAND.getRandomTile()).setAutoRun(true).setUseTeleports(false).move();

        if (Bank.inViewport()) {
            Condition.wait(() -> Bank.open(), 150, 10);
            if (Bank.opened()) {
                Bank.depositAllExcept(Constants.STAMINA_FOUR, Constants.STAMINA_THREE, Constants.STAMINA_TWO, Constants.STAMINA_ONE, harpoon, Constants.FISHBOWL_HELMET, DIVING_APPARATUS, FLIPPERS, Constants.DRIFT_NET, Constants.NUMULITE);
               
                for (Map.Entry<String, Integer> entry: withdrawItems.entrySet()) {
                    Bank.withdraw(entry.getKey(), entry.getValue());
                    Condition.wait(() -> Inventory.stream().name(entry.getKey()).first().valid(), 40, 10);
                }
               
                Bank.close();
                Condition.wait(() -> !Bank.opened(), 20, 15);
            }
        }

    }
}