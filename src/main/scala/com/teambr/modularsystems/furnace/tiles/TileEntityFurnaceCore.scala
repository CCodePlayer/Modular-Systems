package com.teambr.modularsystems.furnace.tiles

import com.teambr.modularsystems.core.blocks.BlockProxy
import com.teambr.modularsystems.core.functions.BlockCountFunction
import com.teambr.modularsystems.core.managers.BlockManager
import com.teambr.modularsystems.core.tiles.AbstractCore
import net.minecraft.block.Block
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.util.BlockPos
import net.minecraft.world.World

/**
 * This file was created for Modular-Systems
 *
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 07, 2015
 */
class TileEntityFurnaceCore extends AbstractCore {

    /**
     * Used to set the blocks to its active and non-active state
     * @param positiveBurnTime True if active
     * @param world World object
     * @param pos The block position
     */
    override def updateBlockState(positiveBurnTime: Boolean, world: World, pos: BlockPos): Unit = {

    }

    /**
     * Take the blocks in this structure and generate the speed etc values
     * @param function The blocks count function
     */
    override def generateValues(function: BlockCountFunction): Unit = {

    }

    /**
     * Gets around that last little bit where the values line up weird and it doesn't convert back
     * @return The block to set when on
     */
    override def getOnBlock: Block = BlockManager.furnaceCoreActive

    /**
     * Check if this blocks is not allowed in the structure
     * @param block The blocks to check
     * @param meta The meta data of said blocks
     * @return True if it is banned
     */
    override def isBlockBanned(block: Block, meta: Int): Boolean = {
        if (block.isInstanceOf[BlockProxy])
            false
        else
            true //TODO Furnace Banned Blocks
    }

    /**
     * Get the output of the recipe
     * @param stack The input
     * @return The output
     */
    override def recipe(stack: ItemStack): ItemStack = {
        if (stack != null)
            FurnaceRecipes.instance().getSmeltingResult(stack)
        else
            null
    }

    /**
     * Used to output the redstone single from this structure
     *
     * Use a range from 0 - 16.
     *
     * 0 Usually means that there is nothing in the tile, so take that for lowest level. Like the generator has no energy while
     * 16 is usually the flip side of that. Output 16 when it is totally full and not less
     *
     * @return int range 0 - 16
     */
    override def getRedstoneOutput: Int = Container.calcRedstoneFromInventory(this)

    override var inventoryName: String = "inventory.furnace.title" //_
}
