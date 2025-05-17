package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.WebWalkingResult;
import org.sam.Constants;
import org.sam.DriftNetFishing;
import org.sam.Task;


public class ReturnToArea extends Task {
    DriftNetFishing main;

    public ReturnToArea(DriftNetFishing main) {
        super();
        super.name = "Return to Area";
        this.main = main;
    }

    public void InUnderwaterEntrance() {
        Npc Ceto = Npcs.stream().id(Constants.NPC_CETO).nearest().first();
        
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
                    if (Chat.clickContinue()) {
                        Condition.wait(() -> Chat.chatting(), 20, 10);
                        if (!Chat.canContinue()) {
                            GameObject tunnel_entrance = Objects.stream().id(Constants.TUNNEL).nearest().first();
                            if (tunnel_entrance.valid()) {
                                if (tunnel_entrance.inViewport()) {
                                    tunnel_entrance.interact("Enter");
                                    Condition.wait(() -> Constants.DRIFT_ENTRANCE.contains(Players.local()), 100, 18);
                                } else if (!tunnel_entrance.inViewport()) {
                                    Camera.turnTo(tunnel_entrance);
                                    Movement.moveTo(tunnel_entrance);
                                    Condition.wait(() -> !Players.local().inMotion(), 70, 10);
                                    tunnel_entrance.interact("Enter");
                                    Condition.wait(() -> Constants.DRIFT_ENTRANCE.contains(Players.local()), 100, 18);
                                }
                            }
                        } else {
                            Condition.wait(() -> !Chat.chatting(), 20, 10);
                            if (Chat.completeChat("Okay, here's 200 numulites.", "Tap here to continue")) {
                                Condition.wait(() -> !Chat.chatting(), 500, 10);
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
        if (weapon.valid()) {
            if (!Inventory.isFull()) {
                if (Equipment.INSTANCE.open()) {
                    Condition.wait(() -> Equipment.INSTANCE.opened(), 300, 10);
                    weapon.interact("Remove");
                    Condition.wait(() -> !Equipment.itemAt(Equipment.Slot.MAIN_HAND).valid(), 35, 15);
                }
            }
        }
        if (offhand.valid()) {
            if (!Inventory.isFull()) {
                if (Equipment.INSTANCE.open()) {
                    Condition.wait(() -> Equipment.INSTANCE.opened(), 300, 10);
                    offhand.interact("Remove");
                    Condition.wait(() -> !Equipment.itemAt(Equipment.Slot.OFF_HAND).valid(), 35, 15);
                }
            }
        }
        GameObject boat = Objects.stream().name(Constants.ISLAND_ROWBOAT).nearest().first();
        if (boat.valid()) {
            if (!boat.inViewport()) {
                Camera.turnTo(boat);
                Movement.moveTo(boat);
                Condition.wait(() -> !Players.local().inMotion(), 25, 15);
            }
            if (boat.inViewport()) {
                boat.interact("Dive");
                Condition.wait(() -> Constants.DRIFT_ENTRANCE.contains(Players.local()), 50, 20);
            }
        } else {
            Movement.builder(Constants.DRIFT_ENTRANCE.getRandomTile()).setAutoRun(true).setUseTeleports(true).move();
        }
        if (Inventory.open()) {
            Condition.wait(() -> Inventory.opened(), 25, 15);
        }
    }

    @Override
    public boolean activate() {
        return !Constants.DRIFT_NET_AREA.contains((Players.local()));
    }

    @Override
    public void execute() {
        WebWalkingResult result = Movement.builder(Constants.DRIFT_NET_AREA.getRandomTile()).setAutoRun(true).setUseTeleports(true).move();
        if (!result.getSuccess()) {
            if (Constants.ON_ISLAND.contains(Players.local())) {
                OnIsland();
                Movement.builder(Constants.DRIFT_NET_AREA.getRandomTile()).setAutoRun(true).setUseTeleports(true).move();
            }
            if (Constants.DRIFT_ENTRANCE.contains(Players.local())) {
                GameObject tunnel_entrance = Objects.stream().id(Constants.TUNNEL).nearest().first();
                if (tunnel_entrance.valid()) {
                    tunnel_entrance.interact("Pay");
                }
            }
        }
    }
}
