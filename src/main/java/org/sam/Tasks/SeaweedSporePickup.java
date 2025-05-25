package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.Task;
import org.sam.DriftNetFishing;
import org.sam.Constants;

public class SeaweedSporePickup extends Task {
    DriftNetFishing main;

    public SeaweedSporePickup(DriftNetFishing main) {
        super();
        super.name = "Picking up Seaweed Spores";
        this.main = main;
    }

    Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).within(15).nearest().first();

    @Override
    public boolean activate() {
        return GroundItems.stream().name(Constants.SEAWEEDSPORE).first().reachable() && fish_shoal.reachable();
    }

    @Override
    public void execute() {
        GroundItem spore = GroundItems.stream().name(Constants.SEAWEEDSPORE).nearest().first();
        if (!spore.valid() || !spore.reachable()) return;
        if (spore.reachable()) {
            if (spore.inViewport()) {
                if (spore.interact("Take")) {
                    Condition.wait(() -> !spore.valid(), 100, 15);
                }
            } else {
                Camera.turnTo(spore);
                Movement.step(spore.tile());
            }
        }
    }
}
