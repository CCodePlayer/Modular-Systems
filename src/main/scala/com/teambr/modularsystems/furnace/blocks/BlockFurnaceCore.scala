package com.teambr.modularsystems.furnace.blocks

import java.util.Random

import com.teambr.bookshelf.Bookshelf
import com.teambr.bookshelf.common.blocks.properties.PropertyRotation
import com.teambr.bookshelf.common.blocks.traits.DropsItems
import com.teambr.bookshelf.common.tiles.traits.OpensGui
import com.teambr.modularsystems.core.common.blocks.BaseBlock
import com.teambr.modularsystems.core.common.blocks.traits.CoreStates
import com.teambr.modularsystems.furnace.container.ContainerFurnaceCore
import com.teambr.modularsystems.furnace.gui.GuiFurnaceCore
import com.teambr.modularsystems.furnace.tiles.TileEntityFurnaceCore
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.{ EnumParticleTypes, BlockPos, EnumFacing }
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{ Side, SideOnly }

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
class BlockFurnaceCore(name: String) extends BaseBlock(Material.rock, name, classOf[TileEntityFurnaceCore])
        with OpensGui with CoreStates with DropsItems {

    //Block Methods
    override def breakBlock(world: World, pos: BlockPos, state: IBlockState) {
        world.getTileEntity(pos) match {
            case core: TileEntityFurnaceCore => core.deconstructMultiblock()
            case _ =>
        }
        super[DropsItems].breakBlock(world, pos, state)
    }

    override def onBlockActivated(world : World, pos : BlockPos, state : IBlockState, player : EntityPlayer, side : EnumFacing, hitX : Float, hitY : Float, hitZ : Float) : Boolean = {
        world.getTileEntity(pos) match {
            case core: TileEntityFurnaceCore =>
                if (core.wellFormed)
                    player.openGui(Bookshelf, 0, world, pos.getX, pos.getY, pos.getZ)
                else
                    core.setDirty()
            case _ =>
        }
        true
    }

    @SideOnly(Side.CLIENT)
    override def randomDisplayTick(world : World, pos : BlockPos, state : IBlockState, rand : Random) {
        world.getTileEntity(pos) match {
            case tile : TileEntityFurnaceCore =>
                if(tile.isBurning) {
                    val facing = state.getValue(PropertyRotation.FOUR_WAY.getProperty)
                    val f : Float = pos.getX.toFloat + 0.5F
                    val f1 : Float = pos.getY.toFloat + 0.0F + rand.nextFloat * 6.0F / 16.0F
                    val f2 : Float = pos.getZ.toFloat + 0.5F
                    val f3 : Float = 0.52F
                    val f4 : Float = rand.nextFloat * 0.6F - 0.3F

                    facing match {
                        case EnumFacing.WEST =>
                            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f - f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
                            world.spawnParticle(EnumParticleTypes.FLAME, (f - f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
                        case EnumFacing.EAST =>
                            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f + f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
                            world.spawnParticle(EnumParticleTypes.FLAME,  (f + f3).toDouble, f1.toDouble, (f2 + f4).toDouble, 0.0D, 0.0D, 0.0D)
                        case EnumFacing.NORTH =>
                            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f + f4).toDouble, f1.toDouble, (f2 - f3).toDouble, 0.0D, 0.0D, 0.0D)
                            world.spawnParticle(EnumParticleTypes.FLAME, (f + f4).toDouble, f1.toDouble, (f2 - f3).toDouble, 0.0D, 0.0D, 0.0D)
                        case _ =>
                            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (f + f4).toDouble, f1.toDouble, (f2 + f3).toDouble, 0.0D, 0.0D, 0.0D)
                            world.spawnParticle(EnumParticleTypes.FLAME, (f + f4).toDouble, f1.toDouble, (f2 + f3).toDouble, 0.0D, 0.0D, 0.0D)
                    }
                }
            case _ =>
        }
    }

    //OpensGui Methods
    override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
        new ContainerFurnaceCore(player.inventory, world.getTileEntity(new BlockPos(x, y, z)).asInstanceOf[TileEntityFurnaceCore])
    }

    override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = {
        new GuiFurnaceCore(player, world.getTileEntity(new BlockPos(x, y, z)).asInstanceOf[TileEntityFurnaceCore])
    }
}
