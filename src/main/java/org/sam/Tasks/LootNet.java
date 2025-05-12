package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Chat;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Widgets;
import org.sam.DriftNetFishing;
import org.sam.Task;

public class LootNet extends Task {
    DriftNetFishing main;

    public LootNet(DriftNetFishing main) {
        super();
        super.name = "Looting the Nets";
        this.main = main;
    }

    @Override
    public boolean activate() {
        GameObject net = Objects.stream().within(10).name("Drift net anchors").action("Harvest").nearest().first();
        return net.valid();
    }

    @Override
    public void execute() {
        GameObject net = Objects.stream().within(10).name("Drift net anchors").action("Harvest").nearest().first();
        if (net.valid() && net.interact("Harvest")) {
            Condition.wait(() -> Chat.canContinue() || Chat.optionCount() > 0, 100, 20);

            if (Chat.optionCount() > 0) {
                Chat.selectOption(0);
            }

            Condition.wait(() -> Widgets.component(12, 22).viosible(), 100, 30);
            if (Widgets.component(12, 22).visible()) {
                Widgets.component(12,22).click();
            }
        }
    }
}
