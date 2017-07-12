package racer290.bettercrafting.integration.botania.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import racer290.bettercrafting.integration.botania.tile.TileManaCraftingSlot;
import vazkii.botania.common.Botania;

public class ManaCraftingSlotRenderer extends TileEntitySpecialRenderer<TileManaCraftingSlot> {
	
	@Override
	public void renderTileEntityAt(TileManaCraftingSlot te, double x, double y, double z, float partialTicks, int destroyStage) {
		
		GlStateManager.pushMatrix();
		
		// Time in ticks
		float time = Minecraft.getSystemTime();
		
		float deltaY = (float) (Math.sin((time / 5000) * Math.PI * 2) * 0.2) / 2;
		
		// Translate directly to block center, some strange magic at y param
		GlStateManager.translate(x + 0.5, y + 0.325 + deltaY, z + 0.5);
		
		// 1 Full rotation per 5 secs; project division result on 360 deg
		float rotateY = (time / 5000) * 360;
		
		GlStateManager.rotate(rotateY, 0, 1, 0);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(te.getStoredItem(), TransformType.GROUND);
		
		GlStateManager.popMatrix();
		
		Botania.proxy.sparkleFX(x, y, z, 0.2f, 0.2f, 0.2f, (float) (0.5f * Math.random()), 5);
		
	}
	
}
