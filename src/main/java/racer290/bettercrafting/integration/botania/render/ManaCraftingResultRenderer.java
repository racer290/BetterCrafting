package racer290.bettercrafting.integration.botania.render;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import racer290.bettercrafting.integration.botania.tile.TileManaCraftingResult;

@SideOnly(Side.CLIENT)
public class ManaCraftingResultRenderer extends TileEntitySpecialRenderer<TileManaCraftingResult> {
	
	@Override
	public void renderTileEntityAt(@Nonnull TileManaCraftingResult tile, double x, double y, double z, float partticks, int digProgress) {
		
		GlStateManager.pushMatrix();
		
		// Time in ticks
		float time = Minecraft.getSystemTime();
		
		float deltaY = (float) (Math.sin((time / 5000) * Math.PI * 2) * 0.2) / 2;
		
		// Translate directly to block center, some strange magic at y param
		GlStateManager.translate(x + 0.5, y + 0.25 + deltaY, z + 0.5);
		
		// 1 Full rotation per 5 secs; project division result on 360 deg
		float rotateY = (time / 5000) * 360;
		
		GlStateManager.rotate(rotateY, 0, 1, 0);
		
		GlStateManager.scale(1.5d, 1.5d, 1.5d);
		
		Minecraft.getMinecraft().getRenderItem().renderItem(tile.getStoredItem(), TransformType.GROUND);
		
		GlStateManager.popMatrix();
		
	}
	
}
