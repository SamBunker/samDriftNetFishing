package org.sam;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Component;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Widgets;
import org.powbot.api.script.*;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;
import org.sam.Tasks.*;
import org.sam.CameraActions.*;

import java.util.ArrayList;

@ScriptConfiguration.List({
        @ScriptConfiguration(
                name = "Harpoon",
                allowedValues = {"Merfolk trident", "Trident of the seas", "Trident of the swamp", "Dragon harpoon", "Harpoon"},
                defaultValue = "Dragon harpoon",
                description = "Which trident or harpoon are you using?",
                optionType = OptionType.STRING
        ),
        @ScriptConfiguration(
                name = "Stamina",
                description = "Use Stamina Potions?",
                optionType = OptionType.BOOLEAN
        ),
        @ScriptConfiguration(
                name = "NumuliteUnlock",
                description = "Did you pay 20,000 numulite for permanent access to drift net fishing? If not, grab Numulite from bank.",
                optionType = OptionType.BOOLEAN
        )
})

@ScriptManifest(
        name = "Sam Drift Net",
        description = "Begin in area, discards fish.",
        author = "Sam",
        version = "1",
        category = ScriptCategory.Fishing
)
public class DriftNetFishing extends AbstractScript {
        Boolean NumuliteUnlock;
        String Harpoon = "";
        Boolean Stamina;

    private final ArrayList<Task> taskList = new ArrayList<Task>();

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("Sam Drift Net", "", "R52T90A6VCM", true, false);
    }

    @Override
    public void onStart() {
        if (!Widgets.component(601, 69).visible()) {
            if (Widgets.component(601, 95).interact("Show More")) {
                Condition.wait(() -> Widgets.component(601, 69).visible(), 80, 10);
            }
        }
        Game.tab(Game.Tab.SETTINGS);
        Condition.wait(() -> Game.tab().equals(Game.Tab.SETTINGS), 40, 20);
        Component zoomSlider = Widgets.component(116, 49);
        if (zoomSlider.visible()) {
            zoomSlider.click();
        }
        Condition.wait(() -> zoomSlider.click(), 30, 10);
        Component compass = Widgets.component(601, 33);
        if (compass.visible()) {
            compass.interact("Look North");
        }
        NumuliteUnlock = getOption("NumuliteUnlock");
        Harpoon = getOption("Harpoon");
        Stamina = getOption("Stamina");
        taskList.add(new Banking(this, NumuliteUnlock, Harpoon, Stamina));
        taskList.add(new ReturnToArea(this));
        taskList.add(new BuildNet(this));
        taskList.add(new LootNet(this));
        taskList.add(new SeaweedSporePickup(this));
        taskList.add(new ChaseFish(this));
    }

    @Override
    public void poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                if (ScriptManager.INSTANCE.isStopping()) {
                    break;
                }
            }
        }
    }
}