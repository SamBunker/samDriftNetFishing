package org.sam.Tasks;
import org.powbot.api.Condition;
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
        return true;
    }

    @Override
    public void execute() {
        Npc target = Npcs.stream().name("Fish shoal").filter(npc -> (Constants.EAST_NET.contains(npc.tile()) || Constants.SOUTH_NET.contains(npc.tile())) && !chasedFish.contains(npc.index())).nearest().first();

        if (!target.valid()) {
            target = Npcs.stream().name("Fish shoal").filter(npc -> Constants.STALE_AREA.contains(npc.tile()) && !chasedFish.contains(npc.index())).nearest().first();
        }

        if (target.valid()) {
            if (target.inViewport() || Movement.step(target)) {
                if (target.interact("Chase")) {
                    chasedFish.add(target.index());
                    chaseCount++;
                    Condition.wait(() -> !Players.local().inMotion() && Players.local().animation() == -1, 100, 15);

                    if (chaseCount >= chaseLimit) {
                        chasedFish.clear();
                        chaseCount = 0;
                        chaseLimit = random.nextInt(2) + 3;
                    }
                }
            } else {
                Camera.turnTo(target);
                Movement.step(target.tile());
            }
        }
    }
}
