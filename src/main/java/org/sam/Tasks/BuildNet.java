package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.DriftNetFishing;
import org.sam.Task;

public class BuildNet extends Task {
    DriftNetFishing main;

    public BuildNet(DriftNetFishing main) {
        super();
        super.name = "Set up Drift Net";
        this.main = main;
    }

    @Override
    public boolean activate() {
        GameObject netAnchor = Objects.stream().within(10).name("Drift net anchors").action("Set up").nearest().first();
        return netAnchor.valid();
    }

    @Override
    public void execute() {
        GameObject netAnchor = Objects.stream().within(10).name("Drift net anchors").action("Set up").nearest().first();
        if (!netAnchor.valid()) return;

        if (netAnchor.inViewport()) {
            if (netAnchor.valid()) {
                if (netAnchor.interact("Set up")) {
                    Condition.wait(() -> !netAnchor.valid() || !Players.local().inMotion(), 100, 20);
                }
            } else {
                Camera.turnTo(netAnchor);
                Movement.step(netAnchor.tile());
            }
        }
    }
}
