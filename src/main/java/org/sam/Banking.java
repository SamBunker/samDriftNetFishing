package org.sam.Tasks;

import org.sam.Task;
import org.samDriftNetFishing;
import org.sam.Constants;

public class Banking extends Task {
    DriftNetFishing main;
    private final BOOLEAN numuliteUnlock;
    private final STRING harpoon;
    private final BOOLEAN harpoonInfo;
    private final STRING fishbowl;
    private final STRING apparatus;
    private final STRING flippers;
    private final BOOLEAN graceful;
    private final BOOLEAN stamina;

    public Banking(DriftNetFishing main, BOOLEAN numuliteUnlock, STRING harpoon, STRING harpoonInfo, STRING fishbowlInfo, STRING apparatusInfo, STRING flippersInfo, BOOLEAN graceful, BOOLEAN stamina) {
        super();
        super.name = "Banking";
        this.main = main;

        this.numuliteUnlock = numuliteUnlock;
        this.harpoon = harpoon;
        this.harpoonInfo = harpoonInfo;
        this.fishbowlInfo = fishbowlInfo;
        this.apparatusInfo = apparatusInfo;
        this.flippersInfo = flippersInfo;
        this.graceful = graceful;
        this.stamina = stamina;
    }

    public void InventoryEquipmentCheck() {
        List<WithdrawItems> withdrawItems = new ArrayList<>();
        
        switch (fishbowlInfo) {
            case "Fishbowl Helmet Equpped Already":
                Item head = Equipment.itemAt(Equipment.Slot.HEAD);
                if (head != Item.Nil && item.name() == Constants.FISHBOWL_HELMET) {
                    break;
                } else {
                    Item fishbowlInventory = Inventory.stream().name(Constants.FISHBOWL_HELMET).first()
                    if (fishbowlInventory.valid) {
                        fishbowlInventory.interact("Equip");
                        break;
                    } else {
                        withdrawItems.add(new WithdrawItems(Constants.FISHBOWL_HELMET, 1));
                        Notifications.showNotification("Fishbowl helmet not equipped, in inventory. Added to bank widthrawl array.");
                        break;
                    }
                }
                Notifications.showNotification("Fishbowl Helmet debug");
                break;
            case "Grab from Bank":
                withdrawItems.add(new WithdrawItems(Constants.FISHBOWL_HELMET, 1));
                break; //Bank.withdraw(itemName, amountToDeposit);
            case "I do not have":
                Notifications.showNotification("Script does not support this yet. (Fishbowl helmet)");
                ScriptManager.INSTANCE.stop();
                break;
        }
        switch (apparatusInfo) {
            case "Diving Apparatus Equipped Already":
                Item cape = Equipment.itemAt(Equipment.Slot.CAPE);
                if (cape != Item.Nil && item.name() == Constants.DIVING_APPARATUS) {
                    break;
                } else {
                    Item divingApparatusInventory = Inventory.stream().name(Constants.DIVING_APPARATUS).first()
                    if (divingApparatusInventory.valid) {
                        divingApparatusInventory.interact("Equip");
                        break;
                    } else {
                        withdrawItems.add(new WithdrawItems(Constants.DIVING_APPARATUS, 1));
                        Notifications.showNotification("Diving Apparatus not equipped, in inventory. Added to bank widthrawl array.");
                        break;
                    }
                }
                Notifications.showNotification("Diving Apparatus debug");
                break;
            case "Grab from Bank":
                withdrawItems.add(new WithdrawItems(Constants.DIVING_APPARATUS, 1));
                break;
            case "I do not have":
                Notifications.showNotification("Script does not support this yet. (Diving Apparatus)");
                ScriptManager.INSTANCE.stop();
                break;
        }
        switch (flippersInfo) {
            case "Flippers Equipped Already":
                Item feet = Equipment.itemAt(Equipment.Slot.FEET);
                if (cape != Item.Nil && item.name() == Constants.FLIPPERS) {
                    break;
                } else {
                    Item flippersInventory = Inventory.stream().name(Constants.FLIPPERS).first()
                    if (flippersInventory.valid) {
                        flippersInventory.interact("Equip");
                        break;
                    } else {
                        withdrawItems.add(new WithdrawItems(Constants.FLIPPERS, 1));
                        Notifications.showNotification("Diving Apparatus not equipped, in inventory. Added to bank widthrawl array.");
                        break;
                    }
                }
                Notifications.showNotification("Flippers debug");
                break;
            case "Grab from Bank":
                withdrawItems.add(new WithdrawItems(Constants.FLIPPERS, 1));
                break;
            case "I do not have":
                break;
        }
        switch (harpoonInfo) {
            case "Harpoon/Trident Equipped Already":
                Item weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND);
                if (weapon != Item.Nil && item.name() == harpoon) {
                    break;
                } else {
                    Item harpoonInventory = Inventory.stream().name(harpoon).first()
                    if (harpoonInventory.valid) {
                        harpoonInventory.interact("Equip");
                        break;
                    } else {
                        withdrawItems.add(new WithdrawItems(harpoon, 1));
                        Notifications.showNotification("Harpoon/Trident not equipped, in inventory. Added to bank widthrawl array.");
                        break;
                    }
                }
                Notifications.showNotification("Harpoon/Trident debug");
                break;
            case "Grab from Bank":
                withdrawItems.add(new WithdrawItems(harpoon, 1));
                break;
            case "I do not have":
                // Maybe check bank for a regular harpoon?
                Notifications.showNotification("Script does not support this yet. (Harpoon)");
                ScriptManager.INSTANCE.stop();
                break;
        }
        switch (numuliteUnlock) {
            case true:
                break;
            case false:
                withdrawItems.add(new WithdrawItems(Constants.NUMULITE, 200));
                break;
        }
        // Handle Stamina on bank widthdrawl?
        switch (stamina) {
            case true:
                //withdrawItems.add(new WithdrawItems(Constants.STAMINA_FOUR, 6));
                break;
            case false:
                break;
        }
        // Items handled, time to handle banking

        public void Banking() {
            if (Bank.inViewport()) {
                        Condition.wait(() -> Bank.open(), 50, 10);
                        if (Bank.opened()) {
                            Bank.depositAllExcept("");
                        }
                    }
        }


    @Override
    public boolean activate() {
        return 
        // Compare to ScriptConfiguration settings selected at the beginning of the script
    }

    @Override
    public void execute() {

    }
}