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
            return;
        }
        if (Constants.ON_ISLAND.contains(Players.local())) {
            OnIsland();
            return;
        }
        if (Constants.DIGSITE_AREA.contains(Players.local())) {
            Component inventory = Widgets.component(601, 89);
            if (Inventory.open()) {
                inventory.click();
            }
            Tile[] path = {
                    new Tile(3325, 3432, 0),
                    new Tile(3328, 3436, 0),
                    new Tile(3328, 3442, 0),
                    new Tile(3333, 3445, 0),
                    new Tile(3341, 3444, 0),
                    new Tile(3348, 3444, 0),
                    new Tile(3355, 3445, 0),
                    new Tile(3361, 3445, 0)
            };
//            TilePath tilepath = new TilePath(path);
            Tile targetLocation = path[path.length-1];
            Npc bargeGuard = Npcs.stream().name("Barge guard").within(targetLocation, 4).first();

            for (Tile tile : path) {
                if (targetLocation.distance() < 5 || (bargeGuard != null && bargeGuard.inViewport())) {
                    break;
                }
                if (Movement.walkTo(tile)) {
                    Condition.wait(() -> tile.distance() < 2 || !Players.local().inMotion(), 200, 10);
                }
            }

//            for (int i = 0; i<path.length;i++) {
//                if (targetLocation.distance()<5 || bargeGuard.inViewport()) {
//                    break;
//                } else {
//                    tilepath.traverse();
//                    Condition.wait(() -> Players.local().inMotion(), 200, 15);
//                }
//            }
            if (bargeGuard.inViewport()) {
                Condition.wait(() -> !Players.local().inMotion(), 200, 15);
                Movement.moveTo(Constants.ROWBOAT_LOCATION);
                Condition.wait(() -> Constants.ROWBOAT_LOCATION.tile() == Players.local().tile(), 100, 15);
                Movement.moveTo(Constants.ON_ISLAND.getRandomTile());
                Condition.wait(() -> Constants.ON_ISLAND.contains(Players.local()), 200, 20);
                return;
            }
            return;
        }
        Movement.walkTo(Constants.DIGSITE_GLIDER.getRandomTile());
        Condition.wait(() -> Constants.DIGSITE_GLIDER.contains(Players.local()), 200, 20);
//
//        Camera.turnTo(Constants.DIGSITE_CHECKONE.getCentralTile());
//        Movement.step(Constants.DIGSITE_CHECKONE.getRandomTile());
//        Condition.wait(() -> Constants.DIGSITE_CHECKONE.contains(Players.local()), 200, 20);
//
//        Camera.turnTo(Constants.DIGSITE_CHECKTWO.getCentralTile());
//        Movement.step(Constants.DIGSITE_CHECKTWO.getRandomTile());
//        Condition.wait(() -> Constants.DIGSITE_CHECKTWO.contains(Players.local()), 200, 20);
//
//        Movement.step(Constants.DIGSITE_CHECKTHREE.getRandomTile());
//        Condition.wait(() -> Constants.DIGSITE_CHECKTHREE.contains(Players.local()), 200, 20);
//        Movement.moveTo(Constants.ROWBOAT_LOCATION);
//
//        if (compass.visible()) {
//            compass.interact("Look North");
//        }
//        Movement.moveTo(Constants.ON_ISLAND.getRandomTile());
//        Condition.wait(() -> Constants.ON_ISLAND.contains(Players.local()), 200, 20);
//        return;
    }
}
