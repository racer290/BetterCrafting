package racer290.bettercrafting.integration.botania.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import racer290.bettercrafting.integration.botania.tile.TileManaCraftingSlot;

@SideOnly(Side.CLIENT)
public class ManaCraftingSlotRenderer extends TileEntitySpecialRenderer<TileManaCraftingSlot> {
	
	@Override
	public void renderTileEntityAt(TileManaCraftingSlot te, double x, double y, double z, float partialTicks, int destroyStage) {
		
		GlStateManager.pushMatrix();
		
		// Time in ticks
		float time = Minecraft.getSystemTime();
		
		float deltaY = (float) (Math.sin((time / 2500) * Math.PI) * 0.2) / 2;
		
		// Translate directly to block center, some strange magic at y param
		GlStateManager.translate(x + 0.5, y + 0.325 + deltaY, z + 0.5);
		
		// 1 Full rotation per 5 secs; project division result on 360 deg
		float rotateY = (time / 5000) * 360;
		
		GlStateManager.rotate(rotateY, 0, 1, 0);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(te.getStoredItem(), TransformType.GROUND);
		
		rotateY *= -5 / 3;
		
		float deltaX = (float) (Math.cos((time / 5000) * Math.PI) * 0.25);
		float deltaZ = (float) (Math.sin((time / 5000) * Math.PI) * 0.25);
		
		GlStateManager.translate(x + deltaX + 0.5, y + 0.325 - deltaY, z + deltaZ + 0.5);
		
		GlStateManager.rotate(rotateY, 0, 1, 0);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(te.getStoredRune(), TransformType.GROUND);
		
		GlStateManager.popMatrix();
		
	}
	
}
