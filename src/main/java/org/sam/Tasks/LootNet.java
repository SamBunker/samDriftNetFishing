package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.DriftNetFishing;
import org.sam.Task;

public class LootNet extends Task {
    DriftNetFishing main;

    public LootNet(DriftNetFishing main) {
        super();
        super.name = "Looting the Nets";
        this.main = main;
    }
    Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).within(15).nearest().first();

    @Override
    public boolean activate() {
        GameObject net = Objects.stream().id(Constants.DRIFT_NET_FULL).nearest().first();
        return net.valid() && fish_shoal.reachable();
    }

    @Override
    public void execute() {
        GameObject net = Objects.stream().id(Constants.DRIFT_NET_FULL).nearest().first();
        if (net.valid() && net.interact("Harvest")) {
            Condition.wait(() -> Chat.chatting(), 25, 20);

            if (Chat.chatting()) {
                Chat.sendInput(0);
            }
            Condition.wait(() -> Widgets.component(607, 0).visible(), 100, 30);
            if (Widgets.component(607, 6).visible()) {
                Widgets.component(607, 6).click();
                Condition.wait(() -> Widgets.component(607, 9).visible(), 10, 15);
                Widgets.component(607, 9).click();
                Condition.wait(() -> !Widgets.component(607, 9).visible(), 20, 15);
            }
        }
    }
}
