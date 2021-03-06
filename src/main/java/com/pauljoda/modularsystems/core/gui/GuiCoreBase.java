package com.pauljoda.modularsystems.core.gui;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.network.OpenContainerPacket;
import com.pauljoda.modularsystems.core.registries.ConfigRegistry;
import com.pauljoda.modularsystems.core.tiles.AbstractCore;
import com.teambr.bookshelf.client.gui.GuiBase;
import com.teambr.bookshelf.client.gui.component.BaseComponent;
import com.teambr.bookshelf.client.gui.component.control.GuiComponentButton;
import com.teambr.bookshelf.client.gui.component.control.GuiComponentCheckBox;
import com.teambr.bookshelf.client.gui.component.display.GuiComponentText;
import com.teambr.bookshelf.client.gui.component.display.GuiTabCollection;
import com.teambr.bookshelf.helpers.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class GuiCoreBase<C extends Container> extends GuiBase<C> {

    protected AbstractCore core;
    private FontRenderer fr;

    public GuiCoreBase(C container, AbstractCore tileEntity, int width, int height, String name) {
        super(container, width, height, name);

        this.core = tileEntity;
        fr = Minecraft.getMinecraft().fontRenderer;
        addLeftTabs(leftTabs);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);

        core = (AbstractCore) core.getWorldObj().getTileEntity(core.xCoord, core.yCoord, core.zCoord);
    }

    @Override
    public void addComponents() {

    }

    /*
     * Side Tabs
     */
    @Override
    public void addLeftTabs(GuiTabCollection tabs) {
        if (!ConfigRegistry.updateTab) {
            if (fr != null) {
                String current = Reference.VERSION;
                String remote = ConfigRegistry.lastVersion;
                List<BaseComponent> updateTab = new ArrayList<>();
                updateTab.add(new GuiComponentText(GuiHelper.GuiColor.YELLOW + "Version Info", 9, 7));
                updateTab.add(new GuiComponentText(GuiHelper.GuiColor.BLUE + "Current Version", 50 - fr.getStringWidth("Current Version") / 2, 20));
                updateTab.add(new GuiComponentText((current.equalsIgnoreCase(remote) ? GuiHelper.GuiColor.GREEN : GuiHelper.GuiColor.RED) + current, 50 - fr.getStringWidth(current) / 2, 30));
                updateTab.add(new GuiComponentText(GuiHelper.GuiColor.BLUE + "Remote Version", 50 - fr.getStringWidth("Remote Version") / 2, 40));
                updateTab.add(new GuiComponentText(GuiHelper.GuiColor.GREEN + remote, 50 - fr.getStringWidth(remote) / 2, 50));
                updateTab.add(new GuiComponentButton(10, 60, 80, 20, "Get Update") {
                    @Override
                    public void doAction() {
                        try {
                            Desktop.getDesktop().browse(java.net.URI.create(ConfigRegistry.updateURL));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                updateTab.add(new GuiComponentCheckBox(5, 85, "Hide tab?", ConfigRegistry.updateTab) {
                    @Override
                    public void setValue(boolean bool) {
                        ConfigRegistry.set(Reference.VERSIONCHECK, Reference.UPDATE_TAB, bool);
                        com.pauljoda.modularsystems.core.managers.PacketManager.net.sendToServer(
                                new OpenContainerPacket.UpdateMessage(core.xCoord, core.yCoord, core.zCoord));
                    }
                });

                tabs.addReverseTab(updateTab, 100, 100, new Color(255, 168, 86), new ItemStack(Blocks.command_block));
                tabs.getTabs().get(0).setToolTip(asList("Version Status"));
            }
        }
    }
}
