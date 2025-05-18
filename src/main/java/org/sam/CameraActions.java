package org.sam;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class CameraActions {

    public CameraActions() {
        super();
    }

    private void openSettings() {
        if (!Widgets.component(601, 69).visible()) {
            if (Widgets.component(601, 95).interact("Show More")) {
                Condition.wait(() -> Widgets.component(601, 69).visible(), 80, 10);
            }
        }
        Game.tab(Game.Tab.SETTINGS);
        Condition.wait(() -> Game.tab().equals(Game.Tab.SETTINGS), 40, 20);
    }

    private void controlSettings() {
        Component gameSettings = Widgets.component(116, 59);
        if (gameSettings.visible()) {
            gameSettings.click();
        }
        Condition.wait(() -> Widgets.component(116, 4).visible(), 50, 20);
    }

    private void displaySettings() {
        Component displaySettings = Widgets.component(116, 68);
        if (displaySettings.visible()) {
            displaySettings.click();
        }
        Condition.wait(() -> Widgets.component(116, 10).visible(), 50, 20);
    }

    private void openInventory() {
        if (Inventory.open()) {
            Condition.wait(() -> Inventory.opened(), 25, 15);
        }
    }

    public void zoomOut() {
        openSettings();
        displaySettings();
        Component zoomSlider = Widgets.component(116, 49);
        if (zoomSlider.visible()) {
            zoomSlider.click();
        }
        Condition.wait(() -> zoomSlider.click(), 30, 10);
        openInventory();
    }

    public void zoomIn() {
        openSettings();
        displaySettings();
        Component zoomSlider = Widgets.component(116, 52);
        if (zoomSlider.visible()) {
            zoomSlider.click();
        }
        Condition.wait(() -> zoomSlider.click(), 30, 10);
        openInventory();
    }

    public void AdjustMinimapSettings() {
        openSettings();
        Condition.sleep(50);
        displaySettings();
        Condition.sleep(50);
        Component controlSettings = Widgets.component(116, 0);
        if (controlSettings.visible()) {
            controlSettings.interact("All Settings");
            Condition.wait(() -> Widgets.component(134, 0).visible(), 50, 15);
            Condition.sleep(50);
            Component interfaces = Widgets.component(134, 6);
            Component settingsContainer = Widgets.component(134, 13);
            if (interfaces.visible()) {
                interfaces.click();
                Condition.wait(() -> settingsContainer.visible(), 50, 15);

                Component settingsScrollbar = Widgets.component(134, 1);
                Component miniZoomWidget = Widgets.component(134, 70);

                Widgets.scrollIncrementally(settingsScrollbar, settingsContainer, miniZoomWidget);
                Condition.wait(() -> miniZoomWidget.visible(), 100, 20);
            }
        }
    }
    public void closeSettings() {
        Component closeSettingsButton = Widgets.component(134, 4);

        if (closeSettingsButton.visible()) {
            closeSettingsButton.click();
            Condition.wait(() -> !Widgets.component(134, 13).visible(), 20, 10);
        }
    }

    public void MinimapZoomHalf() {
        AdjustMinimapSettings();
        Component zoomOut = Widgets.component(134, 84);
        zoomOut.click();
        Condition.sleep(100);
        closeSettings();
    }
}
        //scroll bar 134, 20
        //134, 0 scrollbar track
        //134, 1 track top
        //134, 70 Minimap - Zoom Level
        //134, 81 - midpoint for minimap zoom level track (4/7)
        // 134 84 - 5/7 track

