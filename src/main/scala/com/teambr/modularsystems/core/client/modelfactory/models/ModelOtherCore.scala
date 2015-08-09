package com.teambr.modularsystems.core.client.modelfactory.models

import java.util
import javax.vecmath.Vector3f

import com.teambr.bookshelf.common.blocks.properties.PropertyRotation
import com.teambr.modularsystems.core.common.blocks.traits.CoreStates
import com.teambr.modularsystems.core.lib.Reference
import com.teambr.modularsystems.core.managers.BlockManager
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model._
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.{ IBakedModel, ModelRotation }
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.client.model.{ ISmartItemModel, ISmartBlockModel }

/**
 * This file was created for Modular-Systems
 *
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 08, 2015
 */
class ModelOtherCore  extends ISmartBlockModel with ISmartItemModel {
    val faceBakery = new FaceBakery
    var facingDirection = EnumFacing.NORTH
    val modelRot = ModelRotation.X0_Y0
    var block : Block = null
    var isActive = false

    def this(rotation : EnumFacing, active : Boolean, theBlock : Block) = {
        this()
        facingDirection = rotation
        isActive = active
        block = theBlock
    }
    override def getFaceQuads(facing : EnumFacing) : util.List[_] = {
        val bakedQuads = new util.ArrayList[BakedQuad]()
        addFacesAndStuff(facing, bakedQuads)
        bakedQuads
    }

    private def addFacesAndStuff(facing : EnumFacing, bakedQuad: util.ArrayList[BakedQuad]) : Unit = {
        val uv = new BlockFaceUV(Array[Float](0.0F, 0.0F, 16.0F, 16.0F), 0)
        val face = new BlockPartFace(null, 0, "", uv)

        val scale = true
        val hopperTop = Minecraft.getMinecraft.getTextureMapBlocks.getTextureExtry("minecraft:blocks/hopper_top")
        var ourTexture = getTextureForBlock(facing)
        if(ourTexture == null)
            ourTexture = hopperTop

        facing match {
            case EnumFacing.UP =>
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, ourTexture, EnumFacing.UP, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.03F, 0.0F), new Vector3f(16.0F, 16.03F, 2.0F), face, hopperTop, EnumFacing.UP, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.03F, 14.0F), new Vector3f(16.0F, 16.03F, 16.0F), face, hopperTop, EnumFacing.UP, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.03F, 0.0F), new Vector3f(2.0F, 16.03F, 16.0F), face, hopperTop, EnumFacing.UP, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(14.0F, 16.03F, 0.0F), new Vector3f(16.0F, 16.03F, 16.0F), face, hopperTop, EnumFacing.UP, modelRot, null, scale, true))
            case EnumFacing.DOWN =>
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, ourTexture, EnumFacing.DOWN, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, -0.03F, 0.0F), new Vector3f(16.0F, -0.03F, 2.0F), face, hopperTop, EnumFacing.DOWN, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, -0.03F, 14.0F), new Vector3f(16.0F, -0.03F, 16.0F), face, hopperTop, EnumFacing.DOWN, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, -0.03F, 0.0F), new Vector3f(2.0F, -0.03F, 16.0F), face, hopperTop, EnumFacing.DOWN, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(14.0F, -0.03F, 0.0F), new Vector3f(16.0F, -0.03F, 16.0F), face, hopperTop, EnumFacing.DOWN, modelRot, null, scale, true))
            case EnumFacing.NORTH =>
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, ourTexture, EnumFacing.NORTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, -0.03F), new Vector3f(16.0F, 2.0F, -0.03F), face, hopperTop, EnumFacing.NORTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 14.0F, -0.03F), new Vector3f(16.0F, 16.0F, -0.03F), face, hopperTop, EnumFacing.NORTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, -0.03F), new Vector3f(2.0F, 16.0F, -0.03F), face, hopperTop, EnumFacing.NORTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(14.0F, 0.0F, -0.03F), new Vector3f(16.0F, 16.0F, -0.03F), face, hopperTop, EnumFacing.NORTH, modelRot, null, scale, true))
            case EnumFacing.SOUTH =>
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, ourTexture, EnumFacing.SOUTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.03F), new Vector3f(16.0F, 2.0F, 16.03F), face, hopperTop, EnumFacing.SOUTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 14.0F, 16.03F), new Vector3f(16.0F, 16.0F, 16.03F), face, hopperTop, EnumFacing.SOUTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.03F), new Vector3f(2.0F, 16.0F, 16.03F), face, hopperTop, EnumFacing.SOUTH, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(14.0F, 0.0F, 16.03F), new Vector3f(16.0F, 16.0F, 16.03F), face, hopperTop, EnumFacing.SOUTH, modelRot, null, scale, true))
            case EnumFacing.EAST =>
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, ourTexture, EnumFacing.EAST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(16.03F, 0.0F, 0.0F), new Vector3f(16.03F, 2.0F, 16.0F), face, hopperTop, EnumFacing.EAST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(16.03F, 14.0F, 0.0F), new Vector3f(16.03F, 16.0F, 16.0F), face, hopperTop, EnumFacing.EAST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(16.03F, 0.0F, 0.0F), new Vector3f(16.03F, 16.0F, 2.0F), face, hopperTop, EnumFacing.EAST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(16.03F, 0.0F, 14.0F), new Vector3f(16.03F, 16.0F, 16.0F), face, hopperTop, EnumFacing.EAST, modelRot, null, scale, true))
            case EnumFacing.WEST =>
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, ourTexture, EnumFacing.WEST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(-0.03F, 0.0F, 0.0F), new Vector3f(-0.03F, 2.0F, 16.0F), face, hopperTop, EnumFacing.WEST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(-0.03F, 14.0F, 0.0F), new Vector3f(-0.03F, 16.0F, 16.0F), face, hopperTop, EnumFacing.WEST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(-0.03F, 0.0F, 0.0F), new Vector3f(-0.03F, 16.0F, 2.0F), face, hopperTop, EnumFacing.WEST, modelRot, null, scale, true))
                bakedQuad.add(faceBakery.makeBakedQuad(new Vector3f(-0.03F, 0.0F, 14.0F), new Vector3f(-0.03F, 16.0F, 16.0F), face, hopperTop, EnumFacing.WEST, modelRot, null, scale, true))
        }
    }

    def getTextureForBlock(facing : EnumFacing) : TextureAtlasSprite = {
        if(block == BlockManager.crusherCore) {
            if(facing == facingDirection) {
                if(isActive)
                    return Minecraft.getMinecraft.getTextureMapBlocks.getTextureExtry(Reference.MOD_ID + ":blocks/crusherFront_On")
                else
                    return Minecraft.getMinecraft.getTextureMapBlocks.getTextureExtry(Reference.MOD_ID + ":blocks/crusherFront_off")
            }
        }
        if(facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
            Minecraft.getMinecraft.getTextureMapBlocks.getTextureExtry("minecraft:blocks/furnace_top")
        }
        else
            Minecraft.getMinecraft.getTextureMapBlocks.getTextureExtry("minecraft:blocks/furnace_side")
    }

    override def getGeneralQuads : util.List[_] = {
        new util.ArrayList[Nothing]()
    }

    override def isAmbientOcclusion: Boolean = true

    override def isGui3d: Boolean = true

    override def isBuiltInRenderer: Boolean = false

    override def handleBlockState(state : IBlockState) : IBakedModel = {
        new ModelOtherCore(state.getValue(PropertyRotation.FOUR_WAY.getProperty).asInstanceOf[EnumFacing],
            state.getValue(state.getBlock.asInstanceOf[CoreStates].PROPERTY_ACTIVE).asInstanceOf[Boolean], state.getBlock)
    }

    override def handleItemState(stack : ItemStack) : IBakedModel = {
        new ModelOtherCore(EnumFacing.NORTH, false, Block.getBlockFromItem(stack.getItem))
    }

    val MovedUp = new ItemTransformVec3f(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.05F, 0.05F, -0.15F), new Vector3f(-0.5F, -0.5F, -0.5F))
    override def getItemCameraTransforms : ItemCameraTransforms = {
        new ItemCameraTransforms(MovedUp, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT)
    }

    override def getTexture : TextureAtlasSprite = getTextureForBlock(facingDirection)
}
