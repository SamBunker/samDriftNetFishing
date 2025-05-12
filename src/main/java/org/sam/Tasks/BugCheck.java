package org.sam.Tasks;

import org.powbot.api.Calculations;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;
import org.sam.Constants;
import org.sam.DriftNetFishing;
import org.sam.Task;


public class BugCheck extends Task {
    DriftNetFishing main;

    public BugCheck(DriftNetFishing main) {
        super();
        super.name = "Bug Fix";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return !Constants.DRIFT_NET_AREA.contains(Players.local()) ||
                Calculations.weight() > 24 || Constants.BUGGED_AREA.contains(Players.local());
    }

    @Override
    public void execute() {
        if (!Constants.BUGGED_AREA.contains(Players.local()) && !Constants.DRIFT_NET_AREA.contains(Players.local())) {
            Notifications.showNotification("Outside of bounds. Stopping Script!");
            ScriptManager.INSTANCE.stop();
        }
        if (Constants.BUGGED_AREA.contains(Players.local())) {
            if (Calculations.weight() > 24) {
                Item weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND);

                if (weapon.valid()) {
                    if (Inventory.isFull()) {
                        Notifications.showNotification("Can't unequip item-inventory is full.");
                        ScriptManager.INSTANCE.stop();
                        return;
                    }

                    if (weapon.interact("Remove")) {
                        Condition.wait(() -> !Equipment.itemAt(Equipment.Slot.MAIN_HAND).valid(), 100, 20);
                        Notifications.showNotification("Unequipped item to reduce weight");
                    }
                } else {
                    Notifications.showNotification("Overweight but nothing to unequip. Too much weight in inventory!");
                    ScriptManager.INSTANCE.stop();
                }
            }
            GameObject gate = Objects.stream().action("Pass").nearest().first();
            if (gate.valid()) {
                if (!gate.inViewport()) {
                    Camera.turnTo(gate);
                    Movement.moveTo(gate);
                }
                if (gate.interact("Pass")) {
                    Condition.wait(() -> Players.local().inMotion && Constants.DRIFT_NET_AREA.contains(Players.local()), 100, 18);
                }
            } else {
                Notifications.showNotification("Unable to locate gate. Stopping Script");
                ScriptManager.INSTANCE.stop();

            }
        }
    }
}
