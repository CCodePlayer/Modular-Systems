package com.pauljoda.modularsystems.core.renderers;

import com.pauljoda.modularsystems.power.tiles.*;
import com.teambr.bookshelf.util.RenderUtils;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;

public class TileSpecialDummyRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        Tessellator tess = Tessellator.instance;
        tess.addTranslation((float) x, (float) y, (float) z);
        RenderUtils.bindGuiComponentsSheet();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        float level = (float) Math.max(3 / 16F, (((TileBankBase)tile).getPowerLevelScaled(8) + 4) / 16F);

        if(tile instanceof TileBankLiquid) {
            TileBankLiquid fluidTile = (TileBankLiquid)tile;
            FluidTankInfo info = fluidTile.getTankInfo(ForgeDirection.UNKNOWN)[0];
            if(info != null && info.fluid != null) {
                IIcon fluidTexture = info.fluid.getFluid().getIcon();
                RenderUtils.bindMinecraftBlockSheet();
                float level2 = (float) Math.max(3, (((TileBankBase) tile).getPowerLevelScaled(12) + 4));
                float difference = (fluidTexture.getMaxU() - fluidTexture.getMinU()) / 16;
                float differenceV = (fluidTexture.getMaxV() - fluidTexture.getMinV()) / 16;
                drawLevel(7 / 16F, 4 / 16F, -0.003F, 9 / 16F, level, -0.003F, fluidTexture.getMinU() + (4 * difference), fluidTexture.getMaxV() - (4 * differenceV), fluidTexture.getMinU() + (9 * difference), fluidTexture.getMaxV() - (level2 * differenceV), tess);
                drawLevel(-0.003F, 4 / 16F, 7 / 16F, -0.003F, level, 9 / 16F, fluidTexture.getMinU() + (4 * difference), fluidTexture.getMaxV() - (4 * differenceV), fluidTexture.getMinU() + (9 * difference), fluidTexture.getMaxV() - (level2 * differenceV), tess);
                drawLevel(1.003F, 4 / 16F, 7 / 16F, 1.003F, level, 9 / 16F, fluidTexture.getMinU() + (4 * difference), fluidTexture.getMaxV() - (4 * differenceV), fluidTexture.getMinU() + (9 * difference), fluidTexture.getMaxV() - (level2 * differenceV), tess);
                drawLevel(7 / 16F, 4 / 16F, 1.003F, 9 / 16F, level, 1.003F, fluidTexture.getMinU() + (4 * difference), fluidTexture.getMaxV() - (4 * differenceV), fluidTexture.getMinU() + (9 * difference), fluidTexture.getMaxV() - (level2 * differenceV), tess);
            }
        } else {
            RenderUtils.setColor(new Color(200, 0, 0));
            drawLevel(7 / 16F, 4 / 16F, -0.003F, 9 / 16F, level, -0.003F, tess);
            drawLevel(-0.003F, 4 / 16F, 7 / 16F, -0.003F, level, 9 / 16F, tess);
            drawLevel(1.003F, 4 / 16F, 7 / 16F, 1.003F, level, 9 / 16F, tess);
            drawLevel(7 / 16F, 4 / 16F, 1.003F, 9 / 16F, level, 1.003F, tess);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        RenderUtils.bindMinecraftBlockSheet();
        tess.addTranslation((float) -x, (float) -y, (float) -z);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        RenderUtils.restoreRenderState();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
    }

    protected void drawLevel(float x1, float y1, float z1, float x2, float y2, float z2, Tessellator tess) {
        tess.startDrawingQuads();
        tess.addVertexWithUV(x1, y1, z1, 2 / 255F, 2 / 255F);
        tess.addVertexWithUV(x1, y2, z1, 2 / 255F, 4 / 255F);
        tess.addVertexWithUV(x2, y2, z2, 3 / 255F, 4 / 255F);
        tess.addVertexWithUV(x2, y1, z2, 3 / 255F, 2 / 255F);
        tess.draw();
    }

    protected void drawLevel(float x1, float y1, float z1, float x2, float y2, float z2, float u1, float v1, float u2, float v2, Tessellator tess) {
        tess.startDrawingQuads();
        tess.addVertexWithUV(x1, y1, z1, u1, v1);
        tess.addVertexWithUV(x1, y2, z1, u1, v2);
        tess.addVertexWithUV(x2, y2, z2, u2, v2);
        tess.addVertexWithUV(x2, y1, z2, u2, v1);
        tess.draw();
    }
}
