package com.teambr.modularsystems.core

import java.io.File

import com.teambr.modularsystems.core.achievement.ModAchievements
import com.teambr.modularsystems.core.api.nei.NEICallback
import com.teambr.modularsystems.core.common.CommonProxy
import com.teambr.modularsystems.core.lib.Reference
import com.teambr.modularsystems.core.managers.{ItemManager, BlockManager}
import com.teambr.modularsystems.core.registries.BlockValueRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{ SidedProxy, FMLCommonHandler, Mod }
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.apache.logging.log4j.LogManager

/**
 * This file was created for the Modular-Systems
 * API. The source is available at:
 * https://github.com/TeamCoS/Modular-Systems
 *
 * Bookshelf if licensed under the is licensed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 02, 2015
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION,
        dependencies = Reference.DEPENDENCIES, modLanguage = "scala")
object ModularSystems {
    //The logger. For logging
    final val logger = LogManager.getLogger(Reference.MOD_NAME)

    var nei : NEICallback = null

    var configFolderLocation : String = ""

    @SidedProxy(clientSide = "com.teambr.modularsystems.core.client.ClientProxy",
                serverSide = "com.teambr.modularsystems.core.common.CommonProxy")
    var proxy : CommonProxy = null

    var tabModularSystems : CreativeTabs = new CreativeTabs("tabModularSystems") {
        @SideOnly(Side.CLIENT)
        override def getTabIconItem: Item = Item.getItemFromBlock(Blocks.furnace)
    }

    @EventHandler def preInit(event : FMLPreInitializationEvent) = {
        configFolderLocation = event.getModConfigurationDirectory.getAbsolutePath + File.separator + "Modular-Systems"
        proxy.preInit()
        BlockManager.preInit()
        ItemManager.preInit()

        MinecraftForge.EVENT_BUS.register(BlockValueRegistry)
    }

    @EventHandler def init(event : FMLInitializationEvent) =  {
        ItemManager.init() // Must be before proxy registration
        proxy.init()
        FMLCommonHandler.instance().bus().register(ModAchievements)
    }

    @EventHandler def postInit(event : FMLPostInitializationEvent) = {
        proxy.postInit()
    }
}