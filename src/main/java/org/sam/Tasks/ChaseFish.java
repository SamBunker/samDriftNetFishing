package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.*;
import org.sam.Task;
import org.sam.Constants;
import org.sam.DriftNetFishing;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    @Override
    public boolean activate() {
        return Constants.DRIFT_NET_AREA.contains(Players.local());
    }

    @Override
    public void execute() {
        Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).filter(npc -> (Constants.EAST_NET.contains(npc.tile()) || Constants.SOUTH_NET.contains(npc.tile())) && !chasedFish.contains(npc.index())).nearest().first();
        //Npc target = Npcs.stream().name("Fish shoal").filter(npc -> (Constants.EAST_NET.contains(npc.tile()) || Constants.SOUTH_NET.contains(npc.tile())) && !chasedFish.contains(npc.index())).nearest().first();

        if (!Constants.EAST_NET.contains(fish_shoal) && !Constants.SOUTH_NET.contains(fish_shoal)) {
            fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).filter(npc -> Constants.STALE_AREA.contains(npc.tile()) && !chasedFish.contains(npc.index())).nearest().first();
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
