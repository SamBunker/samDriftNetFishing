package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.WebWalkingResult;
import org.powbot.dax.api.DaxWalker;
import org.powbot.dax.api.models.RunescapeBank;
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
                Condition.wait(() -> Players.local().tile().distanceTo(Constants.ON_ISLAND.getRandomTile()) > 15, 50, 20);
            }
        }
        if (Inventory.open()) {
            Condition.wait(() -> Inventory.opened(), 25, 15);
        }
    }

    Npc annetta = Npcs.stream().id(Constants.NPC_ANNETTE).within(10).nearest().first();
    GameObject tunnel_entrance = Objects.stream().id(Constants.TUNNEL).nearest().first();
    


    @Override
    public boolean activate() {
        return !annetta.reachable();
    }

    @Override
    public void execute() {
        Component compass = Widgets.component(601, 33);
        if (annetta.valid() && !annetta.reachable()) {
            // if try walking to, else navigate through plant door
            //Go through the door
        } 
        if (tunnel_entrance.valid() && Players.local().tile().distanceTo(tunnel_entrance) <= 3) {
            if (tunnel_entrance.inViewport()) {
                if (Constants.DRIFT_ENTRANCE_TILE.reachable()) {
                    Movement.moveTo(Constants.DRIFT_ENTRANCE_TILE);
                    tunnel_entrance.interact("Pay");
                    Condition.wait(() -> Players.local().tile().distanceTo(Constants.DRIFT_ENTRANCE_TILE) > 2, 100, 12);
                    if (compass.visible()) {
                        compass.interact("Look South");
                    }
                } else {
                    if (compass.visible()) {
                        compass.interact("Look South");
                    }
                    
            }
        }
        

        
        
        //May need to rewrite these checks
        GameObject door = Objects.stream().name("Plant door").first();
        if (annetta.valid()) {
            if (annetta.inViewport()) {
                if (door.interact("Navigate")) {
                    Condition.wait(() -> !Players.local().interacting().inMotion(), 50, 14);
                    return;
                }
            } else {
                Camera.turnTo(annetta);
                Movement.moveTo(annetta);
            }
        } else {
            GameObject tunnel_entrance = Objects.stream().id(Constants.TUNNEL).nearest().first();
            if (tunnel_entrance.valid()) {
                if (tunnel_entrance.inViewport()) {
                    Movement.moveTo(Constants.DRIFT_ENTRANCE_TILE);
                    tunnel_entrance.interact("Pay");
                    Condition.wait(() -> Players.local().tile().distanceTo(Constants.DRIFT_ENTRANCE_TILE) > 2, 100, 12);
                    Component compass = Widgets.component(601, 33);
                    if (compass.visible()) {
                        compass.interact("Look South");
                    }
                    door.interact("Navigate");
                    Condition.wait(() -> !Players.local().inMotion(), 100, 15);
                    return;
                } else {
                    Camera.turnTo(tunnel_entrance);
                    Movement.moveTo(tunnel_entrance);
                }
            } else {
                if (!Constants.ON_ISLAND.contains(Players.local())) {
                    Movement.builder(RunescapeBank.FOSSIL_ISLAND.getPosition());
                    Movement.builder(Constants.ON_ISLAND.getRandomTile());
                } else {
                    OnIsland();
                }
            }
        }
    }
}
