package org.sam.Tasks;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;
import org.sam.Constants;
import org.sam.Task;
import org.sam.DriftNetFishing;

import java.util.HashSet;
import java.util.Set;

public class CollectNet extends Task {
    DriftNetFishing main;

    public CollectNet(DriftNetFishing main) {
        super();
        super.name = "Collect Net";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Inventory.stream().name(Constants.NET_NAME).isEmpty();
    }

    @Override
    public void execute() {
        Npc net_lady = Npcs.stream().id(Constants.NPC_ANNETTE).nearest().first();

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
            Long firstCheck = Inventory.stream().name("Drift net").count();
            if (Widgets.component(309, 0).visible()) {
                Widgets.component(309, 0).interact("Withdraw-5");
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