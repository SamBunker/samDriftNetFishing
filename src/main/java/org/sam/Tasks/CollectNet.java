package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.InteractableEntity;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;
import org.sam.Constants;
import org.sam.Task;
import org.sam.DriftNetFishing;

public class CollectNet extends Task {
    DriftNetFishing main;

    public CollectNet(DriftNetFishing main) {
        super();
        super.name = "Collect Net";
        this.main = main;
    }
    Npc fish_shoal = Npcs.stream().id(Constants.FISH_SHOAL).within(15).nearest().first();

    @Override
    public boolean activate() {
        return Inventory.stream().name(Constants.NET_NAME).count() == 0 && fish_shoal.reachable();
    }

    @Override
    public void execute() {
        GameObject net_lady = Objects.stream().id(Constants.NPC_ANNETTE).nearest().first();

        if (!net_lady.valid()) {
            Notifications.showNotification("Can't find Annette. Stopping the script!");
            ScriptManager.INSTANCE.stop();
            return;
        }
        if (!net_lady.inViewport()) {
            Camera.turnTo(net_lady);
            Condition.wait(() -> net_lady.inViewport(), 100, 20);
        }

        if (net_lady.interact("Nets")) {
            Condition.wait(() -> Players.local().interacting() != null && Players.local().interacting().equals(net_lady), 200, 20);
            Long firstCheck = Inventory.stream().name("Drift net").count();
            if (Widgets.component(309, 5).visible()) {
                Widgets.component(309, 5).interact("Withdraw-5");
                Condition.wait(() -> Inventory.stream().name("Drift net").count() > 0, 200, 20);
                Long secondCheck = Inventory.stream().name("Drift net").count();
                if (!(secondCheck > firstCheck)) {
                    Notifications.showNotification("You ran out of drift nets!");
                    ScriptManager.INSTANCE.stop();
                } else {
                    Widgets.component(309, 11).click();
                    Condition.wait(() -> !Widgets.component(309, 11).visible(), 10, 20);
                }
            }
        }
    }
}