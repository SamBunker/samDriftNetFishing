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


    @Override
    public boolean activate() {
        return Constants.DRIFT_NET_AREA.contains(GroundItems.stream().name(Constants.SEAWEEDSPORE).first());
    }

    @Override
    public void execute() {
        GroundItem spore = GroundItems.stream().name(Constants.SEAWEEDSPORE).nearest().first();
        if (!spore.valid()) return;
        if (Constants.DRIFT_NET_AREA.contains(spore)) {
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
