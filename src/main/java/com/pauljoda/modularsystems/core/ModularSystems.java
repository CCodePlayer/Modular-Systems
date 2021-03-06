package com.pauljoda.modularsystems.core;

import com.pauljoda.modularsystems.core.achievement.ModAchievement;
import com.pauljoda.modularsystems.core.api.nei.INEICallback;
import com.pauljoda.modularsystems.core.commands.AddBannedBlock;
import com.pauljoda.modularsystems.core.commands.AddFluidFuel;
import com.pauljoda.modularsystems.core.commands.OpenValueConfig;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.managers.*;
import com.pauljoda.modularsystems.core.proxy.CommonProxy;
import com.pauljoda.modularsystems.core.registries.*;
import com.pauljoda.modularsystems.storage.events.BlockBreakEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

@Mod(name = Reference.MOD_NAME, modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class ModularSystems {
    @Mod.Instance(Reference.MOD_ID)
    public static ModularSystems instance;

    @SidedProxy( clientSide="com.pauljoda.modularsystems.core.proxy.ClientProxy", serverSide="com.pauljoda.modularsystems.core.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static INEICallback nei;

    public static String configFolderLocation;

    //Creates the Creative Tab
    public static CreativeTabs tabModularSystems = new CreativeTabs("tabModularSystems") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockManager.furnaceCore);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFolderLocation = event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "Modular-Systems";
        ConfigRegistry.init(configFolderLocation);

        BlockManager.init();

        MinecraftForge.EVENT_BUS.register(FurnaceBannedBlocks.INSTANCE);
        MinecraftForge.EVENT_BUS.register(BlockValueRegistry.INSTANCE);
        MinecraftForge.EVENT_BUS.register(FluidFuelValues.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new BlockBreakEvent());

        FMLCommonHandler.instance().bus().register(new PlayerLogManager());

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiManager());

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws InterruptedException {

        FurnaceBannedBlocks.INSTANCE.init();
        BlockValueRegistry.INSTANCE.init();
        FluidFuelValues.INSTANCE.init();
        CrusherRecipeRegistry.INSTANCE.init();

        ItemManager.init();

        RecipeManager.init();
        PacketManager.initPackets();

        proxy.init();
        FMLCommonHandler.instance().bus().register(ModAchievement.INSTANCE);

        VersionManager.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) { }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        MinecraftServer server = MinecraftServer.getServer();
        ICommandManager command = server.getCommandManager();
        ServerCommandManager manager = (ServerCommandManager) command;
        manager.registerCommand(new AddBannedBlock());
        manager.registerCommand(new AddFluidFuel());
        manager.registerCommand(new OpenValueConfig());
    }
}
