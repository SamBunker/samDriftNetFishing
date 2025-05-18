package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.Objects;
import org.sam.Task;
import org.sam.Constants;
import org.sam.DriftNetFishing;

import java.util.*;

public class ChaseFish extends Task {
    DriftNetFishing main;

    private final Set<Integer> chasedFish = new HashSet<>();
    private final Random random = new Random();
    private int chaseCount = 0;
    private int chaseLimit = random.nextInt(2) + 3;

    public ChaseFish(DriftNetFishing main) {
        super();
        super.name = "ChaseFish";
        this.main = main;
    }

    Boolean annetta = Npcs.stream().id(Constants.NPC_ANNETTE).within(10).nearest().first().reachable();

    @Override
    public boolean activate() {
        return annetta;
    }

    @Override
    public void execute() {
        List<GameObject> nets = new ArrayList<>();
        Objects.stream()
                .filter(obj -> obj.id() == Constants.DRIFT_NET_EMPTY
                    || obj.id() == Constants.DRIFT_NET_NET
                    || obj.id() == Constants.DRIFT_NET_AND_FSH
                    || obj.id() == Constants.DRIFT_NET_FULL)
                .forEach(nets::add);
        
        Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).filter(npc -> nets.stream().anyMatch(net -> net.tile().distanceTo(npc.tile()) <= 4) && !chasedFish.contains(npc.index())).nearest().first();
        if (!fish_shoal.valid()) {
            fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).within(15).nearest().first();
        }
        if (fish_shoal.valid()) {
            if (fish_shoal.inViewport() || Movement.step(fish_shoal)) {
                if (fish_shoal.interact("Chase")) {
                    chasedFish.add(fish_shoal.index());
                    chaseCount++;
                    Condition.wait(() -> !Players.local().inMotion() && Players.local().animation() == -1, 100, 15);

                    if (chaseCount >= chaseLimit) {
                        chasedFish.clear();
                        chaseCount = 0;
                        chaseLimit = random.nextInt(2) + 3;
                    }
                }
            } else {
                Camera.turnTo(fish_shoal);
                Movement.step(fish_shoal.tile());
                Condition.wait(() -> !Players.local().inMotion(), 20, 15);
            }
        } else {
            Notifications.showNotification("No fish found! What the heck?");
        }
    }
}
