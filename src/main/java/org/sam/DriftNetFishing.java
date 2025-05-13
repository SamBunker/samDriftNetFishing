package org.sam;
import org.powbot.api.script.*;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;
import org.sam.Tasks.BuildNet;
import org.sam.Tasks.ChaseFish;
import org.sam.Tasks.LootNet;
import org.sam.Tasks.SeaweedSporePickup;

import java.util.ArrayList;

@ScriptConfiguration.List({
        @ScriptConfiguration(
                name = "Harpoon",
                allowedValues = {"Merfolk trident", "Trident of the seas", "Trident of the swamp", "Dragon harpoon", "Harpoon"},
                defaultValue = "Dragon harpoon",
                description = "Which trident or harpoon are you using?"
        ),
        @ScriptConfiguration(
                name = "Fishbowl",
                allowedValues = {"Fishbowl Helmet Equipped Already.", "Grab from Bank", "I do not have."},
                defaultValue = "Fishbowl Helmet Equipped Already.",
                description = "Are you wearing a fishbowl helmet?"
        ),
        @ScriptConfiguration(
                name = "Apparatus",
                allowedValues = {"Diving Apparatus Equipped Already.", "Grab from Bank", "I do not have."},
                defaultValue = "Diving Apparatus Equipped Already.",
                description = "Are you wearing a Diving Apparatus?"
        ),
        @ScriptConfiguration(
                name = "Flippers",
                allowedValues = {"Flippers Equipped Already.", "Grab from Bank", "I do not have."},
                defaultValue = "Flippers Equipped Already.",
                description = "Are you wearing Flippers?"
        ),
        @ScriptConfiguration(
                name = "Stamina",
                description = "Use Stamina Potions?",
                optionType = OptionType.BOOLEAN
        ),
        @ScriptConfiguration(
                name = "NumuliteUnlock",
                allowedValues = {"Yes", "No"},
                defaultValue = "No",
                description = "Did you pay 20,000 numulite for permanent access to drift net fishing? If not, grab Numulite from bank."
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
    private final ArrayList<Task> taskList = new ArrayList<Task>();

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("Sam Drift Net", "", "localhost:5575", true, true);
    }

    @Override
    public void onStart() {
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


// Net Fishing Script
// Check headslot for "Fishbowl helmet"
// Check cape for "Diving apparatus"
// Check boots for "Flippers"
// Check inventory for "Dragon harpoon"
//
// Check inventory for Drift net, if none, go to (x=10714, y=11236) -> Nets Annette -> Check net amount (if none, stop script with notification) -> Withdraw-5 Drift net (twice) (check for nets entering inventory)
//
// Picking up Seaweed spore (Take Seaweed spore)
//
//
// south-net collection tiles range: x= 13598, y=15452 to x=13598, y=15448
// east-net collection tiles range: x= 13602, y=15457 to x=13605, y=15457
//
// south-net tile area: 13598(13595), 15452(15448)
// east-net tile area: 13599(13605),15457(15461)
// stale-area tile range: 13598(13593), 15460(15453)
// Harvest Drift net anchors -> (Harvest the fish and destroy the net.) option 1 -> Discard all
// Set up Drift net anchors
//
// Chase Fish shoal -> move to the next, do not interact with the same one multiple times