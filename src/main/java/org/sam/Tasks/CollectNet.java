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
        Npc annette = Npcs.stream().name("Annette").nearest().first();

        if (!annette.valid()) {
            Notifications.showNotification("Can't find Annette. Stopping the script!");
            ScriptManager.INSTANCE.stop();
            return;
        }
        if (!annette.inViewport()) {
            Camera.turnTo(annette);
            Condition.wait(annette::inViewport, 100, 20);
        }

        if (annette.interact("Nets")) {
//            Handle taking nets
            Condition.sleep(1000);
        }


    }


}
