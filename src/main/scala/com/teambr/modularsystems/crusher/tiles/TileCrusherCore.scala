package com.teambr.modularsystems.crusher.tiles

import com.teambr.bookshelf.helper.BlockHelper
import com.teambr.modularsystems.core.common.blocks.BlockProxy
import com.teambr.modularsystems.core.functions.BlockCountFunction
import com.teambr.modularsystems.core.registries.{CrusherRecipeRegistry, FurnaceBannedBlocks, BlockValueRegistry}
import com.teambr.modularsystems.core.common.tiles.AbstractCore
import net.minecraft.block.Block
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack

/**
 * This file was created for Modular-Systems
 *
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since August 08, 2015
 */
class TileCrusherCore extends AbstractCore {

    override def smeltItem() {
        val smeltCount : (Int, Int) = smeltCountAndSmeltSize
        if (smeltCount != null && smeltCount._2 > 0) {
            var recipeResult : ItemStack = recipe(getStackInSlot(0))
            decrStackSize(0, smeltCount._1)
            if (getStackInSlot(1) == null) {
                recipeResult = recipeResult.copy
                recipeResult.stackSize = smeltCount._2
                setInventorySlotContents(1, recipeResult)
            }
            else {
                getStackInSlot(1).stackSize += smeltCount._2
            }
        }
    }

    /**
     * Get the output of the recipe
     * @param stack The input
     * @return The output
     */
    override def recipe(stack: ItemStack): ItemStack = CrusherRecipeRegistry.getOutput(stack).orNull

    /**
     * Take the blocks in this structure and generate the speed etc values
     * @param function The blocks count function
     */
    override def generateValues(function: BlockCountFunction): Unit = {
        for (i <- function.getBlockIds) {
            val t = (BlockHelper.getBlockFromString(i)._1, BlockHelper.getBlockFromString(i)._2)

            if (BlockValueRegistry.isBlockRegistered(t._1, t._2)) {
                values.speed += BlockValueRegistry.getSpeedValue(t._1, t._2, function.getBlockCount(t._1, t._2))
                values.efficiency += BlockValueRegistry.getEfficiencyValue(t._1, t._2, function.getBlockCount(t._1, t._2))
                values.multiplicity += BlockValueRegistry.getMultiplicityValue(t._1, t._2, function.getBlockCount(t._1, t._2))
            }
        }

        for (i <- function.getMaterialStrings) {
            if (BlockValueRegistry.isMaterialRegistered(i)) {
                values.speed += BlockValueRegistry.getSpeedValueMaterial(i, function.getMaterialCount(i))
                values.efficiency += BlockValueRegistry.getEfficiencyValueMaterial(i, function.getMaterialCount(i))
                values.multiplicity += BlockValueRegistry.getMultiplicityValueMaterial(i, function.getMaterialCount(i))
            }
        }
    }

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
            FurnaceBannedBlocks.isBlockBanned(block, meta)
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

    override var inventoryName: String = "inventory.crusher.title"

    override def initialSize : Int = 3
}