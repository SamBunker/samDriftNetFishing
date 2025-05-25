package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import java.util.concurrent.ThreadLocalRandom;

import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;
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
                Condition.wait(() -> Players.local().tile().distanceTo(Constants.ON_ISLAND.getRandomTile()) > 15, 200, 10);
            }
        }
        if (Inventory.open()) {
            Condition.wait(() -> Inventory.opened(), 25, 15);
        }
    }

    GameObject tunnel_entrance = Objects.stream().id(Constants.TUNNEL).nearest().first();
    Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).within(15).nearest().first();

    @Override
    public boolean activate() {
        return !fish_shoal.reachable();
    }

    @Override
    public void execute() {
        Component compass = Widgets.component(601, 33);

        if (Constants.IN_GAME.contains(Players.local())) {
//            Tile currentTile = Players.local().tile();
//            Tile targetTile = new Tile(currentTile.x() + 6, currentTile.y() + 3);
//            Movement.step(targetTile);
//            Condition.wait(() -> !Players.local().inMotion(), 200, 10);
            GameObject plantdoor = Objects.stream().name("Plant door").nearest().first();
            if (plantdoor.reachable()) {
                plantdoor.interact("Navigate");
                Condition.wait(() -> !Players.local().inMotion(), 400, 10);
            } else {
                Movement.moveTo(plantdoor.tile());
            }
            return;
        }
        if (Constants.UNDERWATER.contains(Players.local())) {
            Movement.moveTo(Constants.DRIFT_ENTRANCE_CHECKPOINT_ONE.getRandomTile());
            Condition.wait(() -> Constants.DRIFT_ENTRANCE_CHECKPOINT_ONE.contains(Players.local()), 150, 10);
            if (tunnel_entrance.valid() && tunnel_entrance.reachable()) {
                if (!tunnel_entrance.inViewport() && tunnel_entrance.reachable()) {
                    Camera.turnTo(tunnel_entrance);
                    Movement.moveTo(tunnel_entrance);
                    Condition.wait(() -> !Players.local().inMotion(), 200, 15);
                } else if (tunnel_entrance.inViewport() && tunnel_entrance.reachable()) {
                    if (Constants.DRIFT_ENTRANCE_TILE.reachable()) {
                        Movement.moveTo((Constants.DRIFT_ENTRANCE_TILE));
                        tunnel_entrance.interact("Pay");
                        Condition.wait(() -> Players.local().tile().distanceTo(Constants.DRIFT_ENTRANCE_TILE) > 2, 500, 12);
                        if (compass.visible()) {
                            compass.interact("Look South");
                        }
                    }
                }
            }
        }
        if (Constants.ON_ISLAND.contains(Players.local())) {
            OnIsland();
        }
    }
}
