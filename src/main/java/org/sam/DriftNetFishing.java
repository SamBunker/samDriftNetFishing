package org.sam;
import org.powbot.api.script.*;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;
import org.sam.Tasks.*;

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
        name = "Sam's Drift Net Fishing",
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
        new ScriptUploader().uploadAndStart("Sam Drift Net", "", "localhost:5575", true, true);
    }

    @Override
    public void onStart() {
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