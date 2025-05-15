package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;
import org.sam.Constants;
import org.sam.DriftNetFishing;
import org.sam.Task;


public class ReturnToArea extends Task {
    DriftNetFishing main;
    private final Boolean numuliteUnlock;

    public ReturnToArea(DriftNetFishing main, Boolean numuliteUnlock) {
        super();
        super.name = "Return to Area";
        this.main = main;
        this.numuliteUnlock = numuliteUnlock;
    }

    public void InDriftEntrance() {
        Condition.wait(() -> Constants.DRIFT_ENTRANCE.contains(Players.local()), 100, 18);
        GameObject seaweed_gate = Objects.stream().name(Constants.PLANT_DOOR).nearest().first();
        if (seaweed_gate.valid()) {
            if (seaweed_gate.inViewport()) {
                seaweed_gate.interact("Navigate");
                Condition.wait(() -> Constants.DRIFT_NET_AREA.contains(Players.local()), 100, 18);
            } else if (!seaweed_gate.inViewport()) {
                Camera.turnTo(seaweed_gate);
                Movement.moveTo(seaweed_gate);
                Condition.wait(() -> !Players.local().inMotion(), 70, 10);
                seaweed_gate.interact("Navigate");
                Condition.wait(() -> Constants.DRIFT_NET_AREA.contains(Players.local()), 100, 18);
            }
        }
    }

    public void InUnderwaterEntrance() {
        Npc Ceto = Npcs.stream().name(Constants.TUNNEL_ENTRANCE_NPC).nearest().first();
        
        if (Ceto.valid()) {
            if (!Ceto.inViewport()) {
                Camera.turnTo(Ceto);
                Movement.moveTo(Ceto);
                Condition.wait(() -> !Players.local().inMotion(), 80, 15);
            }
            if (Ceto.inViewport()) {
                Ceto.interact("Pay");
                if (Chat.chatting()) {
                    Condition.wait(() -> Chat.chatting(), 20, 10);
                    if (Chat.canContinue()) {
                        if (Chat.continueChat("Tap here to continue")) {
                            if (!Chat.canContinue()) {
                                Condition.wait(() -> !Chat.chatting(), 40, 10);
                                GameObject tunnel_entrance = Objects.stream().name(Constants.TUNNEL).nearest().first();
                                if (tunnel_entrance.valid()) {
                                    if (tunnel_entrance.inViewport()) {
                                        tunnel_entrance.interact("Enter");
                                        Condition.wait(() -> Constants.DRIFT_ENTRANCE.contains(Players.local()), 100, 18);
                                    } else if (!tunnel_entrance.inViewport()) {
                                        Camera.turnTo(tunnel_entrance);
                                        // Condition wait for adjusting camera angles
                                        Movement.moveTo(tunnel_entrance);
                                        Condition.wait(() -> !Players.local().inMotion(), 70, 10);
                                        tunnel_entrance.interact("Enter");
                                        Condition.wait(() -> Constants.DRIFT_ENTRANCE.contains(Players.local()), 100, 18);
                                    }
                                }
                            } else {
                                Condition.wait(() -> !Chat.chatting(), 20, 10);
                                if (Chat.completeChat(arrayOf("Okay, here's 200 numulites.", "Tap here to continue")) {
                                    Condition.wait(() -> !Chat.chatting(), 500, 10);
                                    // You will automatically enter the location now.
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    public void OnIsland() {
        Condition.wait(() -> Constants.ON_ISLAND.contains(Players.local()), 100, 18);
        

        Item weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND);
        Item offhand = Equipment.itemAt(Equipment.Slot.OFF_HAND);
//        Open equipment widget
        if (weapon.valid()) {
            if (!Inventory.isFull()) {
                weapon.interact("Remove");
                Condition.wait(() -> !Equipment.itemAt(Equipment.Slot.MAIN_HAND).valid(), 35, 15);
            }
        }
        if (offhand.valid()) {
            if (!Inventory.isFull()) {
                offhand.interact("Remove");
                Condition.wait(() -> !Equipment.itemAt(Equipment.Slot.OFF_HAND).valid(), 35, 15);
            }
        }
//        Finish sequence
    }

    @Override
    public boolean activate() {
        return !Constants.DRIFT_NET_AREA.contains(Players.local());
    }

    @Override
    public void execute() {
        if (!numuliteUnlock) {
            Movement.builder(Constants.UNDER_WATER_NEXT_TO_TUNNEL.getRandomTile()).setAutoRun(true).setUseTeleports(true).move();

        } else {
            Movement.builder(Constants.DRIFT_NET_AREA.getRandomTile()).setAutoRun(true).setUseTeleports(true).move();
        }

//        Okay, we know the guy is outside of the area, now we need to get him back. So let's do checks along the way to identify where he is at.
        if (Constants.DRIFT_ENTRANCE.contains(Players.local())) {
            InDriftEntrance();

//            Identify the gate, interact. If not in view, InView turn camera to,
        } else if (Constants.UNDER_WATER_NEXT_TO_TUNNEL.contains(Players.local()) || Constants.UNDER_WATER_NEXT_TO_ANCHOR.contains(Players.local())) {
            InUnderwaterEntrance();
            InDriftEntrance();

        } else if (Constants.ON_ISLAND.contains(Players.local())) {
            OnIsland();
            InUnderwaterEntrance();
            InDriftEntrance();
        } else {
//            Temp
            Notifications.showNotification("Not on island, stopping script!");
            ScriptManager.INSTANCE.stop();
        }

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
    }
}
