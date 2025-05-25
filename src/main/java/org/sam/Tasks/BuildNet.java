package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.DriftNetFishing;
import org.sam.Task;

public class BuildNet extends Task {
    DriftNetFishing main;

    public BuildNet(DriftNetFishing main) {
        super();
        super.name = "Set up Drift Net";
        this.main = main;
    }

    Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).nearest().first();

    @Override
    public boolean activate() {
        GameObject netAnchor = Objects.stream().id(Constants.DRIFT_NET_EMPTY).nearest().first();
        return (netAnchor.valid() && netAnchor.reachable() && fish_shoal.reachable());
    }

    @Override
    public void execute() {
        GameObject netAnchor = Objects.stream().id(Constants.DRIFT_NET_EMPTY).nearest().first();
        if (!netAnchor.valid()) return;

        if (netAnchor.inViewport()) {
            if (netAnchor.valid()) {
                if (netAnchor.interact("Set up")) {
                    Condition.wait(() -> !netAnchor.valid() || !Players.local().inMotion(), 100, 20);
                }
            } else {
                Camera.turnTo(netAnchor);
                Movement.step(netAnchor.tile());
                Condition.wait(() -> !Players.local().inMotion(), 50, 100);
            }
        }
    }
}
